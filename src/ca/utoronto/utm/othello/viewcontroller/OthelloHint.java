package ca.utoronto.utm.othello.viewcontroller;

import ca.utoronto.utm.othello.model.*;

/**
 * Class for giving AI formed hints
 * 
 * @author Team MarvelStudios
 */
public class OthelloHint {
	private GameModel othello;

	public OthelloHint(GameModel othello) {
		this.othello = othello;
	}

	/**
	 * Method for giving an improved AI hint
	 * 
	 * @param player
	 * @return improved hint
	 */
	public Move improvedHint(char player) {
		PlayerImproved improved = new PlayerImproved(this.othello.getOthello(), player);
		return improved.getMove();
	}

	/**
	 * Method for giving a greedy AI hint
	 * 
	 * @param player
	 * @return greedy hint
	 */
	public Move greedyHint(char player) {
		PlayerGreedy greedy = new PlayerGreedy(this.othello.getOthello(), player);
		return greedy.getMove();
	}

	/**
	 * Method for giving a random AI hint
	 * 
	 * @param player
	 * @return random hint
	 */
	public Move randomHint(char player) {
		PlayerRandom random = new PlayerRandom(this.othello.getOthello(), player);
		return random.getMove();
	}
}
