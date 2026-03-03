package main.java.edu.escuelaing.framework;

import java.io.*;
import java.net.*;

/**
 * Core HTTP server implementation.
 * Listens on port 35000, handles multiple concurrent requests using threads.
 *
 * Request handling order:
 *   1. Check registered REST routes (via Spark)
 *   2. Check static files (via StaticFileHandler)
 *   3. Return "Hello World!" for any unrecognized path
 *
 * Follows HTTP/1.1 conventions:
 *   https://developer.mozilla.org/en-US/docs/Web/HTTP/Guides/Overview
 */
public class HttpServer {

    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = null;
        try {
            serverSocket = new ServerSocket(35000);
        } catch (IOException e) {
            System.err.println("Could not listen on port: 35000.");
            System.exit(1);
        }

        System.out.println("Server running on http://localhost:35000");
        System.out.println("Ready to receive requests...");

        boolean running = true;
        while (running) {
            try {
                Socket clientSocket = serverSocket.accept();
                new Thread(() -> {
                    try {
                        handleClient(clientSocket);
                    } catch (Exception e) {
                        System.err.println("Error handling client: " + e.getMessage());
                    }
                }).start();
            } catch (IOException e) {
                System.err.println("Accept failed.");
            }
        }
        serverSocket.close();
    }

    /**
     * Handles a single client connection:
     * Reads the HTTP request, dispatches it, and writes the HTTP response.
     */
    private static void handleClient(Socket clientSocket) throws Exception {
        BufferedReader in = new BufferedReader(
                new InputStreamReader(clientSocket.getInputStream()));
        OutputStream out = clientSocket.getOutputStream();

        String inputLine;
        boolean firstLine = true;
        String method = "GET";
        String uriStr = "/";

        while ((inputLine = in.readLine()) != null) {
            System.out.println("Received: " + inputLine);
            if (firstLine) {
                String[] tokens = inputLine.split(" ");
                if (tokens.length >= 2) {
                    method = tokens[0];
                    uriStr = tokens[1];
                }
                firstLine = false;
            }
            if (!in.ready()) break;
        }

        Request req = new Request(method, uriStr);
        Response res = new Response();

        byte[] body;
        String headers;

        Route route = Spark.getRoute(req.getPath());
        if (route != null) {
            String result = route.handle(req, res);
            body = result.getBytes("UTF-8");
            headers = HttpResponseBuilder.build(res.getStatus(), res.getContentType(), body);

        } else {
            byte[] staticFile = StaticFileHandler.getFile(req.getPath());
            if (staticFile != null) {
                body = staticFile;
                String contentType = StaticFileHandler.getContentType(req.getPath());
                headers = HttpResponseBuilder.build(200, contentType, body);

            } else {
                String helloBody = "<!DOCTYPE html><html><head><meta charset=\"UTF-8\">"
                        + "<title>Hello</title></head><body><h1>Hello World!</h1></body></html>";
                body = helloBody.getBytes("UTF-8");
                headers = HttpResponseBuilder.build(200, "text/html; charset=UTF-8", body);
            }
        }

        out.write(headers.getBytes("UTF-8"));
        out.write(body);
        out.flush();
        clientSocket.close();
    }
}