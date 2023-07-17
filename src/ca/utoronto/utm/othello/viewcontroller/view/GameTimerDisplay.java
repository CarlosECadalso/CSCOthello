package ca.utoronto.utm.othello.viewcontroller.view;

import ca.utoronto.utm.othello.model.OthelloBoard;
import ca.utoronto.utm.othello.viewcontroller.GameModel;
import ca.utoronto.utm.othello.viewcontroller.OthelloTimerHandler;
import ca.utoronto.utm.util.*;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

/**
 * Display the specific players remaining time
 * 
 * @author Team MarvelStudios
 */
class TimerLabel extends Label implements Observer {
	private char player;

	TimerLabel(char p) {
		this.player = p;
		this.setStyle("-fx-background-color: blanchedalmond;");
	}

	public void update(Observable o) {
		OthelloTimerHandler h = (OthelloTimerHandler) o;
		this.setText(h.getTimeLeft(player));

	}
}

/**
 * Show the time remaining for both users
 * 
 * @author Team MarvelStudios
 */
class GameTimerDisplay extends VBox {

	GameTimerDisplay(GameModel model) {
		this.setStyle("-fx-background-color: blanchedalmond;");

		TimerLabel player1 = new TimerLabel(OthelloBoard.P1);
		TimerLabel player2 = new TimerLabel(OthelloBoard.P2);

		this.getChildren().addAll(player1, player2);
		model.getTimer().attachLabels(player1, player2);
	}

}