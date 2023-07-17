package ca.utoronto.utm.othello.model;

import ca.utoronto.utm.util.Visitor;

/**
 * A visitor class that checks if a given row and column is a valid corrdinate
 * 
 * @author Team MarvelStudios
 */
public class CoordinateValidationVisitor implements Visitor {
  private boolean isValid = false;
  private int row, col;

  /**
   * Create a new CoordinateValidationVisitor for the specified row and col
   */
  public CoordinateValidationVisitor(int row, int col) {
    this.row = row;
    this.col = col;
  }

  /**
   * @return if a corrdinate is valid.
   */
  public boolean getIsValid() {
    return this.isValid;
  }

  /**
   * A visitor method that takes in the othelloboard and the char representation
   * of the board and checks if the given row and col is valid on the board.
   * 
   * @param othelloboard
   * @param board
   * @return true if the coordinates are valid
   */
  @Override
  public void visit(OthelloBoard othelloBoard, char[][] board) {
    this.isValid = 0 <= row && row < othelloBoard.getDimension() && 0 <= col && col < othelloBoard.getDimension();
  }
}