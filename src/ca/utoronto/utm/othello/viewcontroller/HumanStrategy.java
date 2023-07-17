package ca.utoronto.utm.othello.viewcontroller;

import ca.utoronto.utm.othello.model.Move;
import ca.utoronto.utm.othello.model.PlayerHuman;
import ca.utoronto.utm.util.Observable;

/**
 * Mock out a human strategy. Note that all human moves are performed through
 * the game pad via clicks to the game its self.
 */
public class HumanStrategy extends PlayerStrategy {

	public HumanStrategy(GameModel model, GameController controller, char which) {
		super(model, controller, which, new PlayerHuman(model.getOthello(), which));
	}

	/**
	 * Getter for human move which is null.
	 */
	public Move getMove() {
		// human strategy is played via button clicks on the game pad
		return null;
	}

	@Override
	public void update(Observable o) {
		return;
	}

}
