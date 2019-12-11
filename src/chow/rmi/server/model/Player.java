package chow.rmi.server.model;

import java.awt.Point;

import chow.rmi.client.controller.ReceiverInterface;

public class Player {
    private String username;
    private boolean myTurn;
    
    private ReceiverInterface RMIUser;
    private Point lastplayed; //last cell played

    public Player(String username) {
        this.username = username;
    }

    public boolean didUserWin() {
        return true;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public boolean isMyTurn() {
        return myTurn;
    }

    public void setMyTurn(boolean myTurn) {
        this.myTurn = myTurn;
    }

    public ReceiverInterface getRMIUser() {
        return RMIUser;
    }

    public void setRMIUser(ReceiverInterface rMIUser) {
        RMIUser = rMIUser;
    }

    public Point getLastplayed() {
        return lastplayed;
    }

    public void setLastplayed(Point lastplayed) {
        this.lastplayed = lastplayed;
    }

    @Override
    public String toString() {
        return "Player" + this.username
            + (this.getRMIUser() != null ? "RMI ok"
                + this.getRMIUser() : "RMI null");
    }
}
