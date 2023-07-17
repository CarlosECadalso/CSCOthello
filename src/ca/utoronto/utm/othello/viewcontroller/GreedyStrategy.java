package ca.utoronto.utm.othello.viewcontroller;

import ca.utoronto.utm.othello.model.Move;
import ca.utoronto.utm.othello.model.PlayerGreedy;

/**
 * A concrete strategy class for the Greedy Player
 * 
 * @author Team MarvelStudios
 */
public class GreedyStrategy extends PlayerStrategy {

	public GreedyStrategy(GameModel model, GameController controller, char which) {
		super(model, controller, which, new PlayerGreedy(model.getOthello(), which));
	}

	/**
	 * @return a move for the greedy player
	 */
	public Move getMove() {
		return new PlayerGreedy(model.getOthello(), which).getMove();
	}
}
