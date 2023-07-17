package ca.utoronto.utm.othello.viewcontroller;

import ca.utoronto.utm.othello.model.Command;
import ca.utoronto.utm.othello.model.OthelloBoard;
import javafx.stage.Stage;

/**
 * A controller class for our game that has specific functionalities not found
 * in OthelloController. It contains all handler and mutation logic required to
 * operate the game
 * 
 * @author Team MarvelStudios
 */
public class GameController {
  private GameModel model;
  private PlayerStrategy p1Strategy;
  private PlayerStrategy p2Strategy;
  private Stage stage;

  public GameController(GameModel model, Stage stage) {
    this.model = model;
    this.stage = stage;
  }

  public GameModel getModel() {
    return this.model;
  }

  /**
   * Add a command to the model and execute it (and all others)
   */
  public void addCommand(Command command) {
    this.model.addCommand(command);
    this.model.move();
  }

  /**
   * A method that checks the current player and detaches its strategy and updates
   * the field strategy that to the given strategy.
   * 
   * @param strategy
   * @param which
   */
  public void setStrategy(PlayerStrategy strategy, char which) {
    if (which == OthelloBoard.P1) {
      this.model.detach(p1Strategy);
      this.p1Strategy = strategy;
    } else {
      this.model.detach(p2Strategy);
      this.p2Strategy = strategy;
    }
    this.model.attach(strategy);

  }

  /**
   * 
   * @return the player strategy for the current player
   */
  public PlayerStrategy getStrategy() {
    if (this.model.getCurrentPlayer().getPlayer() == OthelloBoard.P1) {
      return this.p1Strategy;
    } else {
      return this.p2Strategy;
    }
  }

  /**
   * Method that resets all game components and opens a new application.
   */
  public void resetGame() {
    // close the current game ...
    this.model.resetModel();
    this.model.getTimer().reset();

    this.model.notifyObservers();
    this.model.getTimer().notifyObservers();

    this.stage.close();
    try {
      // ... boot up a new one
      new OthelloApplication().start(new Stage());
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  /**
   * Method that undoes a command in the model
   */
  public void undo() {
    this.model.undoCommands();
  }

  /**
   * Method that redoes a command in the model
   */
  public void redo() {
    this.model.redoCommands();
  }
}