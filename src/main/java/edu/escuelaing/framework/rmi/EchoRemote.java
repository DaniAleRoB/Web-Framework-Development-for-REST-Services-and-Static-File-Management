package edu.escuelaing.framework.rmi;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * Remote interface for the RMI Echo service.
 * Based on the networking tutorial from the course PDF (Figure 9).
 *
 * Defines the contract of services that can be called remotely.
 * All methods must declare RemoteException.
 */
public interface EchoRemote extends Remote {
    public String echo(String cadena) throws RemoteException;
}