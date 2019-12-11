package chow.rmi.server.controller;

import java.awt.Point;
import java.net.MalformedURLException;

import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.server.UnicastRemoteObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import chow.rmi.server.model.Player;
import chow.rmi.server.model.Game;
import chow.rmi.client.controller.ReceiverInterface;

public class Dispatcher extends UnicastRemoteObject implements DispatcherInterface {
    private static final long serialVersionUID = 1L;
    private final List<Player> waitingList;
    private final Map<Player, Game> gameList;

    public Dispatcher() throws RemoteException {
		super();
		this.waitingList = new ArrayList<Player>();
		this.gameList = new HashMap<Player, Game>();
		try {
			LocateRegistry.createRegistry(1099);
			Naming.rebind("JMines", this);
			System.out.println("Serveur pret");
		} catch (final RemoteException e) {
			System.err.println("Remote Exception " + e.getMessage());
		} catch (final MalformedURLException e) {
			System.err.println("Malformed " + e.getMessage());
		}
    }

    @Override
    public void playWith(String clientName, String opponentName) throws RemoteException {
		Player playerClient = getPlayerFromName(clientName);
		Player playerOpponent = getPlayerFromName(opponentName);

		Game game = new Game(playerClient, playerOpponent, this);
		playerOpponent.getRMIUser().gameStarts(clientName);
		sendResultToBothPlayers(game);

		this.gameList.put(playerClient, game);
		this.gameList.put(playerOpponent, game);
	}

	@Override
	public int confirmClickedCoordinates(String username, Point p, boolean moved) throws RemoteException {
		Player player = this.getPlayerFromName(username);
		Game game = this.gameList.get(player);

		if (game != null) {
			int result = game.move(player, p, moved);

			if (result >= 0) {
				if (moved) {
					ReceiverInterface opponent = game.getOpponentPlayer().getRMIUser();
					opponent.retrieveMovedNotification(); //edit
				}

				sendResultToBothPlayers(game);

				if (moved == 0) {
					game.turnPlayer().getRMIUser().opponentCanPlay(true);
					game.getOpponentPlayer().getRMIUser().opponentCanPlay(false);
				}
			}
			return result;
		} else {
			return -1; //ERROR
		}
	}

	public void sendResultToBothPlayers(Game game) throws RemoteException {
		int myStable;
		int opponentStable;

		if ()
	}
}
