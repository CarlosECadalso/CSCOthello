package ca.utoronto.utm.othello.viewcontroller;

import ca.utoronto.utm.othello.model.Command;
import ca.utoronto.utm.othello.model.Move;
import ca.utoronto.utm.othello.model.Othello;
import javafx.application.Platform;

/**
 * A class that implements the command interface and is responsible for tracking
 * player moves
 * 
 * @author Team MarvelStudios
 * 
 */
public class MoveCommand implements Command {
	private Move move;
	private boolean isAi;
	private Othello prevState;
	private Othello nextState;

	public MoveCommand(Move move, boolean isAi) {
		this.move = move;
		this.isAi = isAi;
	}

	/**
	 * @return the move for this command
	 */
	public Move getMove() {
		return this.move;
	}

	/**
	 * @return true if the move was made by an AI
	 */
	public boolean getIsAi() {
		return isAi;
	}

	/**
	 * @return the previous board state before the move was made
	 */
	public Othello getPrevState() {
		return this.prevState;
	}

	/**
	 * 
	 * @return the resulting board state of the move
	 */
	public Othello getNextState() {
		return nextState;
	}

	/**
	 * Undo the command
	 */
	public void undo(GameModel model) {
		if (prevState != null) {
			model.othello = prevState;
			model.getHintModel().update(model);
		}
	}

	/**
	 * @return a random number between min and min*2.2
	 */
	private int getRandom(int min) {
		int max = (int) Math.round(min * 2.2);
		return min + (int) (Math.random() * ((max - min) + 1));
	}

	public void execute(GameModel model) {
		// Create a new thread, because if you call sleep without using a thread then
		// everything will pause.
		new Thread(new Runnable() {
			@Override
			public void run() {
				prevState = model.getOthello().copy();
				try {
					if (isAi) {
						// ensure that the previous animation has completed and delay the bot move
						int maxDelay = model.getMaxAnimationDelay();
						Thread.sleep(getRandom(maxDelay > 600 ? maxDelay : 600));
					}
				} catch (InterruptedException e) {
				}
				// This places a job in the UI update queue
				Platform.runLater(new Runnable() {
					public void run() {
						model.getOthello().move(MoveCommand.this.move.getRow(), MoveCommand.this.move.getCol());
						model.getHintModel().setHint(null);
						model.notifyObservers();

						nextState = model.getOthello().copy();
					}
				});
			}
		}).start();
	}

}
