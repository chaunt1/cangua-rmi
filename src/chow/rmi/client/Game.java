package chow.rmi.client;

import java.util.concurrent.Semaphore;

import javax.swing.JOptionPane;

public class Game {

	static final int BLUE = 1;
	static final int YELLOW = 2;
	static final int GREEN = 3;
	static final int RED = 4;
	static final int DISTANCE = 45;

	static boolean diePhaseFlag = true;
	static boolean horsePhaseFlag = false;
	static Semaphore diePhaseSema = new Semaphore(0);
	static Semaphore horsePhaseSema = new Semaphore(0);

	public void showError(String error) {
		JOptionPane.showMessageDialog(null, error);
	}

	public void sleep(int miliseconds) {
		try {
			Thread.sleep(miliseconds);
		} catch (InterruptedException ex) {
			Thread.currentThread().interrupt();
		}
	}
}
