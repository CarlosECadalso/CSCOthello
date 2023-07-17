package ca.utoronto.utm.othello.viewcontroller.view;

import ca.utoronto.utm.othello.model.OthelloBoard;
import ca.utoronto.utm.othello.viewcontroller.GameModel;
import ca.utoronto.utm.util.Observable;
import ca.utoronto.utm.util.Observer;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;

/**
 * Show the current state of the game
 * 
 * @author Team MarvelStudios
 */
class GameStatusDisplay extends HBox implements Observer {
	private GameModel model;
	private Label gameStatus;

	GameStatusDisplay(GameModel model) {
		this.model = model;
		this.setStyle("-fx-background-color: blanchedalmond;");
		gameStatus = new Label(getLabel());
		this.getChildren().add(gameStatus);
	}

	/**
	 * deal with the correct string to display for a winner, tie, or ongoing game.
	 * 
	 * @return a string for the game's status
	 */
	private String getLabel() {
		int firstScore = model.getOthello().getCount(OthelloBoard.P1);
		int secondScore = model.getOthello().getCount(OthelloBoard.P2);
		if (model.isGameOver()) {
			if (model.getTimer().isATimers0()) {
				return model.getTimer().whichTimerIs0() == OthelloBoard.P1 ? "P2 Wins" : "P1 Wins";
			} else if (firstScore != secondScore) {
				return firstScore > secondScore ? "P1 Wins" : "P2 Wins";
			}
			return ("No one wins, it's a tie haha!");
		} else {
			return ("Game Status: Ongoing game");
		}
	}

	@Override
	public void update(Observable o) {
		this.gameStatus.setText(getLabel());
	}
}
