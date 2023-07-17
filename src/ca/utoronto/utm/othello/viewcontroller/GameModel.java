package ca.utoronto.utm.othello.viewcontroller;

import java.nio.file.Paths;

import java.util.ArrayList;

import ca.utoronto.utm.othello.model.Command;
import ca.utoronto.utm.othello.model.Move;
import ca.utoronto.utm.othello.model.Othello;

import ca.utoronto.utm.othello.model.OthelloBoard;
import ca.utoronto.utm.othello.model.Player;
import ca.utoronto.utm.util.Observable;


/**
 * A Model class that has specific functionality not found in the OthelloModel
 * class. Contains all application logic including the hints, game state and
 * logic to handle undo and redo. It also houses the specified players.
 * 
 * @author Team MarvelStudios
 */
public class GameModel extends Observable {
  protected Othello othello = new Othello();
  private Player player1;
  private Player player2;

  private OthelloTimer timer;

  private Stack<Command> undoStack = new Stack<>();
  private Stack<Command> redoStack = new Stack<>();
  private ArrayList<Command> commands = new ArrayList<Command>();
  private HintModel hints;

  /**
   * Create a new game model which contains all game state
   */
  public GameModel() {
    this.timer = new OthelloTimer(this);
    this.attach(this.timer);
    this.hints = new HintModel(this);
    this.attach(this.hints);

  }

  /**
   * Set a new player
   * 
   * @param p
   */
  public void setPlayer(Player p) {
    if (p.getPlayer() == OthelloBoard.P1) {
      this.player1 = p;
    } else {
      this.player2 = p;
    }
  }


  /**
   * A getter for the type of the player token
   * 
   * @param playerToken
   * @return
   */
  public Player getPlayer(char playerToken) {
    if (playerToken == OthelloBoard.P1) {
      return player1;
    }
    return player2;
  }

  /**
   * A method that executes all Move Commands
   */
  public void move() {
    if (!this.isGameOver()) {
      this.executeAll();
    }
  }

  /**
   * Get the previous move which changed the board
   */
  public Move getLastMove() {
    if (this.canUndo()) {
      return ((MoveCommand) this.undoStack.peek()).getMove();
    }
    return null;
  }

  /**
   * calculates the distance from the previous button click to the provided (row,
   * col) and determines a animation delay based on the distance between the two
   * points. Calculation is performed via a breadth first search from the clicked
   * cell.
   * 
   * For example if (3,3,) was clicked then the cells (2,2) and (2,3) would have
   * the same delay since they are both 1 cell away.
   * 
   * @param row
   * @param col
   * @return
   */
  public int getAnimationDelay(int row, int col) {
    try {
      if (!this.undoStack.isEmpty()) {
        MoveCommand prevCommand = ((MoveCommand) this.undoStack.peek());
        Move prevMove = prevCommand.getMove();
        if (!this.redoStack.isEmpty()) {
          prevCommand = ((MoveCommand) this.redoStack.peek());
          prevMove = prevCommand.getMove();
        }

        int delay = 100; // base milliseconds
        int delayFactor = 250; // increment the delay for each distance awaw

        if (row == prevMove.getRow()) {
          return (Math.abs(col - prevMove.getCol()) * delayFactor) + delay;
        }
        return (Math.max(Math.abs(row - prevMove.getRow()), Math.abs(col - prevMove.getCol())) * delayFactor) + delay;

      }
    } catch (NullPointerException e) {
    }
    return 0;
  }

  /**
   * 
   * @return the maximum delay for the previous game state
   */
  public int getMaxAnimationDelay() {
    int maxDelay = 0;
    for (int row = 0; row < Othello.DIMENSION; row++) {
      for (int col = 0; col < Othello.DIMENSION; col++) {
        maxDelay = Math.max(maxDelay, getAnimationDelay(row, col));
      }
    }
    return maxDelay;
  }

  /**
   * Redo the last command till the last user command
   */
  public void redoCommands() {
    if (!redoStack.isEmpty()) {
      Command command = redoStack.pop();
      command.execute(this);
      undoStack.push(command);
      while (!redoStack.isEmpty() && ((MoveCommand) command).getIsAi()) {
        command = redoStack.pop();
        command.execute(this);
        undoStack.push(command);
      }

      this.notifyObservers();
    }
  }

  /**
   * undo the last command till the last user command
   */
  public void undoCommands() {
    if (!undoStack.isEmpty()) {
      Command command = undoStack.pop();

      command.undo(this);
      redoStack.push(command);
      this.hints.setHint(null);
      while (!undoStack.isEmpty() && ((MoveCommand) command).getIsAi()) {
        command = undoStack.pop();

        command.undo(this);
        redoStack.push(command);
      }

      this.notifyObservers();
    }

  }

  /**
   * @return true if we can undo the last move
   */
  public boolean canUndo() {
    return !this.undoStack.isEmpty();
  }

  /**
   * @return true if we can redo the last move
   */
  public boolean canRedo() {
    return !this.redoStack.isEmpty();
  }

  /**
   * 
   * @return the current player turn
   */
  public Player getCurrentPlayer() {
    return getPlayer(othello.getWhosTurn());
  }

  /**
   * 
   * @return the Othello game object
   */
  public Othello getOthello() {
    return this.othello;
  }

  /**
   * Method for resetting the game model
   */
  public void resetModel() {
    this.othello = new Othello();
    this.undoStack = new Stack<>();
    this.notifyObservers();
  }

  /**
   * @return true if the timer or game is over
   */
  public boolean isGameOver() {
    return timer.isATimers0() || othello.isGameOver();
  }

  /**
   * 
   * @return the game timer
   */
  public OthelloTimer getTimer() {
    return timer;
  }

  /**
   * Method for adding a command
   * 
   * @param command
   */
  public void addCommand(Command command) {
    commands.add(command);
  }

  /**
   * A method that executes all commands to have a modified game state.
   */
  public void executeAll() {
    while (commands.size() > 0) {
      Command c = this.commands.remove(0);
      c.execute(this);
      undoStack.push(c);

      this.redoStack.clear();
    }
  }

  /**
   * 
   * @return the hint model
   */
  public HintModel getHintModel() {
    return this.hints;
  }

}