package chow.rmi.client;

import java.awt.BorderLayout;
import java.rmi.RemoteException;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JFrame;

import chow.rmi.client.controller.Receiver;
import chow.rmi.client.model.Player;
import chow.rmi.client.GameSession;
import chow.rmi.client.PlayersPanel;

public class ClientWindow extends JFrame implements Observer {
    private static final long serialVersionUID = 1L;
    private static ClientWindow instance;
    private Receiver controller;
    private GameGraphic gamePanel;
    private PlayersPanel playersPanel;

    public ClientWindow(Receiver controller) throws RemoteException {
        ClientWindow.setInstance(this);
        this.setController(controller);
		this.setTitle("Cờ Cá Ngựa");
		this.setSize(1210, 910);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setLocationRelativeTo(null);
		this.setLayout(new BorderLayout());

		this.gamePanel = new GameGraphic();
		this.playersPanel = new PlayersPanel();

		//this.getContentPane().add(gamePanel, BorderLayout.WEST);
		this.getContentPane().add(playersPanel, BorderLayout.EAST);

		this.setResizable(false);
		this.setVisible(true);
    }

    @Override
	public void update(Observable arg0, Object message) {
		if (message instanceof List) {
			//change on connected users
			this.getPlayersPanel().update(arg0, message);
        }
        // else if (message instanceof Player || message instanceof Boolean) {
		// 	//change on game panel
		// 	this.getGamePanel().update(arg0, message);
		// }
    }

    public Receiver getController() {
		return this.controller;
	}

	public void setController(Receiver controller) {
		this.controller = controller;
	}

    public GameGraphic getGamePanel() {
        return gamePanel;
    }

    public void setGamePanel(GameGraphic gamePanel) {
        this.gamePanel = gamePanel;
    }

	public PlayersPanel getPlayersPanel() {
		return playersPanel;
	}

	public void setPlayersPanel(PlayersPanel playersPanel) {
		this.playersPanel = playersPanel;
	}

    public static ClientWindow getInstance() {
		return instance;
    }

    public static void setInstance(ClientWindow instance) {
		ClientWindow.instance = instance;
	}
}
