package quinzical.ui;

import java.io.File;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
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
	
	private GridPane menuBar;
	private Label title;
	private Button practiceButton;
	private Button gameButton;
	private Button settingsButton;
	private Button quitButton;
	
	public MenuView(MenuController controller, int width, int height) {
		
		final int BUTTONWIDTH = width/5;
		final int BUTTONHEIGHT = height/9;
		final int TITLEFONTSIZE = height/9;
		
		GridPane mainPane = new GridPane();
		main = new Scene(mainPane, width, height);
		File styleFile = new File("./src/quinzical/style.css");
		main.getStylesheets().clear();
		main.getStylesheets().add("file:///" + styleFile.getAbsolutePath().replace("\\", "/"));
		
		// The title of the application.
		title = new Label("Quinzical");
		
		// Buttons will be placed on the menubar.
		menuBar = new GridPane();
		practiceButton = new Button("Practice");
		gameButton = new Button("Play Game");
		settingsButton = new Button("Settings");
		quitButton = new Button("Quit");
		
		// Mainpane settings.
		mainPane.setVgap(height/15);
		mainPane.setAlignment(Pos.CENTER);
		mainPane.setStyle("-fx-background-color: white");
		
		// Title label settings.
		title.setTextAlignment(TextAlignment.CENTER);
		title.setAlignment(Pos.CENTER);
		title.setFont(new Font(TITLEFONTSIZE));
		GridPane.setHalignment(title, HPos.CENTER);

		// Button sizes.
		practiceButton.setPrefHeight(BUTTONHEIGHT);
		gameButton.setPrefHeight(BUTTONHEIGHT);
		settingsButton.setPrefHeight(BUTTONHEIGHT);
		quitButton.setPrefHeight(BUTTONHEIGHT);
		practiceButton.setPrefWidth(BUTTONWIDTH);
		gameButton.setPrefWidth(BUTTONWIDTH);
		settingsButton.setPrefWidth(BUTTONWIDTH);
		quitButton.setPrefWidth(BUTTONWIDTH);
		practiceButton.setFocusTraversable(false);
		gameButton.setFocusTraversable(false);
		settingsButton.setFocusTraversable(false);
		quitButton.setFocusTraversable(false);
		
		// Add buttons and labels to the menubar.
		menuBar.add(practiceButton, 0, 0);
		menuBar.add(gameButton, 1, 0);
		menuBar.add(settingsButton, 2, 0);
		menuBar.add(quitButton, 3, 0);
		
		// add compoments to mainpane.
		mainPane.add(title, 0, 0);
		mainPane.add(menuBar, 0, 1);
		
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
