package chow.rmi.server;

import java.rmi.AlreadyBoundException;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class Server {
    private static final int PORT = 1099;
    private static Registry registry;

    public static void startRegistry() throws RemoteException {
        registry = LocateRegistry.createRegistry(PORT);
    }

    public static void registerObject(String name, Remote remoteObj)
        throws RemoteException, AlreadyBoundException {
            registry.bind(name, remoteObj);
            System.out.println("Registered: " + name + " -> ");
        }

        public static void main(String[] args) throws Exception {
            System.out.println("Server Starting ...");
            startRegistry();

            System.out.println("Server Started !");
        }
}
