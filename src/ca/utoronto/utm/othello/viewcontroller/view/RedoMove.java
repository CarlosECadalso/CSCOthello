package ca.utoronto.utm.othello.viewcontroller.view;

import ca.utoronto.utm.othello.viewcontroller.GameController;
import ca.utoronto.utm.othello.viewcontroller.GameModel;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import ca.utoronto.utm.util.Observable;
import ca.utoronto.utm.util.Observer;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;

/**
 * Handle redoing the last move
 * 
 * @author Team MarvelStudios
 */
class RedoMove extends HBox implements Observer {
  private GameModel model;
  private Button button;

  RedoMove(GameController controller, GameModel model) {
    this.model = model;
    this.setStyle("-fx-background-color: blanchedalmond;");
    button = new Button("Redo");
    button.setDisable(!model.canRedo());
    button.setOnAction(new EventHandler<ActionEvent>() {
      @Override
      public void handle(ActionEvent e) {
        controller.redo();
      }
    });

    this.getChildren().add(button);
  }

  /**
   * Determine if the redo button should be clickable
   */
  @Override
  public void update(Observable o) {
    button.setDisable(!model.canRedo());
  }
}