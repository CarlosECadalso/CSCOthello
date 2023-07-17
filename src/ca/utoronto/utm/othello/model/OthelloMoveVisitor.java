package ca.utoronto.utm.othello.model;

import ca.utoronto.utm.util.Visitor;

/**
 * A visitor class that moves a token on the board.
 * 
 * @author Team MarvelStudios
 */
public class OthelloMoveVisitor implements Visitor {
  private boolean moved = false;
  private int row, col;
  private char player;

  /**
   * Create a new OthelloMoveVisitor for the specified location and player
   */
  public OthelloMoveVisitor(int row, int col, char player) {
    this.row = row;
    this.col = col;
    this.player = player;
  }

  /**
   * @return true if the move was successful
   */
  public boolean getMoved() {
    return moved;
  }

  /**
   * Visitor method to move a token on the board.
   * 
   * @param othelloBoard
   * @param board
   */
  @Override
  public void visit(OthelloBoard othelloBoard, char[][] board) {
    CoordinateValidationVisitor visitor = new CoordinateValidationVisitor(row, col);
    othelloBoard.accept(visitor);
    if (!visitor.getIsValid()) {
      moved = false;
      return;
    }
    if (board[row][col] != OthelloBoard.EMPTY) {
      moved = false;
      return;
    }

    int numChangedTotal = 0;
    for (int drow = -1; drow <= 1; drow++) {
      for (int dcol = -1; dcol <= 1; dcol++) {
        if (drow == 0 && dcol == 0)
          continue;
        int numChanged = flip(othelloBoard, board, row + drow, col + dcol, drow, dcol, player);
        if (numChanged >= 0)
          numChangedTotal += numChanged;
      }
    }
    if (numChangedTotal > 0) {
      board[row][col] = player;
      moved = true;
      return;
    }
    moved = false;
    return;
  }

  /**
   * flip all other player tokens to player, starting at (row,col) in direction
   * (drow, dcol). Example: If (drow,dcol)=(0,1) and player==O then XXXO will
   * result in a flip to OOOO
   * 
   * @param row    starting row, in {0,...,dim-1} (typically {0,...,7})
   * @param col    starting col, in {0,...,dim-1} (typically {0,...,7})
   * @param drow   the row direction, in {-1,0,1}
   * @param dcol   the col direction, in {-1,0,1}
   * @param player Either OthelloBoard.P1 or OthelloBoard.P2, the target token to
   *               flip to.
   * @return the number of other player tokens actually flipped, -1 if this is not
   *         a valid move in this one direction, that is, EMPTY or the end of the
   *         board is reached before seeing a player token.
   */
  private int flip(OthelloBoard othelloBoard, char[][] board, int row, int col, int drow, int dcol, char player) {
    CoordinateValidationVisitor visitor = new CoordinateValidationVisitor(row, col);
    othelloBoard.accept(visitor);
    if (!visitor.getIsValid())
      return -1;
    if (board[row][col] == OthelloBoard.EMPTY)
      return -1;
    if (board[row][col] == player)
      return 0;
    if (board[row][col] == OthelloBoard.otherPlayer(player)) {
      int numChanged = this.flip(othelloBoard, board, row + drow, col + dcol, drow, dcol, player);
      if (numChanged >= 0) {
        board[row][col] = player;
        return numChanged + 1;
      } else {
        return numChanged;
      }
    }
    return -1; // Should not get here!
  }
}