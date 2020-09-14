package quinzical.ui;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import quinzical.controller.SettingsController;

public class SettingsView {
	
	private Scene main;
	
	private Label title;
	private Button returnToMenuButton;
	
	public SettingsView(SettingsController controller, int width, int height) {
		
		GridPane mainPane = new GridPane();
		main = new Scene(mainPane, width, height);
		
		// Initialize buttons and labels.
		title = new Label("Settings");
		returnToMenuButton = new Button("Return to menu");
		
		// Add buttons and labels to the view.
		mainPane.add(title, 0, 0);
		mainPane.add(returnToMenuButton, 0, 1);
		
		// Button functionality
		returnToMenuButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent arg0) {
				controller.returnToLastScene();
			}
		});		
	}
	
	public Scene getScene() {
		return main;
	}
}
