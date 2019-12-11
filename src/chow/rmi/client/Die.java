package chow.rmi.client;

import java.util.Random;

public class Die extends Game {
    private int steps = 0;

    public void thrown() {
        Random random = new Random();
        steps = (random.nextInt(6) + 1);
    }

    public int getSteps() {
        return steps;
    }
}
