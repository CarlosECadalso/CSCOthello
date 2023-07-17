package ca.utoronto.utm.othello.viewcontroller.view;

import java.nio.file.Paths;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

/**
 * A class that displays a pop-up to display of the game rules.
 * 
 * @author Team MarvelStudios
 */
class RulesButton extends Button {
	RulesButton() {
		super("Game Rules");
		this.setOnAction(new RulesButtonEventHandler());
	}
}

class RulesButtonEventHandler implements EventHandler<ActionEvent> {
	private boolean isOpen = false;

	@Override
	public void handle(ActionEvent event) {
		if (!isOpen) {
			Stage newStage = new Stage();
			VBox comp = new VBox();
			newStage.setResizable(false);

			// Set the image dimensions and attach it to the window
			ImageView selectedImage = new ImageView();
			Image b = new Image("file:" + Paths.get("resources", "rules.png"));
			selectedImage.setImage(b);
			selectedImage.setFitHeight(375);
			selectedImage.setFitWidth(600);
			comp.getChildren().addAll(selectedImage);

			// Set the scene and show the stage
			newStage.setScene(new Scene(comp));
			newStage.show();
			isOpen = true;

			newStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
				public void handle(WindowEvent event) {
					RulesButtonEventHandler.this.isOpen = false;
				}
			});
		}
	}

}