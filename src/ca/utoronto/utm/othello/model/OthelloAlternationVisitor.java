package ca.utoronto.utm.othello.model;

import ca.utoronto.utm.util.Visitor;

/**
 * A visitor class that checks if an alternation is possible on the board.
 * 
 * @author Team MarvelStudios
 */
public class OthelloAlternationVisitor implements Visitor {
  private int row, col, drow, dcol;
  private char token = OthelloBoard.EMPTY;

  /**
   * Create a new OthelloAlternationVisitor for the specified visitor
   */
  public OthelloAlternationVisitor(int row, int col, int drow, int dcol) {
    this.row = row;
    this.col = col;
    this.drow = drow;
    this.dcol = dcol;
  }

  /**
   * @return the token with a valid alternation
   */
  public char getToken() {
    return this.token;
  }

  /**
   * check if an alternation is possible
   * 
   * @param othelloboard
   * @param board
   */
  @Override
  public void visit(OthelloBoard othelloBoard, char[][] board) {
    if (drow == 0 && dcol == 0)
      return;

    char firstToken = othelloBoard.get(row, col);

    while (true) {
      char nextToken = othelloBoard.get(row, col);
      if (nextToken != OthelloBoard.P1 && nextToken != OthelloBoard.P2) {
        this.token = OthelloBoard.EMPTY;
        return;
      }
      if (nextToken == OthelloBoard.otherPlayer(firstToken)) {
        this.token = nextToken;
        return;
      }
      row += drow;
      col += dcol;
    }
  }
}