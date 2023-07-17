package ca.utoronto.utm.util;

import ca.utoronto.utm.othello.model.OthelloBoard;

public interface Visitor {

	public void visit(OthelloBoard othelloBoard, char[][] board);

}
