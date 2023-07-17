package ca.utoronto.utm.othello.viewcontroller;

import ca.utoronto.utm.othello.model.OthelloBoard;
import ca.utoronto.utm.util.Observable;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;

/**
 * An event handler class for the timer
 * 
 * @author Team MarvelStudios
 */
public class OthelloTimerHandler extends Observable implements EventHandler<ActionEvent> {

  final static int DEFAULT_TIMER_START_SECONDS = 300;

  private int timeInSeconds;

  private String name;
  private OthelloTimer source;

  public OthelloTimerHandler(String name, int timeInSeconds, OthelloTimer source) {
    this.timeInSeconds = timeInSeconds;
    this.name = name;
    this.source = source;

  }

  public OthelloTimerHandler(String name, OthelloTimer source) {
    this(name, DEFAULT_TIMER_START_SECONDS, source);
  }

  /**
   * Getter for seconds in time.
   * 
   * @return time in seconds
   */
  public int getTimeInSeconds() {
    return timeInSeconds;
  }

  /**
   * Getter for a player's time left
   * 
   * @param p
   * @return
   */
  public String getTimeLeft(char p) {
    // Check who's player timer are we dealing with and decrease it
    if (p == OthelloBoard.P1 && source.isTimer1Running() && timeInSeconds > 0) {
      timeInSeconds--;
    } else if (p == OthelloBoard.P2 && source.isTimer2Running() && timeInSeconds > 0) {
      timeInSeconds--;
    }
    int secs = this.timeInSeconds % 60;
    int mins = (int) (this.timeInSeconds / 60);
    if (secs < 10) {// Display the current evaluated time
      return this.name + ": " + mins + ":0" + secs;
    } else {
      return this.name + ": " + mins + ":" + secs;
    }

  }

  /**
   * Method for resetting the time
   * 
   * @param time
   */
  public void reset(int time) {
    this.timeInSeconds = time;
    this.notifyObservers();
  }

  /**
   * Handle method for displaying the calculated and changing time.
   * 
   * @param event
   */
  @Override
  public void handle(ActionEvent event) {
    if (this.timeInSeconds > 0 && !this.source.isGameOver()) {
      this.notifyObservers();
    } else {
      this.source.notifyObservers();
    }
  }
}
