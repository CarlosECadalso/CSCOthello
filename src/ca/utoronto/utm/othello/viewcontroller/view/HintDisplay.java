package ca.utoronto.utm.othello.viewcontroller.view;

import ca.utoronto.utm.othello.viewcontroller.GameModel;
import ca.utoronto.utm.othello.viewcontroller.OthelloHint;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import ca.utoronto.utm.othello.model.*;

/**
 * A class that displays the hint coordinates and the hint graphics on the
 * board.
 * 
 * @author Team MarvelStudios
 */
class HintDisplay extends VBox {
  HintDisplay(GameModel model) {

    Button hint = new Button("Hint!");

    // Create a testfield for displaying the hint coordinates
    TextField hintResult = new TextField("");
    hintResult.setEditable(false);
    hintResult.setPrefColumnCount(5);
    this.setStyle("-fx-background-color: blanchedalmond;");

    // Make a drobdown of player hints to choose from and by default it's a random.
    ObservableList<String> options = FXCollections.observableArrayList("Random Hint", "Greedy Hint", "Improved Hint");
    final ComboBox<String> comboBox = new ComboBox<String>(options);
    comboBox.setPromptText("Random Hint");
    hint.setOnAction(new HintHandler(model, hintResult, comboBox));
    this.getChildren().add(hint);
    this.getChildren().add(hintResult);
    this.getChildren().add(comboBox);
  }
}

class HintHandler implements EventHandler<ActionEvent> {
  private GameModel model;
  private TextField result;
  private ComboBox<String> comboBox;
  private Move current;

  HintHandler(GameModel model, TextField result, ComboBox<String> comboBox) {
    this.model = model;
    this.result = result;
    this.comboBox = comboBox;
    this.current = null;
  }

  /**
   * A handle event for what type of hint to display
   * 
   * @param event
   */
  @Override
  public void handle(ActionEvent event) {
    OthelloHint hint = new OthelloHint(model);
    this.current = null;

    // check the picked hint
    if (this.comboBox.getValue() == "Greedy Hint") {
      this.current = hint.greedyHint(model.getOthello().getWhosTurn());
    } else if (this.comboBox.getValue() == "Improved Hint") {
      this.current = hint.improvedHint(model.getOthello().getWhosTurn());
    } else {
      this.current = hint.randomHint(model.getOthello().getWhosTurn());
    }
    model.getHintModel().setHint(this.current);
    model.notifyObservers();
    result.setText((this.current.getRow()+1) + "," + (this.current.getCol()+1));
  }

}