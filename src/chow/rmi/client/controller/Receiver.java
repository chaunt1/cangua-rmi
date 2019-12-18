package chow.rmi.client.controller;

import java.awt.Point;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.List;

import chow.rmi.client.model.CurrentGame;
import chow.rmi.server.controller.DispatcherInterface;

public class Receiver implements ReceiverInterface {
    private DispatcherInterface proxy;
    private CurrentGame model;
    public Receiver(CurrentGame model) {
        this.model = model;
    }

    public int connect(String serverIP) {
        try {
            this.proxy = (DispatcherInterface) Naming.lookup("rmi://" + serverIP + "/CaNgua");
			int result = this.proxy.connection((ReceiverInterface)UnicastRemoteObject.exportObject(this,0), this.model.getMyUsername());
			if (result > 0) {
				System.out.println("Bạn là người chơi số " + result + " kết nối!\n");
				return result;
			} else {
				System.out.println("Tên người dùng đã có");
				UnicastRemoteObject.unexportObject(this, false);
				return 0;
			}
        } catch (Exception e) {
            System.err.println("Không thể kết nối tới Server" + e.getMessage());
            return -1;
        }
    }

    @Override
    public boolean isReady() {
		return true;
    }

    @Override
	public void sendConnectedUsers(List<String> connectedUsers)
			throws RemoteException {
		this.model.setConnectedUsers(connectedUsers);
    }

    //Send Result

    @Override
	public void opponentCanPlay(boolean isMyTurn) throws RemoteException {
		this.getModel().setMyTurn(isMyTurn);
    }

    public void startGameWithOpponent(String opponent) throws RemoteException {
		this.proxy.playWith(this.model.getMyUsername(), opponent);
		this.model.setOpponentUsername(opponent);
		this.model.setHost(true);
		this.model.setMyTurn(false);
    }

    @Override
	public void gameStarts(String opponent) throws RemoteException {
		this.model.setOpponentUsername(opponent);
		this.model.setHost(false);
		this.model.setMyTurn(true);
    }

    public boolean isUserReady(String potentialOpponent) throws RemoteException {
		return this.proxy.isUserReady(potentialOpponent);
    }

    public void updateConnectedUsers() throws RemoteException {
		this.proxy.updateConnectedUsers();
    }

    public String getMyUsername() {
		return model.getMyUsername();
    }

    public void setMyUsername(String username) {
		this.model.setMyUsername(username);
    }

    public CurrentGame getModel() {
		return model;
    }

    public void setModel(CurrentGame model) {
		this.model = model;
	}

	@Override
	public void retrieveMovedNotification() throws RemoteException {
		this.model.getOpponent().setMoved(true);
	}
}
