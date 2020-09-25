package quinzical.ui;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;
import quinzical.controller.MenuController;

public class MenuView {
	
	private Scene main;
	
	private Label title;
	private Button practiceButton;
	private Button gameButton;
	private Button settingsButton;
	private Button quitButton;
	
	public MenuView(MenuController controller, int width, int height) {
		
		GridPane mainPane = new GridPane();
		main = new Scene(mainPane, width, height);
		
		// Initialize buttons and labels.
		title = new Label("Quinzical");
		practiceButton = new Button("Practice");
		gameButton = new Button("Play Game");
		settingsButton = new Button("Settings");
		quitButton = new Button("Quit");
		
		// mainPane settings
		mainPane.setVgap(height/30);
		mainPane.setAlignment(Pos.CENTER);
		
		// Label settings
		title.setTextAlignment(TextAlignment.CENTER);
		title.setAlignment(Pos.CENTER);
		title.setFont(new Font(height/9));
		title.setPadding(new Insets(0, width/20, 0, width/20));

		// Button sizes.
		practiceButton.setPrefHeight(height/9);
		gameButton.setPrefHeight(height/9);
		settingsButton.setPrefHeight(height/9);
		quitButton.setPrefHeight(height/9);
		practiceButton.setPrefWidth(width/3);
		gameButton.setPrefWidth(width/3);
		settingsButton.setPrefWidth(width/3);
		quitButton.setPrefWidth(width/3);
		
		// Add buttons and labels to the view.
		mainPane.add(title, 0, 0);
		mainPane.add(practiceButton, 0, 1);
		mainPane.add(gameButton, 0, 2);
		mainPane.add(settingsButton, 0, 3);
		mainPane.add(quitButton, 0, 4);
		
		// On practice button activation.
		practiceButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent arg0) {
				controller.showPracticeView();
			}
		});

		// On game button activation.
		gameButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent arg0) {
				controller.showGameView();
			}
		});
		
		// On settings button activation.
		settingsButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent arg0) {
				controller.showSettingsView();
			}
		});

		// on quit button activation.
		quitButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent arg0) {
				controller.quitGame();
			}
		});
	}
	
	public Scene getScene() {
		return main;
	}
}
