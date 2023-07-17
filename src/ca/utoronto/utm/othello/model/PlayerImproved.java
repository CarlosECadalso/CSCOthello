package ca.utoronto.utm.othello.model;

/**
 * PlayerImproved makes a move by considering all possible moves that the player
 * can make. Then, the player chooses a move based on the following 2 list of
 * priorities:
 * 
 * Positional Priority: 1. The player can play in a corner 2. The player can
 * play in a side 3. The player can play in the inner part of the board
 * 
 * Presence Priority: 1. The player can maximize its presence in the middle 4 by
 * 4 square of the board. 2. The player can maximize its presence somewhere in
 * the board
 * 
 * Each move will be chosen from the list using the first list of priorities. If
 * more than one move fulfill a priority in the first list, the moves will be
 * filtered using the second list of priorities. If more than one move fulfill a
 * priority in both lists, then the one with the smallest row will be picked,
 * and, if they are both in the same row, the one with the smallest column will
 * be picked.
 * 
 * Example: Say moves (2,7) and (3,1) result in the maximum number of tokens for
 * this player. Then (2,7) is returned since 2 is the smaller row.
 * 
 * Example: Say moves (2,7) and (2,4) result in the maximum number of tokens for
 * this player. Then (2,4) is returned, since the rows are tied, but (2,4) has
 * the smaller column.
 * 
 * @author Carlos Enrique Cadalso Rivero
 *
 */
public class PlayerImproved extends Player {
	public static final String NAME = "Improved";

	public PlayerImproved(Othello othello, char player) {
		super(othello, player);
	}

	@Override
	public Move getMove() {
		Move bestMove = new Move(-1, -1);

		bestMove = this.getCornerMove();
		if (bestMove.getCol() != -1 || bestMove.getRow() != -1) {
			return bestMove;
		}

		bestMove = this.getSideMove();

		if (bestMove.getCol() != -1 || bestMove.getRow() != -1) {
			return bestMove;
		}

		bestMove = this.getBoardMove();

		return bestMove;
	}

	public Move getCornerMove() {
		Othello othelloCopy = othello.copy();
		Move bestMove = new Move(-1, -1);
		int bestMoveCount = 0;

		OthelloWeightedCountVisitor countVisitor = new OthelloWeightedCountVisitor(player);
		for (int corner = 0; corner < 4; corner++) {
			int[] cornerPos = new int[2];
			if (corner == 0) {
				cornerPos[0] = 0;
				cornerPos[1] = 0;
			} else if (corner == 1) {
				cornerPos[0] = 0;
				cornerPos[1] = 7;
			} else if (corner == 2) {
				cornerPos[0] = 7;
				cornerPos[1] = 0;
			} else {
				cornerPos[0] = 7;
				cornerPos[1] = 7;
			}

			othelloCopy = othello.copy();
			othelloCopy.accept(countVisitor);
			if (othelloCopy.move(cornerPos[0], cornerPos[1]) && countVisitor.getCount() > bestMoveCount) {
				bestMove = new Move(cornerPos[0], cornerPos[1]);
				bestMoveCount = countVisitor.getCount();
			}
		}
		return bestMove;

	}

	private static boolean isCorner(Move move) {
		return ((move.getCol() == 0 && move.getRow() == 0) || (move.getCol() == 0 && move.getRow() == 7)
				|| (move.getCol() == 7 && move.getRow() == 0) || (move.getCol() == 7 && move.getRow() == 7));
	}

	public Move getSideMove() {
		Othello othelloCopy = othello.copy();
		Move bestMove = new Move(-1, -1);
		int bestMoveCount = 0;

		OthelloWeightedCountVisitor countVisitor = new OthelloWeightedCountVisitor(this.player);
		for (int row = Othello.DIMENSION - 1; row >= 0; row--) {
			for (int col = Othello.DIMENSION - 1; col >= 0; col--) {
				if ((row == 0 || col == 0 || row == 7 || col == 7) && !isCorner(new Move(row, col))) {

					othelloCopy = othello.copy();
					if (othelloCopy.move(row, col)) {
						this.getBestCount(othelloCopy, countVisitor);
						if (countVisitor.getCount() > bestMoveCount) {
							bestMove = new Move(row, col);
							bestMoveCount = countVisitor.getCount();
						}
					}

				}

			}
		}
		return bestMove;
	}

	public Move getBoardMove() {
		Othello othelloCopy = othello.copy();
		Move bestMove = new Move(-1, -1);
		int bestMoveCount = 0;

		OthelloWeightedCountVisitor countVisitor = new OthelloWeightedCountVisitor(this.player);
		for (int row = Othello.DIMENSION - 1; row >= 0; row--) {
			for (int col = Othello.DIMENSION - 1; col >= 0; col--) {
				if (row != 0 && col != 0 && row != 7 && col != 7) {

					othelloCopy = othello.copy();
					if (othelloCopy.move(row, col)) {
						this.getBestCount(othelloCopy, countVisitor);
						if (countVisitor.getCount() > bestMoveCount) {
							bestMove = new Move(row, col);
							bestMoveCount = countVisitor.getCount();

						}
					}
				}

			}
		}
		return bestMove;
	}

	private void getBestCount(Othello othello, OthelloWeightedCountVisitor countVisitor) {
		othello.accept(countVisitor);
	}

	public String toString() {
		return NAME;
	}
}
