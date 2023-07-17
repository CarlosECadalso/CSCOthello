package ca.utoronto.utm.othello.viewcontroller.view;

import ca.utoronto.utm.othello.model.Move;
import ca.utoronto.utm.othello.viewcontroller.MoveCommand;
import ca.utoronto.utm.othello.model.Othello;
import ca.utoronto.utm.othello.model.PlayerHuman;
import ca.utoronto.utm.othello.viewcontroller.GameController;
import ca.utoronto.utm.othello.viewcontroller.GameModel;
import javafx.scene.layout.VBox;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.layout.HBox;

/**
 * Contain the board buttons for gameplay
 * 
 * @author Team MarvelStudios
 */
class GamePad extends VBox {
  GamePad(GameModel model, GameController controller) {
    this.setPadding(new Insets(10));
    this.setStyle("-fx-background-color: blanchedalmond;");
    OthelloButtonPressEventHandler handler = new OthelloButtonPressEventHandler(model, controller);
    for (int row = 0; row < Othello.DIMENSION; row++) {
      HBox currentContainer = new HBox();
      for (int col = 0; col < Othello.DIMENSION; col++) {
        BoardButton b = new BoardButton(row, col, model);

        currentContainer.getChildren().add(b);
        b.setOnAction(handler);
        model.attach(b);

        // configure the default state of the button
        b.update(model);
      }
      this.getChildren().add(currentContainer);

    }
  }
}

/**
 * A class that handles an event triggered by a button click
 * 
 * @author Team MarvelStudios
 */
class OthelloButtonPressEventHandler implements EventHandler<ActionEvent> {
  private GameModel model;
  private GameController controller;

  OthelloButtonPressEventHandler(GameModel model, GameController controller) {
    this.controller = controller;
    this.model = model;
  }

  /**
   * Execute the move logic when a button is pressed
   */
  @Override
  public void handle(ActionEvent event) {
    if (!this.model.isGameOver() && this.model.getCurrentPlayer() instanceof PlayerHuman) {
      BoardButton button = (BoardButton) event.getSource();
      Move move = new Move(button.getRow(), button.getCol());
      controller.addCommand(new MoveCommand(move, false));
    }

  }

}