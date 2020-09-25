package quinzical.ui;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.layout.GridPane;
import quinzical.controller.SettingsController;

public class SettingsView {
	
	private Scene main;
	
	private Label title;
	private Label speechSpeedLabel;
	private Slider speechSpeedSlider;
	private Label testLabel;
	private Button testButton;
	private Button returnToMenuButton;
	
	public SettingsView(SettingsController controller, int width, int height) {
		
		GridPane mainPane = new GridPane();
		main = new Scene(mainPane, width, height);
		
		// Initialize buttons and labels.
		title = new Label("Settings");
		speechSpeedLabel = new Label("Speech speed: ");
		speechSpeedSlider = new Slider();
		testLabel = new Label(controller.getTestText());
		testButton = new Button("Test");
		returnToMenuButton = new Button("Return to menu");
		
		// Add buttons and labels to the view.
		mainPane.add(title, 0, 0, 2, 1);
		mainPane.add(testLabel, 0, 1);
		mainPane.add(testButton, 1, 1);
		mainPane.add(speechSpeedLabel, 0, 2);
		mainPane.add(speechSpeedSlider, 1, 2);
		mainPane.add(returnToMenuButton, 0, 3, 2, 1);
		
		// Button functionality
		testButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent arg0) {
				controller.testSpeech();
			}
		});		
		
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
