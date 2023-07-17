package ca.utoronto.utm.othello.viewcontroller.view;

import ca.utoronto.utm.othello.model.*;
import ca.utoronto.utm.othello.viewcontroller.GameModel;
import ca.utoronto.utm.util.Observable;
import ca.utoronto.utm.util.Observer;
import javafx.scene.control.Label;

/**
 * A class that displays the scores of each player.
 * 
 * @author Team MarvelStudios
 */
class PlayerDisplay extends Label implements Observer {

	private String P1type;
	private String P2type;
	private GameModel othello;

	public PlayerDisplay(GameModel othello) {
		this.P1type = "";
		this.P2type = "";
		this.othello = othello;
		this.setStyle("-fx-background-color: blanchedalmond;");
	}

	/**
	 * @return the token count for the specified user
	 */
	private String getScore(char player) {
		if (this.P1type != "" && this.P2type != "") {
			return Integer.toString(othello.getOthello().getCount(player));
		}
		return "";
	}

	/**
	 * An update method that displays the token count and updates it whenever a
	 * player makes a move.
	 * 
	 * @param o
	 */
	public void update(Observable o) {
		this.P1type = othello.getPlayer(OthelloBoard.P1).toString();
		this.P2type = othello.getPlayer(OthelloBoard.P2).toString();
		this.setText("Score: \t" + "P1: (" + this.P1type + "): " + this.getScore(OthelloBoard.P1) + "\n\t\t" + "P2: ("
				+ this.P2type + "): " + this.getScore(OthelloBoard.P2));
	}
}
