package ca.utoronto.utm.othello.viewcontroller.view;

import java.nio.file.Paths;

import ca.utoronto.utm.othello.viewcontroller.GameController;
import ca.utoronto.utm.othello.viewcontroller.GameModel;
import ca.utoronto.utm.othello.viewcontroller.OthelloTimer;
import ca.utoronto.utm.util.Observable;
import ca.utoronto.utm.util.Observer;
import javafx.animation.FadeTransition;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Duration;

public class View extends VBox implements Observer {
  private GameModel model;
  private TimerSelector timerSelector;
  private GameController controller;

  private GamePad gamePad;
  private DataDisplay dataDisplay;
  private Stage stage;
  private ImageView selectedImage = new ImageView();

  public View(GameModel model, GameController controller, Stage stage) {
    this.model = model;
    this.controller = controller;
    timerSelector = new TimerSelector(model);
    this.getChildren().add(timerSelector);
    this.stage = stage;
    this.getChildren().add(new PlayerPickerDisplay(controller, this));
    stage.setResizable(false);
    Image b = new Image("file:" + Paths.get("resources", "checkerboard_d.gif"));
    selectedImage.setImage(b);
    this.getChildren().addAll(selectedImage);

    Button rules = new RulesButton();
    this.getChildren().add(rules);

    stage.setScene(new Scene(this));
    stage.setHeight(420);
    stage.setWidth(426);

    this.setPadding(new Insets(10));

  }

  void buildDisplay() {
    if (gamePad == null && dataDisplay == null) {
      this.getChildren().removeAll(this.selectedImage);
      gamePad = new GamePad(model, controller);

      FadeTransition fade = new FadeTransition(Duration.seconds(2), gamePad);
      fade.setFromValue(1);
      fade.setToValue(0);
      fade.setAutoReverse(false);
      fade.setCycleCount(1);
      FadeTransition ft = new FadeTransition(Duration.millis(1000), gamePad);
      ft.setFromValue(0.0);
      ft.setToValue(1.0);
      ft.play();

      this.getChildren().add(gamePad);

      dataDisplay = new DataDisplay(model, controller);
      this.getChildren().add(dataDisplay);
      stage.setHeight(670);
      stage.setWidth(480);
      stage.setResizable(false);
      model.getTimer().startTimer();
    }
  }

  @Override
  public void update(Observable o) {
    if (o instanceof OthelloTimer) {
      model.resetModel();
      this.getChildren().remove(gamePad);
      gamePad = null;
      dataDisplay = null;
    }
  }

}