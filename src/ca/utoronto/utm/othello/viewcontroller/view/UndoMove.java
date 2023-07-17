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
 * A class that enables an undo move click
 * 
 * @author Team MarvelStudios
 */
class UndoMove extends HBox implements Observer {
  private GameModel model;
  private Button button;

  UndoMove(GameController controller, GameModel model) {
    this.model = model;
    this.setStyle("-fx-background-color: blanchedalmond;");
    button = new Button("Undo");
    button.setDisable(!model.canUndo());
    button.setDisable(model.isGameOver());
    button.setOnAction(new EventHandler<ActionEvent>() {
      @Override
      public void handle(ActionEvent e) {
    	if(!model.isGameOver()) {
    		controller.undo();
    	}
    	
      }
    });

    this.getChildren().add(button);
  }

  /**
   * Determine if the undo move button should be clickable
   */
  @Override
  public void update(Observable o) {
	button.setDisable(model.isGameOver() || !model.canUndo());
  }
}