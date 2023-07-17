package ca.utoronto.utm.othello.viewcontroller;

/**
 * Factory class for creating concrete strategy objects.
 * 
 * @author Team MarvelStudios
 */
public class OthelloPlayerStrategyFactory {

	public OthelloPlayerStrategyFactory() {
	}

	public PlayerStrategy toggleStrategy(GameModel model, GameController controller, String type, char which) {
		PlayerStrategy strategy = null;
		switch (type) {
		case "Human Player":
			strategy = new HumanStrategy(model, controller, which);
			break;
		case "Random Player":
			strategy = new RandomStrategy(model, controller, which);
			break;
		case "Greedy Player":
			strategy = new GreedyStrategy(model, controller, which);
			break;
		case "Improved Player":
			strategy = new ImprovedStrategy(model, controller, which);
			break;
		}
		return strategy;

	}

}
