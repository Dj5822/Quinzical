package quinzical.ui;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import quinzical.controller.PracticeController;
import quinzical.controller.SceneController;

public class PracticeView {

	private SceneController sceneController;
	
	private Scene mainMenuScene;
	
	private Label title;
	private Button returnToMenuButton;
	
	public PracticeView(SceneController sceneController, PracticeController controller, int width, int height) {
		
		this.sceneController = sceneController;
		
		GridPane mainPane = new GridPane();
		mainMenuScene = new Scene(mainPane, width, height);
		
		// Initialize buttons and labels.
		title = new Label("Practice View");
		returnToMenuButton = new Button("Return to menu");
		
		// On return to menu button activation.
		returnToMenuButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent arg0) {
				sceneController.showMenu();
			}
		});
		
		// Add buttons and labels to the view.
		mainPane.add(title, 0, 0);
		mainPane.add(returnToMenuButton, 0, 1);
	}
	
	public Scene getScene() {
		return mainMenuScene;
	}
}
