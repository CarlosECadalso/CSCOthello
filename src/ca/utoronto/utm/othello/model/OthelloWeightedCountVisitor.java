package ca.utoronto.utm.othello.model;

import ca.utoronto.utm.util.Visitor;

public class OthelloWeightedCountVisitor implements Visitor {

	private int count;
	private char player;

	public OthelloWeightedCountVisitor(char player) {
		this.count = 0;
		this.player = player;
	}

	public int getCount() {
		return this.count;
	}

	@Override
	public void visit(OthelloBoard othelloBoard, char[][] board) {
		for (int row = 0; row < 8; row++) {
			for (int col = 0; col < 8; col++) {
				if (othelloBoard.get(row, col) == player) {
					if (2 <= row && row >= 5 && 2 <= col && col >= 5) {
						this.count += 100;
					} else {
						this.count++;
					}
				}
			}
		}
	}

}
