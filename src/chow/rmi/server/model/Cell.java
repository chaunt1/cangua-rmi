package chow.rmi.server.model;

import java.io.Serializable;

public class Cell implements Serializable {
    private static final long serialVersionUID = 1L;

    private Boolean isDiscovered;
    private boolean isMine;
    private boolean isLast;

    public Cell() {
        this.isDiscovered = null;
        this.isMine = false;
    }

    public Cell(boolean statut, boolean isMine, boolean isLast) {
        this.isDiscovered = statut;
        this.isMine = isMine;
        this.isLast = isLast;
    }

    public Boolean isDiscovered() {
        return isDiscovered;
    }

    public void setDiscovered(boolean discoveredByHost) {
        this.isDiscovered = discoveredByHost;
    }

    public boolean isMine() {
        return isMine;
    }

    public void setMine(boolean isMine) {
        this.isMine = isMine;
    }

    public boolean isLast() {
        return isLast;
    }

    public void setLast(boolean isLast) {
        this.isLast = isLast;
    }

    public String toString(boolean beDiscovered) {
        if (this.isDiscovered() != null || beDiscovered) {
            if (this.isMine()) {
                return "x";
            } else {
                return "y";
            }
        } else return "o";
    }
}