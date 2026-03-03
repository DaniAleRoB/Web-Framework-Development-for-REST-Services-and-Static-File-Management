package main.java.edu.escuelaing.framework;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Builds properly formatted HTTP/1.1 response headers.
 * Follows conventions from https://developer.mozilla.org/en-US/docs/Web/HTTP/Guides/Overview
 */
public class HttpResponseBuilder {

    /**
     * Builds the HTTP response header string.
     *
     * @param status      HTTP status code (200, 404, etc.)
     * @param contentType MIME type of the response body
     * @param body        the response body bytes (used to compute Content-Length)
     * @return formatted HTTP/1.1 header string ending with \r\n\r\n
     */
    public static String build(int status, String contentType, byte[] body) {
        String statusText = switch (status) {
            case 200 -> "OK";
            case 404 -> "Not Found";
            case 500 -> "Internal Server Error";
            default  -> "Unknown";
        };

        return "HTTP/1.1 " + status + " " + statusText + "\r\n"
                + "Date: " + ZonedDateTime.now().format(DateTimeFormatter.RFC_1123_DATE_TIME) + "\r\n"
                + "Server: JavaWebFramework/1.0\r\n"
                + "Content-Length: " + body.length + "\r\n"
                + "Content-Type: " + contentType + "\r\n"
                + "Connection: close\r\n"
                + "\r\n";
    }
}