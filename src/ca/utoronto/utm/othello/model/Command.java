package ca.utoronto.utm.othello.model;

import ca.utoronto.utm.othello.viewcontroller.GameModel;

/**
 * Specify the Command interface for the command pattern
 * 
 * @author Team MarvelStudios
 */
public interface Command {
  public void execute(GameModel model);

  public void undo(GameModel model);
}