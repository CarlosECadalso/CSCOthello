package ca.utoronto.utm.othello.model;

import ca.utoronto.utm.util.Visitor;

/**
 * Visitor class that counts how many tokens are there on the board.
 * 
 * @author Team MarvelStudios
 * 
 */
public class OthelloCountVisitor implements Visitor {
  private int count = 0;
  private char player;

  /**
   * Create a new OthelloCountVisitor for the specific player
   */
  public OthelloCountVisitor(char player) {
    this.player = player;
  }

  /**
   * @return the number of tokens for the specific player
   */
  public int getCount() {
    return count;
  }

  /**
   * Visitor method to count the number of tokens there are on the board.
   * 
   * @param othelloboard
   * @param board
   */
  @Override
  public void visit(OthelloBoard othelloBoard, char[][] board) {
    for (int row = 0; row < othelloBoard.getDimension(); row++) {
      for (int col = 0; col < othelloBoard.getDimension(); col++) {
        if (board[row][col] == player)
          count++;
      }
    }
  }
}