package chow.rmi.client.model;

import java.util.List;
import java.util.Observable;

import chow.rmi.server.model.Cell;

public class CurrentGame extends Observable {
    private Player me;
    private Player opponent;
    private boolean myTurn;
//    private Cell[][] cells;
    private List<String> connectedUsers;
    private boolean isHost;

    public CurrentGame() {
        this.me = new Player("", true);
        this.opponent = new Player("", false);
    }

    public Player getMe() {
        return me;
    }

    public Player getOpponent() {
        return opponent;
    }

    public boolean isMyTurn() {
        return myTurn;
    }

    public void setMyTurn(boolean myTurn) {
        this.myTurn = myTurn;
        this.doNotify(this.myTurn);
    }

    public boolean isHost() {
        return isHost;
    }

    public void setHost(boolean isHost) {
        this.isHost = isHost;
    }

    // public Cell[][] getCells() {
    //     return cells;
    // }

    // public void setCells(Cell[][] cells) {
    //     this.cells = cells;
    //     this.doNotify(this.cells);
    // }

    public String getMyUsername() {
        return me.getUsername();
    }

    public void setMyUsername(String username) {
        this.me.setUsername(username);
        this.doNotify(username);
    }

    public String getOpponentUsername() {
        return this.me.getUsername();
    }

    public void setOpponentUsername(String opponentUsername) {
        this.opponent.setUsername(opponentUsername);
        this.doNotify(opponentUsername);
    }

    public List<String> getConnectedUsers() {
        return connectedUsers;
    }

    public void setConnectedUsers(List<String> connectedUsers) {
        this.connectedUsers = connectedUsers;
        this.doNotify(this.connectedUsers);
    }

    public void updateConnectedUsers() {
        this.doNotify(this.connectedUsers);
    }

    public void doNotify(Object obj) {
        setChanged();
        notifyObservers(obj);
    }
}