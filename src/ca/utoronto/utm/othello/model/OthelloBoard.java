package ca.utoronto.utm.othello.model;

import ca.utoronto.utm.util.Visitable;
import ca.utoronto.utm.util.Visitor;

/**
 * Keep track of all of the tokens on the board. This understands some
 * interesting things about an Othello board, what the board looks like at the
 * start of the game, what the players tokens look like ('X' and 'O'), whether
 * given coordinates are on the board, whether either of the players have a move
 * somewhere on the board, what happens when a player makes a move at a specific
 * location (the opposite players tokens are flipped).
 * 
 * Othello makes use of the OthelloBoard.
 * 
 * @author arnold
 *
 */

public class OthelloBoard implements Visitable {
	public static final char EMPTY = ' ', P1 = 'X', P2 = 'O', BOTH = 'B';
	private int dim = 8;
	private char[][] board;

	public OthelloBoard(int dim) {
		this.dim = dim;
		board = new char[this.dim][this.dim];
		for (int row = 0; row < this.dim; row++) {
			for (int col = 0; col < this.dim; col++) {
				this.board[row][col] = EMPTY;
			}
		}
		int mid = this.dim / 2;
		this.board[mid - 1][mid - 1] = this.board[mid][mid] = P1;
		this.board[mid][mid - 1] = this.board[mid - 1][mid] = P2;
	}

	/**
	 * 
	 * @return a copy of this
	 */
	public OthelloBoard copy() {
		OthelloBoard ob = new OthelloBoard(this.dim);
		for (int row = 0; row < this.dim; row++) {
			for (int col = 0; col < this.dim; col++) {
				ob.board[row][col] = this.board[row][col];
			}
		}
		return ob;
	}

	/**
	 * 
	 * @param row starting row, in {0,...,dim-1} (typically {0,...,7})
	 * @param col starting col, in {0,...,dim-1} (typically {0,...,7})
	 * @return P1,P2 or EMPTY, EMPTY is returned for an invalid (row,col)
	 */
	public char get(int row, int col) {
		CoordinateValidationVisitor visitor = new CoordinateValidationVisitor(row, col);
		this.accept(visitor);
		if (visitor.getIsValid())
			return this.board[row][col];
		else
			return EMPTY;
	}

	public int getDimension() {
		return this.dim;
	}

	/**
	 * 
	 * @param player either P1 or P2
	 * @return P2 or P1, the opposite of player
	 */
	public static char otherPlayer(char player) {
		if (player == P1)
			return P2;
		if (player == P2)
			return P1;
		return EMPTY;
	}

	/**
	 * 
	 * @return whether P1,P2 or BOTH have a move somewhere on the board, EMPTY if
	 *         neither do.
	 */
	public char hasMove() {
		HasMoveVisitor visitor = new HasMoveVisitor();
		this.accept(visitor);
		return visitor.getToken();
	}

	/**
	 * Make a move for player at position (row,col) according to Othello rules,
	 * making appropriate modifications to the board. Nothing is changed if this is
	 * not a valid move.
	 * 
	 * @param row    starting row, in {0,...,dim-1} (typically {0,...,7})
	 * @param col    starting col, in {0,...,dim-1} (typically {0,...,7})
	 * @param player P1 or P2
	 * @return true if player moved successfully at (row,col), false otherwise
	 */
	public boolean move(int row, int col, char player) {
		OthelloMoveVisitor visitor = new OthelloMoveVisitor(row, col, player);
		this.accept(visitor);
		return visitor.getMoved();
	}

	/**
	 * 
	 * @param player P1 or P2
	 * @return the number of tokens on the board for player
	 */
	public int getCount(char player) {
		OthelloCountVisitor visitor = new OthelloCountVisitor(player);
		this.accept(visitor);
		return visitor.getCount();
	}

	/**
	 * @return a string representation of this, just the play area, with no
	 *         additional information. DO NOT MODIFY THIS!!
	 */
	public String toString() {
		/**
		 * See assignment web page for sample output.
		 */
		String s = "";
		s += "  ";
		for (int col = 0; col < this.dim; col++) {
			s += col + " ";
		}
		s += '\n';

		s += " +";
		for (int col = 0; col < this.dim; col++) {
			s += "-+";
		}
		s += '\n';

		for (int row = 0; row < this.dim; row++) {
			s += row + "|";
			for (int col = 0; col < this.dim; col++) {
				s += board[row][col] + "|";
			}
			s += row + "\n";

			s += " +";
			for (int col = 0; col < this.dim; col++) {
				s += "-+";
			}
			s += '\n';
		}
		s += "  ";
		for (int col = 0; col < this.dim; col++) {
			s += col + " ";
		}
		s += '\n';
		return s;
	}

