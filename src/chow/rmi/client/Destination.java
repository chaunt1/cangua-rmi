package chow.rmi.client;

public class Destination extends Game {

    static final int NO_RANK = -1;
    static final int NUMBER_RANK= 6;
    static final int NO_HORSE = -1;
    private int peek = NUMBER_RANK -1;
    private int rank[] = new int[NUMBER_RANK];

    Destination(int color) {
        for(int i=0; i < NUMBER_RANK; i++) {
            rank[i] = NO_HORSE;
        }
    }

    public boolean isWin() {
        return peek == 1;
    }

    public int getRank(int rank) {
        return this.rank[rank];
    }

    public boolean setDestination(int rank, int newRank, HorseSea horse) {
        newRank--;
        if (newRank > peek) {
            showError("Không thể thăng hạng quân cờ này");
            return false;
        }

        if (rank == NO_RANK) {
            for(int i=0; i<= newRank;i++) {
                if (this.rank[i] != NO_HORSE) {
                    showError("Không thể thăng hạng quân cờ này");
                    return false;
                }
            }

            this.rank[newRank] = horse.getId();
            horse.setRank(newRank);
        } else {
            if (newRank - rank == 1 && this.rank[newRank] == NO_HORSE) {
                this.rank[newRank] = this.rank[rank];
                horse.setRank(newRank);
                this.rank[rank] = NO_HORSE;
            } else {
                showError("Không thể thăng hạn quân này.");
            }
        }

        if (this.rank[peek] != NO_HORSE) {
            peek--;
        }

        return true;
    }
}
