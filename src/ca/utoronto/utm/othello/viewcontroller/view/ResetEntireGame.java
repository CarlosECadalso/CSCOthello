package ca.utoronto.utm.othello.viewcontroller.view;

import ca.utoronto.utm.othello.viewcontroller.GameController;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;

/**
 * A class that resets the entire game
 * 
 * @author Team MarvelStudios
 * 
 */
class ResetEntireGame extends HBox {
  ResetEntireGame(GameController controller) {
    Button reset = new Button("Reset Entire Game");

    this.setStyle("-fx-background-color: grey;");
    reset.setOnAction(new EventHandler<ActionEvent>() {
      @Override
      public void handle(ActionEvent e) {
        controller.resetGame();
      }
    });
    this.getChildren().add(reset);
  }
}