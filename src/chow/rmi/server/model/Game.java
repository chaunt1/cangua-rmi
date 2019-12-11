package chow.rmi.server.model;

import java.awt.Point;
import java.io.Serializable;

import chow.rmi.server.controller.Dispatcher;

public class Game implements Serializable {

    private static final long serialVersionUID = 1L;
    private Player playerHost;
    private Player playerClient;

    private boolean isPlayerHostTurn;
    private boolean isGameFinished;
    private Dispatcher dispatcher;

    public Game(Player playerHost, Player playerClient, Dispatcher dispatcher) {
        this.setDispatcher(dispatcher);
        this.playerHost = playerHost;
        this.playerClient = playerClient;

        this.isGameFinished = false;

        this.isPlayerHostTurn = false;

        this.playerHost.setMyTurn(false);
        this.playerClient.setMyTurn(true);

        System.out.println("Game has started");
	}

	public int move(Player player, Point p, boolean moved) {
		Player playerWhoShouldPlay = (isPlayerHostTurn() ? this.getPlayerHost() : this.getPlayerClient());
		if (player.getUsername().equals(playerWhoShouldPlay.getUsername())) {
			if (moved) {
				//
				return 0; //MOVED
			} else {
				return 1; //UNMOVED
			}
		}
		return -1; //ERROR
	}

    public void finishGame() {
        System.out.println("Game finished, good game! " + this.getWinner() + " won the game!");
    }

    public Player turnPlayer() {
        this.isPlayerHostTurn = !this.isPlayerHostTurn;

        this.playerHost.setMyTurn(!this.playerHost.isMyTurn());
        this.playerClient.setMyTurn(!this.playerClient.isMyTurn());

        System.out.println("That's " + this.getCurrentPlayer().getUsername() + "'s turn!");
        return this.getCurrentPlayer();
    }

    // public Cell[][] getLightenedCells() {
	// 	return applyLastCoordinates(this.grid.getLightenedCells());
	// }

	// /**
	//  * Get cells of the game
	//  * @return complete grid
	//  */
	// public Cell[][] getCells() {
	// 	return applyLastCoordinates(this.grid.getCells());
	// }


	// /**
	//  * Apply which cell has been played the last
	//  * @param grid cells of game
	//  * @return the grid with changes applied about the last cell cliqued
	//  */
	// public Cell[][] applyLastCoordinates(Cell[][] grid) {
	//     //Get last played coordinates
	// 	Point lastPlayedClient = this.playerClient.getLastPlayed();
	// 	Point lastPlayedHost = this.playerHost.getLastPlayed();

	// 	//Apply last played coordinates to the result grid
	// 	if (lastPlayedClient != null) {
	// 		grid[lastPlayedClient.x][lastPlayedClient.y].setLast(true);
	// 	}

	// 	if (lastPlayedHost != null) {
	// 		grid[lastPlayedHost.x][lastPlayedHost.y].setLast(true);
	// 	}

	// 	return grid;
    // }

    public boolean isPlayerHost(Player player) {
        return player == this.playerHost;
    }

    public Player getCurrentPlayer() {
        return this.isPlayerHostTurn ? playerHost : playerClient;
    }

    public Player getOpponentPlayer() {
		return this.isPlayerHostTurn ? playerClient : playerHost;
	}

	public Player getPlayerHost() {
		return playerHost;
	}

	public void setPlayerHost(Player playerHost) {
		this.playerHost = playerHost;
	}

	public Player getPlayerClient() {
		return playerClient;
	}

	public void setPlayerClient(Player playerClient) {
		this.playerClient = playerClient;
    }

    // public Grid getGrid() {
	// 	return grid;
	// }

	// public void setGrid(Grid grid) {
	// 	this.grid = grid;
	// }

	public boolean isPlayerHostTurn() {
		return isPlayerHostTurn;
	}

	public void setPlayerHostTurn(boolean isPlayerHostTurn) {
		this.isPlayerHostTurn = isPlayerHostTurn;
	}

	public boolean isGameFinished() {
		return isGameFinished;
	}

	public void setGameFinished(boolean isGameFinished) {
		this.isGameFinished = isGameFinished;
	}

	public Player getWinner() {
		return this.getPlayerClient().didUserWin() ?
				this.getPlayerClient() : this.getPlayerHost();
	}

	public Dispatcher getDispatcher() {
		return dispatcher;
	}

	public void setDispatcher(Dispatcher dispatcher) {
		this.dispatcher = dispatcher;
	}
}
