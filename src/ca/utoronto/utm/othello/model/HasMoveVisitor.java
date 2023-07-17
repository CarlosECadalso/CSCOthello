package ca.utoronto.utm.othello.model;

import ca.utoronto.utm.util.Visitor;

/**
 * A visitor class that checks if a player has a certain move.
 * 
 * @author Team MarvelStudios
 */
public class HasMoveVisitor implements Visitor {
  private char token = OthelloBoard.EMPTY;
  int row, col, drow, dcol;
  // true if we need to check specific coordinates
  private boolean specific = false;

  public HasMoveVisitor() {
  }

  /**
   * Create a new HasMoveVisitor for the specified coordinates
   */
  public HasMoveVisitor(int row, int col, int drow, int dcol) {
    this.row = row;
    this.col = col;
    this.drow = drow;
    this.dcol = dcol;
    specific = true;
  }

  /**
   * @return player token type for the valid move
   */
  public char getToken() {
    return token;
  }

  /**
   * Visitor method to check if there is a possible move specifically or in
   * general in the board using hasMove() as a helper.
   * 
   * @param othelloBoard
   * @param board
   */
  @Override
  public void visit(OthelloBoard othelloBoard, char[][] board) {
    if (!specific) {
      token = this.hasMove(othelloBoard, board);
    } else {
      token = this.hasMove(othelloBoard, board, row, col, drow, dcol);
    }
  }

  /**
   * check if a move is possible on the board,
   * 
   * @param othelloBoard
   * @param board
   * @return the player type that has a move.
   */
  private char hasMove(OthelloBoard othelloBoard, char[][] board) {
    char retVal = OthelloBoard.EMPTY;
    for (int row = 0; row < othelloBoard.getDimension(); row++) {
      for (int col = 0; col < othelloBoard.getDimension(); col++) {
        for (int drow = -1; drow <= 1; drow++) {
          for (int dcol = -1; dcol <= 1; dcol++) {
            if (drow == 0 && dcol == 0)
              continue;
            char p = this.hasMove(othelloBoard, board, row, col, drow, dcol);
            if (p == OthelloBoard.P1 && retVal == OthelloBoard.P2)
              return OthelloBoard.BOTH;
            if (p == OthelloBoard.P2 && retVal == OthelloBoard.P1)
              return OthelloBoard.BOTH;
            if (retVal == OthelloBoard.EMPTY)
              retVal = p;
          }
        }
      }
    }
    return retVal;
  }

  /**
   * Return which player has a move (row,col) in direction (drow,dcol).
   * 
   * @param row  starting row, in {0,...,dim-1} (typically {0,...,7})
   * @param drow the row direction, in {-1,0,1}
   * @param dcol the col direction, in {-1,0,1}
   * @return P1,P2,EMPTY
   */
  private char hasMove(OthelloBoard othelloBoard, char[][] board, int row, int col, int drow, int dcol) {
    CoordinateValidationVisitor visitor = new CoordinateValidationVisitor(row, col);
    othelloBoard.accept(visitor);
    if (!visitor.getIsValid() || board[row][col] != OthelloBoard.EMPTY) {
      return OthelloBoard.EMPTY;
    }

    OthelloAlternationVisitor oav = new OthelloAlternationVisitor(row + drow, col + dcol, drow, dcol);
    othelloBoard.accept(oav);

    return oav.getToken();
  }
}