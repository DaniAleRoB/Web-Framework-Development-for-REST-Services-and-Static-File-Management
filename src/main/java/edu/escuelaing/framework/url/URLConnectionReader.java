package edu.escuelaing.framework.url;

import java.io.*;
import java.net.*;
import java.util.*;

/**
 * Demonstrates reading HTTP response headers using URLConnection.
 * Based on the networking tutorial from the course PDF (Figure 2).
 *
 * Connects to a URL, reads and prints all HTTP headers,
 * then saves the response body to resultado.html (Exercise 2 of the PDF).
 *
 * Run: java main.java.edu.escuelaing.framework.url.URLConnectionReader http://www.google.com
 */
public class URLConnectionReader {

    public static void main(String[] args) throws Exception {
        String site = args.length > 0 ? args[0] : "http://www.google.com";

        URL siteURL = new URL(site);

        URLConnection urlConnection = siteURL.openConnection();

        Map<String, List<String>> headers = urlConnection.getHeaderFields();
        Set<Map.Entry<String, List<String>>> entrySet = headers.entrySet();

        System.out.println("=== HTTP Response Headers ===");
        for (Map.Entry<String, List<String>> entry : entrySet) {
            String headerName = entry.getKey();
            if (headerName != null) {
                System.out.print(headerName + ": ");
            }
            List<String> headerValues = entry.getValue();
            for (String value : headerValues) {
                System.out.print(value);
            }
            System.out.println();
        }

        // Read response body and save to resultado.html
        System.out.println("\n=== Saving body to resultado.html ===");
        BufferedReader reader = new BufferedReader(
                new InputStreamReader(urlConnection.getInputStream()));

        try (PrintWriter writer = new PrintWriter(new FileWriter("resultado.html"))) {
            String inputLine;
            while ((inputLine = reader.readLine()) != null) {
                writer.println(inputLine);
            }
        }

        reader.close();
        System.out.println("Done. Open resultado.html in your browser.");
    }
}