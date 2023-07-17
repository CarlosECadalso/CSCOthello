package ca.utoronto.utm.othello.viewcontroller;

import ca.utoronto.utm.othello.model.Move;
import ca.utoronto.utm.othello.model.PlayerImproved;

/**
 * A strategy class for the improved player
 */
public class ImprovedStrategy extends PlayerStrategy {

	public ImprovedStrategy(GameModel model, GameController controller, char which) {
		super(model, controller, which, new PlayerImproved(model.getOthello(), which));
	}

	/**
	 * Method that creates a PlayerImproved object and then returns its move.
	 */
	public Move getMove() {
		return new PlayerImproved(this.model.getOthello(), this.which).getMove();
	}
}
