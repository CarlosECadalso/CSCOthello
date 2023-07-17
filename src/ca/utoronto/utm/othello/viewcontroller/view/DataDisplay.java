package ca.utoronto.utm.othello.viewcontroller.view;

import ca.utoronto.utm.othello.viewcontroller.GameController;
import ca.utoronto.utm.othello.viewcontroller.GameModel;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

/**
 * Class for displaying the statistics of the game
 * 
 * @author Team MarvelStudios
 */
class DataDisplay extends VBox {

  DataDisplay(GameModel model, GameController controller) {

    this.setStyle("-fx-background-color: blanchedalmond;");

    // The hint's data
    HintDisplay hint = new HintDisplay(model);
    this.getChildren().add(hint);

    // The next player's data
    NextDisplay nextDisplay = new NextDisplay(model);
    model.attach(nextDisplay);
    getChildren().add(nextDisplay);

    // The timer's data
    GameTimerDisplay timerDisplay = new GameTimerDisplay(model);
    getChildren().add(timerDisplay);

    // The player's token count data
    PlayerDisplay playerDisplay = new PlayerDisplay(model);
    getChildren().add(playerDisplay);
    model.attach(playerDisplay);
    playerDisplay.update(model);

    // The status of the game whether is is ongoing or over.
    GameStatusDisplay gameStatus = new GameStatusDisplay(model);
    getChildren().add(gameStatus);
    model.getTimer().attach(gameStatus);
    model.attach(gameStatus);

    // Data for undoing moves
    UndoMove undo = new UndoMove(controller, model);
    model.attach(undo);
    RedoMove redo = new RedoMove(controller, model);
    model.attach(redo);

    // GUI window graphics configurations
    BorderPane border = new BorderPane();
    HBox container = new HBox();
    container.getChildren().addAll(undo, redo);
    border.setLeft(container);
    border.setRight(new ResetEntireGame(controller));

    getChildren().add(border);

  }

}