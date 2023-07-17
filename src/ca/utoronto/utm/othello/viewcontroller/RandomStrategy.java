package ca.utoronto.utm.othello.viewcontroller;

import ca.utoronto.utm.othello.model.Move;
import ca.utoronto.utm.othello.model.PlayerRandom;

/**
 * A concrete strategy class for a Random Player.
 * 
 * @author Team MarvelStudios
 */
public class RandomStrategy extends PlayerStrategy {

	public RandomStrategy(GameModel model, GameController controller, char which) {
		super(model, controller, which, new PlayerRandom(model.getOthello(), which));
	}

	/**
	 * Method that creates a Random player and returns its move.
	 */
	public Move getMove() {
		return new PlayerRandom(model.getOthello(), which).getMove();
	}
}
