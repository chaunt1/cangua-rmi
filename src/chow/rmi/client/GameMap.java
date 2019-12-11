package chow.rmi.client;

public class GameMap extends Game {

    private Player player[];
    private final int map[] = new int[NUMBER_NODE];

    static final int NUMBER_NODE = 56;
    private final int NO_HORSE = 0;

    private final int DES[]= { -1, 55, 13, 27, 41 };
    private final int START[]= { -1, 0, 14, 28, 42 };
    private int numberPlayer;

    GameMap() {
        numberPlayer = 4;
        player = new Player[numberPlayer + 1];

        for(int color = 1; color <= numberPlayer; color++) {
            player[color] = new Player(color);
        }
    }

    public int getNumberPlayer() {
        return numberPlayer;
    }

    public int getMap(int index) {
        return map[index];
    }

    public Player[] getPlayer() {
        return player;
    }

    public boolean xuatQuan (int color) {
        int idHorse = player[color].stable.getHorse();

        if (idHorse == Stable.NO_HORSE) {
            return false;
        }

        if (setMap(color, idHorse, START[color], 0)) {
            player[color].stable.remove(idHorse);
            player[color].addHorse(idHorse);
            return true;
        }

        return false;
    }

    public void addPlayerListener(int color, int steps) {
        player[color].addMouseListener(this, steps);
    }

    public void removePlayerListener(int color) {
        player[color].removeMouseListener();
    }

    public boolean setMap(int color, int idHorse, int start, int steps) {
        if (start == DES[color]) {
            if (player[color].destination.setDestination(Destination.NO_RANK, steps, player[color].horse[idHorse])) {
                player[color].horse[idHorse].toFinish();
                map[start] = NO_HORSE;
                return true;
            } else return false;
        }

        if ( start < DES[color] && start + steps > DES[color]) {
            showError("Quân cờ này không thể di chuyển, vượt quá đích đến!");
            return false;
        }

        for(int step = 1; step < steps; step++) {
            if (map[(start + step) % NUMBER_NODE] != NO_HORSE) {
                showError("Quân cờ này không thể di chuyển, có quân cờ cản trước mặt!");
                return false;
            }
        }

        int pos = (start + steps) % NUMBER_NODE;

        if (map[pos] == NO_HORSE) {
            map[start] = NO_HORSE;
            map[pos] = color * 10 + idHorse;
        } else if(map[pos] / 10 == color) {
            showError("Quân cờ này không thể di chuyển, đích đến là quân cùng màu.");
            return false;
        } else if (map[pos] / 10 < 5) {
            int victimPlayer = map[pos] / 10;
            int victimHorse = map[pos] % 10;

            player[victimPlayer].removeHorse(victimHorse);
            player[victimPlayer].stable.add(victimHorse);

            map[start] = NO_HORSE;
            map[pos] = color * 10 + idHorse;
        }

        return true;
    }

    public boolean isWin() {
        for (int i = 1; i <= numberPlayer; i++) {
            if(player[i].isWin()) {
                showError("Player " + i + " is winner!");
                return true;
            }
        }

        return false;
    }
}
