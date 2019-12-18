package chow.rmi.server;

import java.rmi.RemoteException;

import chow.rmi.server.controller.Dispatcher;

public class Server {
        public static void main(String[] args) throws RemoteException {
            @SuppressWarnings("unused")
            Dispatcher dispatcher = new Dispatcher();
        }
}
