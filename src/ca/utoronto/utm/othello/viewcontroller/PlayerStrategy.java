package ca.utoronto.utm.othello.viewcontroller;

import ca.utoronto.utm.othello.model.Command;
import ca.utoronto.utm.othello.model.Move;
import ca.utoronto.utm.othello.model.Player;
import ca.utoronto.utm.util.Observable;
import ca.utoronto.utm.util.Observer;

/**
 * An abstract Strategy class for the concrete strategies used in the game.
 * 
 * @author Team MarvelStudios
 */
public abstract class PlayerStrategy implements Observer {
	protected GameModel model;
	protected GameController controller;
	protected Player player;
	protected char which;

	public PlayerStrategy(GameModel model, GameController controller, char which, Player player) {
		this.model = model;
		this.which = which;
		this.player = player;
		this.controller = controller;
	}

	/**
	 * Makes a move for a particular strategy using move command
	 */
	public void makeMove() {
		MoveCommand command = new MoveCommand(this.getMove(), true);
		this.addCommand(command);

	}

	/**
	 * Add a new command to execute
	 */
	public void addCommand(Command command) {
		this.controller.addCommand(command);
	}

	/**
	 * Setter for the model's current player
	 */
	public void setPlayer() {
		this.model.setPlayer(this.player);
	}

	/**
	 * Abstract method for a getter for Move.
	 * 
	 * @return
	 */
	public abstract Move getMove();

	/**
	 * An update method for the player strategy to be displayed/updated in the board
	 * 
	 * @param o
	 */
	@Override
	public void update(Observable o) {
		if (!(this.model.isGameOver()) && (this.model.getCurrentPlayer().getPlayer() == this.player.getPlayer())) {
			this.makeMove();
		}
	}

}
