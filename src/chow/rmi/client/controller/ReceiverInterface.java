package chow.rmi.client.controller;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

public interface ReceiverInterface extends Remote {
    public boolean isReady() throws RemoteException;
    public void sendConnectedUsers(List<String> connectedPlayers) throws RemoteException;
    public void gameStarts(String opponent) throws RemoteException;
    public void opponentCanPlay(boolean isMyTurn) throws RemoteException;
    public void retrieveMovedNotification() throws RemoteException;
}
