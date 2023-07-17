package ca.utoronto.utm.othello.viewcontroller;

import ca.utoronto.utm.util.Observable;
import ca.utoronto.utm.util.Observer;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.control.Label;
import javafx.util.Duration;

import ca.utoronto.utm.othello.model.OthelloBoard;

/**
 * Class that takes care of the timer in the Othello game.
 * 
 * @author MarvelStudios
 */
public class OthelloTimer extends Observable implements Observer {

  public final static int DEFAULT_TIMER_START_SECONDS = 300;
  private int time = DEFAULT_TIMER_START_SECONDS;

  private Label textTimerP1;
  private Label textTimerP2;
  private Timeline timer1;
  private OthelloTimerHandler othelloTimerHandlerP1;
  private Timeline timer2;
  private OthelloTimerHandler othelloTimerHandlerP2;
  private GameModel gameToTime;
  private Timeline timerRunning;

  public OthelloTimer(Label textTimerP1, Label textTimerP2, GameModel othello) {
    this.textTimerP1 = textTimerP1;
    this.textTimerP2 = textTimerP2;
    this.gameToTime = othello;
  }

  public OthelloTimer(GameModel othello) {
    this.gameToTime = othello;
  }

  /**
   * Checker to see if game is over or not
   * 
   * @return if the game is over
   */
  public boolean isGameOver() {
    return gameToTime.getOthello().isGameOver();
  }

  /**
   * Method that sets the labels
   * 
   * @param textTimerP1
   * @param textTimerP2
   */
  public void attachLabels(Label textTimerP1, Label textTimerP2) {
    this.textTimerP1 = textTimerP1;
    this.textTimerP2 = textTimerP2;
  }

  /**
   * Method that is responsible for the functionality of the timers displayed.
   * 
   * @param timeInSeconds
   */
  public void startTimer(int timeInSeconds) {
    this.othelloTimerHandlerP1 = new OthelloTimerHandler("P1 Timer: ", timeInSeconds, this);
    this.othelloTimerHandlerP1.attach((Observer) textTimerP1);
    this.othelloTimerHandlerP1.notifyObservers();

    this.othelloTimerHandlerP2 = new OthelloTimerHandler("P2 Timer: ", timeInSeconds, this);
    this.othelloTimerHandlerP2.attach((Observer) textTimerP2);
    this.othelloTimerHandlerP2.notifyObservers();

    this.timer1 = new Timeline(new KeyFrame(Duration.millis(1000), othelloTimerHandlerP1));

    this.timer2 = new Timeline(new KeyFrame(Duration.millis(1000), othelloTimerHandlerP2));

    this.timer1.setCycleCount(Animation.INDEFINITE);
    this.timer1.play();
    this.timerRunning = timer1;

    this.timer2.setCycleCount(Animation.INDEFINITE);
    this.timer2.stop();
  }

  /**
   * Method that starts the game timer
   */
  public void startTimer() {
    this.startTimer(time);
  }

  /**
   * Setter for the timer when a player inputs time.
   * 
   * @param time
   */
  public void setTime(int time) {
    this.time = time;
    this.reset();
  }

  /**
   * Checks if a switch in play and pause is appropriate
   * 
   * @return true if a swtich in timers is valid.
   */
  public boolean isTimerSwitchGood() {
    return this.othelloTimerHandlerP1.getTimeInSeconds() != 0 && this.othelloTimerHandlerP2.getTimeInSeconds() != 0;
  }

  /**
   * Get the other player's timer
   * 
   * @param timer
   * @return other player's timer
   */
  public Timeline otherTimer(Timeline timer) {
    if (this.timerRunning == this.timer1) {
      return this.timer2;
    } else if (this.timerRunning == this.timer2) {
      return this.timer1;
    } else {
      return null;
    }
  }

  /**
   * 
   * @param timer
   * @return a timehandler for the current running timer
   */
  public OthelloTimerHandler getTimerHandler(Timeline timer) {
    if (this.timerRunning == this.timer1) {
      return this.othelloTimerHandlerP1;
    } else if (this.timerRunning == this.timer2) {
      return this.othelloTimerHandlerP2;
    }
    return null;
  }

  /**
   * Check if both timers are at 0
   * 
   * @return true if both timers are 0
   */
  public boolean isBothTimers0() {
    return this.othelloTimerHandlerP1.getTimeInSeconds() == 0 && this.othelloTimerHandlerP2.getTimeInSeconds() == 0;
  }

  /**
   * Checker for if a timer is at 0.
   * 
   * @return true if a timer is at 0
   */
  public boolean isATimers0() {
    if (othelloTimerHandlerP1 != null && othelloTimerHandlerP2 != null) {
      return this.othelloTimerHandlerP1.getTimeInSeconds() == 0 || this.othelloTimerHandlerP2.getTimeInSeconds() == 0;
    }
    return false;
  }

  /**
   * Checker for which specific player timer is 0.
   * 
   * @return which timer is 0
   */
  public char whichTimerIs0() {
    if (this.othelloTimerHandlerP1.getTimeInSeconds() == 0) {
      return OthelloBoard.P1;
    } else if (this.othelloTimerHandlerP2.getTimeInSeconds() == 0) {
      return OthelloBoard.P2;
    }
    return OthelloBoard.EMPTY;
  }

  @Override
  public void update(Observable o) {
    if (this.gameToTime.isGameOver()) {
      this.timer1.stop();
      this.timer2.stop();
      this.timerRunning = null;

    } else {// Check what timer to play and what timer to stop
      if (this.gameToTime.getOthello().getWhosTurn() == OthelloBoard.P2) {
        this.timer2.play();
        this.timer1.pause();
        this.timerRunning = timer2;

      } else {
        this.timer1.play();
        this.timer2.pause();
        this.timerRunning = timer1;
      }
    }
  }

  /**
   * Getter for player1's timer
   * 
   * @return player1's timer
   */
  public Timeline getTimer1() {
    return this.timer1;
  }

  /**
   * Getter for player2's timer
   * 
   * @return player2's timer
   */
  public Timeline getTimer2() {
    return this.timer2;
  }

  /**
   * @return if P1's timer is running
   */
  public boolean isTimer1Running() {
    return this.timerRunning == timer1;
  }

  /**
   * @return if P2's timer is running
   */
  public boolean isTimer2Running() {
    return this.timerRunning == timer2;
  }

  /**
   * Method for resetting the timer
   */
  public void reset() {
    if (this.othelloTimerHandlerP1 != null && this.othelloTimerHandlerP2 != null) {
      this.othelloTimerHandlerP1.reset(time);
      this.othelloTimerHandlerP2.reset(time);
    }
  }

}
