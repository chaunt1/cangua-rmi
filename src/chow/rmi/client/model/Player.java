package chow.rmi.client.model;

public class Player {
    private String username;
    private boolean isItMe;
    private boolean moved;

    public Player(String username, boolean isItMe) {
        this.username = username;
        this.isItMe = isItMe;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public boolean isItMe() {
        return isItMe;
    }

    public void setItMe(boolean isItMe) {
        this.isItMe = isItMe;
    }

    public void setMoved(boolean moved) {
        this.moved = moved;
    }

    public boolean getMoved() {
        return this.moved;
    }
}