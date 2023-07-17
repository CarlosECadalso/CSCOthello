package ca.utoronto.utm.othello.viewcontroller.view;

import ca.utoronto.utm.util.Observer;

import ca.utoronto.utm.util.Observable;
import javafx.animation.FadeTransition;
import javafx.scene.control.Button;
import javafx.scene.paint.Color;
import javafx.scene.layout.Pane;

import javafx.scene.shape.Circle;
import javafx.util.Duration;
import ca.utoronto.utm.othello.model.OthelloBoard;
import ca.utoronto.utm.othello.viewcontroller.GameModel;

/**
 * Represents a button on the othello board and it's set token
 * 
 * @author Team MarvelStudios
 */
class BoardButton extends Button implements Observer {
	private int row, col;
	private GameModel model;
	private static final int BUTTON_HEIGHT = 40;
	private static final int BUTTON_WIDTH = 55;

	/**
	 * crate a new BoardButton at the specified location
	 */
	BoardButton(int row, int col, GameModel model) {
		super();
		this.row = row;
		this.col = col;
		this.model = model;

		this.setMinHeight(BUTTON_HEIGHT);
		this.setMaxHeight(BUTTON_HEIGHT);
		this.setMinWidth(BUTTON_WIDTH);
		this.setMaxWidth(BUTTON_WIDTH);

	}

	public void update(Observable o) {

		char player = this.model.getOthello().getToken(this.row, this.col);
		if (player != OthelloBoard.EMPTY) {
			Circle circle = new Circle();
			circle.setCenterX((int) (BUTTON_HEIGHT / 2));
			circle.setCenterY((int) (BUTTON_WIDTH / 3.5));
			circle.setRadius(13.0f);
			circle.setOpacity(0);

			if (player == OthelloBoard.P1) {
				circle.setFill(Color.BLACK);
				circle.setStroke(Color.WHITE);
			} else if (player == OthelloBoard.P2) {
				circle.setFill(Color.WHITE);
				circle.setStroke(Color.BLACK);

			}

			this.fadeChange(circle);

		} else {

			this.setGraphic(new TokenImage(TokenImage.EMPTY));
			this.setStyle("-fx-background-color: lavender; -fx-border-color: lightcoral");

		}

		this.highlight();

	}

	/**
	 * Check if this button should be highlighted with a hint
	 */
	private void highlight() {

		boolean isHint = this.model.getHintModel().isHint(row, col);
		boolean isMove = this.model.getHintModel().isMove(row, col);

		if (isHint) {
			this.setStyle("-fx-background-color: blue");
		}

		if ((!isMove && !isHint)) {
			this.setStyle("-fx-background-color: lavender; -fx-border-color: lightcoral");
		}
		if (isMove && !isHint) {
			this.setStyle("-fx-background-color: honeydew; -fx-border-color: lightcoral");
		}

	}

	/**
	 * Animate graphics if necessary
	 */
	private void fadeChange(Circle circle) {

		if (this.getGraphic() != null && this.getGraphic() instanceof Pane) {

			Pane oldPane = (Pane) this.getGraphic();

			// If the graphic is not empty the create a circle of the opposite color.
			if (oldPane.getChildren().size() > 0) {
				Color prevColor = (Color) ((Circle) oldPane.getChildren().get(0)).getFill();
				if (prevColor != circle.getFill()) {
					// Set a fading animation effect to the circle to fade out then fade in the new
					// circle.
					FadeTransition ft = new FadeTransition(Duration.millis(225), circle);
					ft.setFromValue(0.0);
					ft.setToValue(1.0);
					// set the delay based on this buttons proximity to the move which animated it
					ft.setDelay(new Duration(this.model.getAnimationDelay(row, col)));

					Pane pane = new Pane(circle);
					this.setGraphic(pane);
					ft.play();
				}
			}

		} else {
			circle.setOpacity(1); // no animation
			Pane pane = new Pane(circle);
			this.setGraphic(pane);
		}

	}

	/**
	 * @return the button's row
	 */
	public int getRow() {
		return this.row;
	}

	/**
	 * @return the button's column
	 */
	public int getCol() {
		return this.col;
	}

}