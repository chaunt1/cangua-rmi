package chow.rmi.client;

import java.rmi.RemoteException;

import chow.rmi.client.ClientWindow;
import chow.rmi.client.WelcomeWindow;
import chow.rmi.client.controller.Receiver;
import chow.rmi.client.model.CurrentGame;

public class Client {
    private static WelcomeWindow welcomeView;
    private static CurrentGame model;
    private static Receiver controller;
    private static ClientWindow clientView;

    public static void main(String[] args) throws Exception {
        showConnectionWindow();
    }

    public static void showConnectionWindow() {
        model = new CurrentGame();
        controller = new Receiver(model);
        welcomeView = new WelcomeWindow();
        welcomeView.setController(controller);
    }

    public static void showMainWindow() throws RemoteException {
        clientView = new ClientWindow(controller);
        model.addObserver(clientView);
        controller.getModel().updateConnectedUsers();
    }
}
