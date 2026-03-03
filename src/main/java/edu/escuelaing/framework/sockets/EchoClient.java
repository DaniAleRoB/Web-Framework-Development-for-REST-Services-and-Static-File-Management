package edu.escuelaing.framework.sockets;

import java.io.*;
import java.net.*;

/**
 * Simple echo client using TCP sockets.
 * Based on the networking tutorial from the course PDF (Figure 3).
 *
 * Connects to EchoServer on localhost:35000.
 * Reads lines from stdin, sends them to the server, and prints the echo response.
 *
 * Run EchoServer first, then run this client.
 * Type messages and press Enter. Type "Bye." to exit.
 */
public class EchoClient {

    public static void main(String[] args) throws IOException {
        Socket echoSocket = null;
        PrintWriter out = null;
        BufferedReader in = null;

        try {
            echoSocket = new Socket("127.0.0.1", 35000);
            out = new PrintWriter(echoSocket.getOutputStream(), true);
            in  = new BufferedReader(new InputStreamReader(echoSocket.getInputStream()));
        } catch (UnknownHostException e) {
            System.err.println("Don't know about host!");
            System.exit(1);
        } catch (IOException e) {
            System.err.println("Couldn't get I/O for the connection to localhost.");
            System.exit(1);
        }

        BufferedReader stdIn = new BufferedReader(new InputStreamReader(System.in));
        String userInput;

        System.out.println("Connected to EchoServer. Type messages (type 'Bye.' to exit):");
        while ((userInput = stdIn.readLine()) != null) {
            out.println(userInput);
            System.out.println("echo: " + in.readLine());
            if (userInput.equals("Bye.")) break;
        }

        out.close();
        in.close();
        stdIn.close();
        echoSocket.close();
    }
}