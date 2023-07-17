package ca.utoronto.utm.othello.viewcontroller.view;

import ca.utoronto.utm.othello.model.OthelloBoard;
import ca.utoronto.utm.othello.viewcontroller.GameController;
import ca.utoronto.utm.othello.viewcontroller.OthelloPlayerStrategyFactory;
import ca.utoronto.utm.othello.viewcontroller.PlayerStrategy;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.HBox;

/**
 * A class that deals with what playes are picked to play in the game.
 * 
 * @author Team MarvelStudios
 */
class PlayerPickerDisplay extends HBox {
  PlayerPickerDisplay(GameController controller, View view) {

    ObservableList<String> playerOptions1 = FXCollections.observableArrayList("Human Player", "Random Player",
        "Greedy Player", "Improved Player");
    ObservableList<String> playerOptions2 = FXCollections.observableArrayList("Human Player", "Random Player",
        "Greedy Player", "Improved Player");

    // Create 2 dropdowns for a player vs player selection.
    final ComboBox<String> p1Box = new ComboBox<String>(playerOptions1);
    p1Box.setValue("Human Player");
    final ComboBox<String> p2Box = new ComboBox<String>(playerOptions2);
    p2Box.setValue("Human Player");
    GameTypeEventHandler handler = new GameTypeEventHandler(controller, view, p1Box, p2Box);
    Button go = new Button("Go!");
    go.setOnAction(handler);
    this.getChildren().addAll(p1Box, p2Box, go);

    this.setStyle("-fx-background-color: cadetblue;");

  }
}

/**
 * A class that deals with an event that is triggered from selecting players in
 * the dropdown.
 * 
 * @author Team MarvelStudios
 */
class GameTypeEventHandler implements EventHandler<ActionEvent> {

  private GameController controller;
  private View view;
  private ComboBox<String> p1Box;
  private ComboBox<String> p2Box;
  private OthelloPlayerStrategyFactory factory = new OthelloPlayerStrategyFactory();

  GameTypeEventHandler(GameController controller, View view, ComboBox<String> p1Box, ComboBox<String> p2Box) {
    this.controller = controller;
    this.view = view;
    this.p1Box = p1Box;
    this.p2Box = p2Box;
  }

  /**
   * A handle method that uses the factory to generate strategies and player moves
   * for the game and then form how the game will actually be visually
   * represented.
   * 
   * @param event
   */
  @Override
  public void handle(ActionEvent event) {
    String p1Type = (String) this.p1Box.getValue();
    String p2Type = (String) this.p2Box.getValue();
    PlayerStrategy s1 = this.factory.toggleStrategy(this.controller.getModel(), this.controller, p1Type,
        OthelloBoard.P1);
    PlayerStrategy s2 = this.factory.toggleStrategy(this.controller.getModel(), this.controller, p2Type,
        OthelloBoard.P2);
    s1.setPlayer();
    s2.setPlayer();
    this.controller.setStrategy(s1, OthelloBoard.P1);
    this.controller.setStrategy(s2, OthelloBoard.P2);

    view.buildDisplay();

    this.controller.getModel().notifyObservers();
  }

}
