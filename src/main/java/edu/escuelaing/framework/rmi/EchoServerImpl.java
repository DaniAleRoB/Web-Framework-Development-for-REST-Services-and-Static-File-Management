package edu.escuelaing.framework.rmi;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

/**
 * RMI server implementation of the EchoRemote interface.
 * Based on the networking tutorial from the course PDF (Figure 10).
 *
 * Publishes the echo service in the RMI registry under the name "echoServer".
 * Returns the received message prefixed with "desde el servidor: ".
 *
 * How to run:
 *   1. Start the RMI registry:  cd target/classes && rmiregistry 23000
 *   2. Run this class:          java main.java.edu.escuelaing.framework.rmi.EchoServerImpl
 *   3. Run the client:          java main.java.edu.escuelaing.framework.rmi.EchoClientRMI
 */
public class EchoServerImpl implements EchoRemote {

    public EchoServerImpl(String ipRMIregistry, int puertoRMIregistry,
                          String nombreDePublicacion) {
        try {
            EchoRemote echoServer =
                    (EchoRemote) UnicastRemoteObject.exportObject(this, 0);

            Registry registry = LocateRegistry.getRegistry(ipRMIregistry, puertoRMIregistry);
            registry.rebind(nombreDePublicacion, echoServer);

            System.out.println("Echo RMI server ready on "
                    + ipRMIregistry + ":" + puertoRMIregistry
                    + " as '" + nombreDePublicacion + "'");

        } catch (Exception e) {
            System.err.println("Echo server exception:");
            e.printStackTrace();
        }
    }

    @Override
    public String echo(String cadena) throws RemoteException {
        return "desde el servidor: " + cadena;
    }

    public static void main(String[] args) {
        EchoServerImpl ec = new EchoServerImpl("127.0.0.1", 23000, "echoServer");
    }
}