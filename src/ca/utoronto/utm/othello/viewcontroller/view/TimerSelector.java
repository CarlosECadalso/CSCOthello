package ca.utoronto.utm.othello.viewcontroller.view;

import ca.utoronto.utm.othello.viewcontroller.GameModel;
import ca.utoronto.utm.othello.viewcontroller.OthelloTimer;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;

/**
 * A class that displays the timer selector at the start of the game.
 * 
 * @author Team MarvelStudios
 * 
 */
class TimerSelector extends HBox {
	private TextField timerAnswerTextField = new TextField();

	TimerSelector(GameModel model) {
		this.setStyle("-fx-background-color: cadetblue;");

		Label timerQuery = new Label("  TIMER DURATION: ");
		timerQuery.setStyle("-fx-font-weight: bold");

		// Create a textfield that gets user input and when set is pressed, an event
		// handler takes care of that.
		this.getChildren().add(timerQuery);
		this.getChildren().add(timerAnswerTextField);
		timerAnswerTextField.setText(Integer.toString(OthelloTimer.DEFAULT_TIMER_START_SECONDS / 60));
		Button button = new Button("Set");

		button.setOnAction(new EventHandler<ActionEvent>() {
			/**
			 * Event handler that sets the timer for both players when the set button is
			 * clicked.
			 * 
			 */
			@Override
			public void handle(ActionEvent e) {
				int newTime = (int) (Float.parseFloat(timerAnswerTextField.getText()) * 60);
				if (newTime > 1) {
					model.getTimer().setTime(newTime);
				} else {
					model.getTimer().setTime(OthelloTimer.DEFAULT_TIMER_START_SECONDS);
				}
			}
		});

		this.getChildren().add(button);
	}

}