	/**
	 * A quick test of OthelloBoard. Output is on assignment page.
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		OthelloBoard ob = new OthelloBoard(8);
		System.out.println(ob.toString());
		System.out.println("getCount(P1)=" + ob.getCount(P1));
		System.out.println("getCount(P2)=" + ob.getCount(P2));
		for (int row = 0; row < ob.dim; row++) {
			for (int col = 0; col < ob.dim; col++) {
				ob.board[row][col] = P1;
			}
		}
		System.out.println(ob.toString());
		System.out.println("getCount(P1)=" + ob.getCount(P1));
		System.out.println("getCount(P2)=" + ob.getCount(P2));

		// Should all be blank
		for (int drow = -1; drow <= 1; drow++) {
			for (int dcol = -1; dcol <= 1; dcol++) {

				OthelloAlternationVisitor visitor = new OthelloAlternationVisitor(4, 4, drow, dcol);
				ob.accept(visitor);
				System.out.println("alternation=" + visitor.getToken());
			}
		}

		for (int row = 0; row < ob.dim; row++) {
			for (int col = 0; col < ob.dim; col++) {
				if (row == 0 || col == 0) {
					ob.board[row][col] = P2;
				}
			}
		}
		System.out.println(ob.toString());

		// Should all be P2 (O) except drow=0,dcol=0
		for (int drow = -1; drow <= 1; drow++) {
			for (int dcol = -1; dcol <= 1; dcol++) {
				System.out.println("direction=(" + drow + "," + dcol + ")");
				OthelloAlternationVisitor visitor = new OthelloAlternationVisitor(4, 4, drow, dcol);
				ob.accept(visitor);
				System.out.println("alternation=" + visitor.getToken());
			}
		}

		// Can't move to (4,4) since the square is not empty
		System.out.println("Trying to move to (4,4) move=" + ob.move(4, 4, P2));

		ob.board[4][4] = EMPTY;
		ob.board[2][4] = EMPTY;

		System.out.println(ob.toString());

		for (int drow = -1; drow <= 1; drow++) {
			for (int dcol = -1; dcol <= 1; dcol++) {
				System.out.println("direction=(" + drow + "," + dcol + ")");
				HasMoveVisitor visitor = new HasMoveVisitor(4, 4, drow, dcol);
				ob.accept(visitor);
				System.out.println("hasMove at (4,4) in above direction =" + visitor.getToken());
			}
		}
		System.out.println("who has a move=" + ob.hasMove());
		System.out.println("Trying to move to (4,4) move=" + ob.move(4, 4, P2));
		System.out.println(ob.toString());

		System.out.println("Testing flip method: before");
		ob = new OthelloBoard(Othello.DIMENSION);
		for (int row = 0; row < Othello.DIMENSION; row++) {
			for (int col = 0; col < Othello.DIMENSION; col++) {
				if (row > col)
					ob.board[row][col] = EMPTY;
				else
					ob.board[row][col] = P2;
				if (col == Othello.DIMENSION - 1)
					ob.board[row][col] = P1;
			}
		}
		ob.board[0][7] = EMPTY;
		ob.board[1][7] = P2;
		ob.board[4][4] = P1;
		System.out.println(ob.toString());

		System.out.println("Testing alternation:");
		for (int row = 0; row < Othello.DIMENSION; row++) {
			OthelloAlternationVisitor visitor = new OthelloAlternationVisitor(row, row, 0, 1);
			ob.accept(visitor);
			char result = visitor.getToken();
			System.out.println("alternation(" + row + "," + row + ",0,1)=" + result);
		}
		for (int row = 0; row < Othello.DIMENSION; row++) {
			OthelloAlternationVisitor visitor = new OthelloAlternationVisitor(row, row - 1, 0, 1);
			ob.accept(visitor);
			char result = visitor.getToken();
			System.out.println("alternation(" + row + "," + (row - 1) + ",0,1)=" + result);
		}

		System.out.println("Testing hasMove:");
		for (int row = 0; row < Othello.DIMENSION; row++) {
			HasMoveVisitor visitor = new HasMoveVisitor(row, row, 0, 1);
			ob.accept(visitor);
			char result = visitor.getToken();
			System.out.println("hasMove(" + row + "," + row + ",0,1)=" + result);
		}
		for (int row = 0; row < Othello.DIMENSION; row++) {
			HasMoveVisitor visitor = new HasMoveVisitor(row, row - 1, 0, 1);
			ob.accept(visitor);
			char result = visitor.getToken();
			System.out.println("hasMove(" + row + "," + (row - 1) + ",0,1)=" + result);
		}

	}

	@Override
	public void accept(Visitor visitor) {
		visitor.visit(this, this.board);
	}
}
