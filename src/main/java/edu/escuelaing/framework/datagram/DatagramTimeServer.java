package edu.escuelaing.framework.datagram;

import java.io.IOException;
import java.net.*;
import java.util.Date;
import java.util.logging.*;

/**
 * UDP server that responds with the current server time.
 * Based on the networking tutorial from the course PDF (Figure 6).
 *
 * Listens on UDP port 4445. When it receives any datagram,
 * it replies with the current date/time as a string.
 *
 * Run this first, then run DatagramTimeClient.
 */
public class DatagramTimeServer {

    DatagramSocket socket;

    public DatagramTimeServer() {
        try {
            socket = new DatagramSocket(4445);
            System.out.println("DatagramTimeServer listening on UDP port 4445...");
        } catch (SocketException ex) {
            Logger.getLogger(DatagramTimeServer.class.getName())
                    .log(Level.SEVERE, null, ex);
        }
    }

    public void startServer() {
        byte[] buf = new byte[256];
        try {
            DatagramPacket packet = new DatagramPacket(buf, buf.length);
            socket.receive(packet);
            System.out.println("Request received from " + packet.getAddress());

            String dString = new Date().toString();
            buf = dString.getBytes();

            InetAddress address = packet.getAddress();
            int port = packet.getPort();
            packet = new DatagramPacket(buf, buf.length, address, port);
            socket.send(packet);

            System.out.println("Sent time: " + dString);

        } catch (IOException ex) {
            Logger.getLogger(DatagramTimeServer.class.getName())
                    .log(Level.SEVERE, null, ex);
        }
        socket.close();
    }

    public static void main(String[] args) {
        DatagramTimeServer ds = new DatagramTimeServer();
        ds.startServer();
    }
}