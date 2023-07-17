package ca.utoronto.utm.othello.viewcontroller.view;

import ca.utoronto.utm.othello.model.Move;
import ca.utoronto.utm.othello.model.OthelloBoard;
import ca.utoronto.utm.othello.viewcontroller.GameModel;
import ca.utoronto.utm.util.Observable;
import ca.utoronto.utm.util.Observer;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;

/**
 * A class that displays the next player who will play
 * 
 * @author Team MarvelStudios
 * 
 */
class NextDisplay extends HBox implements Observer {
	private GameModel model;
	private Label label = new Label();
	private Label prev = new Label();

	NextDisplay(GameModel model) {
		this.model = model;
		this.getChildren().add(label);
		this.getChildren().add(prev);
		this.update(model);
		this.setStyle("-fx-background-color: blanchedalmond;");
	}

	/**
	 * An update method for displaying the actual player token of who's turn is it
	 * to play.
	 * 
	 * @param o
	 */
	@Override
	public void update(Observable o) {
		if (!this.model.isGameOver()) {
			this.label.setText("Plays Next	");
			TokenImage image;
			if (model.getOthello().getWhosTurn() == OthelloBoard.P1) {
				image = new TokenImage(TokenImage.BLACK);
			} else {
				image = new TokenImage(TokenImage.WHITE);
			}
			image.setFitHeight(10);
			image.setFitWidth(10);
			this.label.setGraphic(image);
			Move last = this.model.getLastMove();
			if (last != null) {
				String who = "";
				if (this.model.getCurrentPlayer().getPlayer() == OthelloBoard.P1) {
					who = "Player 2";
				} else {
					who = "Player 1";
				}
				this.prev.setText(who + " moved at (" + (last.getRow()+1) + ", " + (last.getCol()+1) + ")");
			} else {
				this.prev.setText("");
			}

		} else {
			this.label.setGraphic(null);
			this.label.setText("");
		}
	}

}