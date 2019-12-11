package chow.rmi.client;

public class Stable extends Game {
    private int number = 0;
    static final int NO_HORSE = -1;
    private boolean id[] = new boolean[Player.NUMBER_HORSE];

    Stable(int color) {
        number = Player.NUMBER_HORSE;
        for (int i = 0; i < Player.NUMBER_HORSE; i++) {
            id[i] = true;
        }
    }

    public void add(int id) {
        number++;
        this.id[id] = true;
    }

    public int getHorse() {
        if(isEmpty()){
            return NO_HORSE;
        }

        for(int i = 0; i < Player.NUMBER_HORSE; i++) {
            if (id[i] == true) {
                return i;
            }
        }

        return NO_HORSE;
    }

    public void remove(int id) {
        number--;
        this.id[id] = false;
    }

    public boolean isEmpty() {
        return number == 0;
    }
}
