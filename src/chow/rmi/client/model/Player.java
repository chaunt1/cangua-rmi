package chow.rmi.client.model;

public class Player {
    private String username;
    private boolean isItMe;
    private boolean moved;
    private boolean canMove;

    public Player(String username, boolean isItMe) {
        this.username = username;
        this.isItMe = isItMe;
        this.moved = false;
        this.canMove = false;
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

    public void setCanMove(boolean canMove) {
        this.canMove = canMove;
    }

    public boolean getCanMove() {
        return this.canMove;
    }
}