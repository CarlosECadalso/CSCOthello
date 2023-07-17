package ca.utoronto.utm.othello.viewcontroller.view;

import javafx.scene.image.*;

import java.nio.file.Paths;

/**
 * A class that creates images for the tokens displayed in the player data
 * 
 * @author Team MarvelStudios
 */
class TokenImage extends ImageView {
  private static String black = "file:" + Paths.get("resources", "black_circle.png");
  private static String white = "file:" + Paths.get("resources", "white_circle.png");
  private static String empty = "file:" + Paths.get("resources", "empty_circle.png");

  static Image BLACK = new Image(black);
  static Image WHITE = new Image(white);
  static Image EMPTY = new Image(empty);

  TokenImage(Image type) {
    super(type);
    this.setFitWidth(30);
    this.setFitHeight(30);
  }

  TokenImage(Image type, int width, int height) {
    super(type);
    this.setFitWidth(width);
    this.setFitHeight(height);
  }

}