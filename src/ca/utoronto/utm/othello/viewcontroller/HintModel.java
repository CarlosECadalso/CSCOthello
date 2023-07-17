package ca.utoronto.utm.othello.viewcontroller;

import java.util.ArrayList;

import ca.utoronto.utm.othello.model.Othello;
import ca.utoronto.utm.othello.model.OthelloBoard;
import ca.utoronto.utm.othello.model.PlayerRandom;
import ca.utoronto.utm.util.Observable;
import ca.utoronto.utm.util.Observer;

import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;

import ca.utoronto.utm.othello.model.Move;

/**
 * A model class for providing hints during the game. It contains all logic for
 * determining the hints to be displayed.
 * 
 * @author Team MarvelStudios
 */
public class HintModel implements Observer {
	private Move hint;
	private ArrayList<ArrayList<Boolean>> moves = new ArrayList<ArrayList<Boolean>>();
	{
		for (int row = 0; row < Othello.DIMENSION; row++) {
			ArrayList<Boolean> list = new ArrayList<Boolean>(Arrays.asList(new Boolean[Othello.DIMENSION]));
			Collections.fill(list, false);
			moves.add(list);
		}
	}
	private GameModel gameModel;

	public HintModel(GameModel model) {
		this.hint = null;
		this.gameModel = model;
	}

	/**
	 * Getter for player1 moves in a PlayerRandom manner.
	 * 
	 * @return PlayerRandom moves for P1
	 */
	private ArrayList<Move> getP1Moves() {
		return new PlayerRandom(gameModel.getOthello(), OthelloBoard.P1).getMoves();
	}

	/**
	 * Getter for player2 moves in a PlayerRandom manner.
	 * 
	 * @return PlayerRandom moves for P2
	 */
	private ArrayList<Move> getP2Moves() {
		return new PlayerRandom(gameModel.getOthello(), OthelloBoard.P2).getMoves();
	}

	/**
	 * Setter for the move hint
	 * 
	 * @param move
	 */
	public void setHint(Move move) {
		this.hint = move;
	}

	/**
	 * Checks if the coordinate is part of the hint moves.
	 * 
	 * @param row
	 * @param col
	 * @return if the coordinate is part of the hint moves.
	 */
	public boolean isHint(int row, int col) {
		if (this.hint != null) {
			return this.hint.getCol() == col && this.hint.getRow() == row;
		} else {
			return false;
		}
	}

	/**
	 * Setter for all the possible potential moves a player may make.
	 * 
	 * @param potential
	 */
	private void setMoves(ArrayList<Move> potential) {
		int potIndex = 0;
		MoveIterator update = new MoveIterator();
		while (update.hasNext()) {
			Move move = update.next();
			int row = move.getRow();
			int col = move.getCol();
			if (this.moves.get(row).get(col) == true) {
				this.moves.get(row).set(col, false);
			}
			if (potIndex < potential.size()) {
				Move canMove = potential.get(potIndex);
				if (move.getRow() == canMove.getRow() && move.getCol() == canMove.getCol()) {
					this.moves.get(row).set(col, true);
					potIndex++;
				}
			}

		}
	}

	/**
	 * Checks whether a move is possible to make for the specified coordinates
	 * 
	 * @param row
	 * @param col
	 * @return if a move is possible.
	 */
	public boolean isMove(int row, int col) {
		return this.moves.get(row).get(col);
	}

	/**
	 * An update method for setting the moves that a player can make through the
	 * hint
	 */
	@Override
	public void update(Observable o) {
		if (this.gameModel.getOthello().getWhosTurn() == OthelloBoard.P1) {
			this.setMoves(getP1Moves());
		} else {
			setMoves(getP2Moves());
		}
	}

	/**
	 * Represents all possible moves
	 */
	private class MoveIterator implements Iterator<Object> {

		private int position;
		private int row = 0;
		private int col = 0;

		@Override
		public boolean hasNext() {
			if (this.position < Othello.DIMENSION * Othello.DIMENSION) {
				return true;
			}
			return false;
		}

		/**
		 * @return the next move
		 */
		@Override
		public Move next() {
			Move move = new Move(this.row, this.col);

			if (this.col == Othello.DIMENSION - 1) {
				this.col = 0;
				this.row++;
				this.position++;
			} else {
				this.col++;
				this.position++;
			}
			return move;
		}

	}

}
