package edu.escuelaing.framework.datagram;

import java.io.IOException;
import java.net.*;
import java.util.logging.*;

/**
 * UDP client that requests the current time from DatagramTimeServer.
 * Based on the networking tutorial from the course PDF (Figure 7).
 *
 * Sends a UDP datagram to localhost:4445 and prints the time response.
 *
 * Run DatagramTimeServer first, then run this client.
 */
public class DatagramTimeClient {

    public static void main(String[] args) {
        try {
            DatagramSocket socket = new DatagramSocket();
            byte[] buf = new byte[256];

            InetAddress address = InetAddress.getByName("127.0.0.1");
            DatagramPacket packet = new DatagramPacket(buf, buf.length, address, 4445);
            socket.send(packet);
            System.out.println("Request sent to DatagramTimeServer...");

            packet = new DatagramPacket(buf, buf.length);
            socket.receive(packet);

            String received = new String(packet.getData(), 0, packet.getLength());
            System.out.println("Server time: " + received);

            socket.close();

        } catch (SocketException ex) {
            Logger.getLogger(DatagramTimeClient.class.getName())
                    .log(Level.SEVERE, null, ex);
        } catch (UnknownHostException ex) {
            Logger.getLogger(DatagramTimeClient.class.getName())
                    .log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(DatagramTimeClient.class.getName())
                    .log(Level.SEVERE, null, ex);
        }
    }
}