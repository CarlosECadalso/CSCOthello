package ca.utoronto.utm.othello.model;

import java.util.ArrayList;
import java.util.Random;

/**
 * PlayerRandom makes a move by first determining all possible moves that this
 * player can make, putting them in an ArrayList, and then randomly choosing one
 * of them.
 * 
 * @author arnold
 *
 */
public class PlayerRandom extends Player {
	public static final String NAME = "Random";
	private Random rand = new Random();

	public PlayerRandom(Othello othello, char player) {
		super(othello, player);
	}

	@Override
	public Move getMove() {
		ArrayList<Move> moves=  this.getMoves();
		try {
			return moves.get(this.rand.nextInt(moves.size()));
		} catch (Exception e) {
			return new Move(0, 0);
		}

	}
	
	public ArrayList<Move> getMoves() {
		ArrayList<Move> moves = new ArrayList<Move>();
		for (int row = 0; row < Othello.DIMENSION; row++) {
			for (int col = 0; col < Othello.DIMENSION; col++) {
				Othello othelloCopy = othello.copy();
				if (othelloCopy.move(row, col))
					moves.add(new Move(row, col));
			}
		}
		return moves;
	}

	public String toString() {
		return NAME;
	}
}
