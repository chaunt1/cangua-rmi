package chow.rmi.server.controller;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.awt.Point;
import chow.rmi.client.controller.ReceiverInterface;

public interface DispatcherInterface extends Remote {
    public int connection(ReceiverInterface client, String username) throws RemoteException;
    public void playWith(String clientName, String opponentName) throws RemoteException;
    public boolean isUserReady(String potentialOpponent) throws RemoteException;
    public int confirmClickedCoordinates(String username, Point p, boolean bombUsed) throws RemoteException;
    public void updateConnectedUsers() throws RemoteException;
}
