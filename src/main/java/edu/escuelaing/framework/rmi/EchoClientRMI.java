package edu.escuelaing.framework.rmi;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

/**
 * RMI client that connects to EchoServerImpl and calls echo() remotely.
 * Based on the networking tutorial from the course PDF (Figure 11).
 *
 * Looks up "echoServer" in the RMI registry and invokes echo() on the remote object.
 *
 * How to run:
 *   1. Start the RMI registry:  cd target/classes && rmiregistry 23000
 *   2. Start the server:        java main.java.edu.escuelaing.framework.rmi.EchoServerImpl
 *   3. Run this client:         java main.java.edu.escuelaing.framework.rmi.EchoClientRMI
 */
public class EchoClientRMI {

    public void ejecutaServicio(String ipRmiregistry, int puertoRmiRegistry,
                                String nombreServicio) {
        try {
            Registry registry = LocateRegistry.getRegistry(ipRmiregistry, puertoRmiRegistry);
            EchoRemote echoServer = (EchoRemote) registry.lookup(nombreServicio);
            System.out.println(echoServer.echo("Hola, como estas?"));
        } catch (Exception e) {
            System.err.println("Hay un problema:");
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        EchoClientRMI ec = new EchoClientRMI();
        ec.ejecutaServicio("127.0.0.1", 23000, "echoServer");
    }
}