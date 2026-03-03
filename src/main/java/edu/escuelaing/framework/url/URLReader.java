package edu.escuelaing.framework.url;

import java.io.*;
import java.net.*;

/**
 * Demonstrates reading a web page using Java's URL class.
 * Based on the networking tutorial from the course PDF (Figure 1).
 *
 * Connects to google.com and prints the HTML response body to stdout.
 *
 * Run: java main.java.edu.escuelaing.framework.url.URLReader
 */
public class URLReader {

    public static void main(String[] args) throws Exception {
        URL google = new URL("http://www.google.com/");

        try (BufferedReader reader =
                     new BufferedReader(new InputStreamReader(google.openStream()))) {
            String inputLine;
            while ((inputLine = reader.readLine()) != null) {
                System.out.println(inputLine);
            }
        } catch (IOException x) {
            System.err.println(x);
        }
    }
}