package quinzical.ui;

import java.io.File;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import quinzical.controller.GameController;

/**
 * This class manage the view of game mode of quinzical.
 * Contains method to change the view when users are playing.
 */
public class GameView {
	
	private Scene main;
	
	private Button startButton;
	private Button submitButton;
	private Button dontKnowButton;
	private Button returnToMenuButton;
	private Button settingsButton;

	private Label winningLabel;
	private Label endingLabel;
	private Label hintLabel;
	
	private Label[] categoryLabels;
	private Button[][] clueButtons;
	private TextField inputField;
	
	public GameView(GameController controller, int width, int height) {
		
		// Setup main pane.		
		GridPane mainPane = new GridPane();
		mainPane.setPadding(new Insets(20,20,20,20));
		mainPane.setAlignment(Pos.CENTER);
		mainPane.setVgap(20);
		mainPane.setHgap(20);
		
		// Initialize buttons and labels.
		startButton = new Button("Start/Reset the game");
		winningLabel = new Label("Current Worth: $0");
		categoryLabels= new Label[5] ;
		clueButtons = new Button[5][5];
		endingLabel = new Label(""); // label to notify the user all questions are completed
		hintLabel = new Label("Click one of the available buttons above to hear a clue~"); // instruction label set up
		inputField = new TextField();
		submitButton = new Button("Submit my answer!");
		dontKnowButton = new Button("don't know");
		returnToMenuButton = new Button("Return to menu");
		settingsButton = new Button("Voice speed setting");
		for(int col=0;col<5;col++) {
			// Creating 5 categories labels.
			categoryLabels[col]=new Label("{Category No: "+col+"}");
			categoryLabels[col].setMinWidth(150);
			for (int row=1;row<=5;row++) {
				// Creating 5x5 grid clue buttons.
				clueButtons[col][row-1]=new Button(Integer.toString(row*100));
				clueButtons[col][row-1].setId(Integer.toString(10*(col)+row+1));
				clueButtons[col][row-1].setDisable(true);
				clueButtons[col][row-1].setFocusTraversable(false);
			}
		}
		
		// set component sizes
		startButton.setPrefHeight(height/9);
		submitButton.setPrefHeight(height/9);
		dontKnowButton.setPrefHeight(height/9);
		returnToMenuButton.setPrefHeight(height/9);
		settingsButton.setPrefHeight(height/9);
		
		startButton.setPrefWidth(width/3);
		submitButton.setPrefWidth(width/3);
		dontKnowButton.setPrefWidth(width/3);
		returnToMenuButton.setPrefWidth(width/3);
		settingsButton.setPrefWidth(width/3);
		
		// set component visibility.
		endingLabel.setVisible(false);
		hintLabel.setWrapText(true);
		hintLabel.setVisible(false);
		inputField.setVisible(false);
		submitButton.setVisible(false);
		dontKnowButton.setVisible(false);
		startButton.setFocusTraversable(false);
		submitButton.setFocusTraversable(false);
		dontKnowButton.setFocusTraversable(false);
		returnToMenuButton.setFocusTraversable(false);
		settingsButton.setFocusTraversable(false);
		
		// Add buttons and labels to the view.
		mainPane.add(startButton, 0, 0, 2, 1);
		mainPane.add(winningLabel, 2, 0, 2, 1);
		mainPane.add(endingLabel, 0, 2, 5, 2);
		mainPane.add(hintLabel, 0, 7, 5, 2);
		mainPane.add(inputField, 0, 9, 5, 1);
		mainPane.add(submitButton, 0, 10, 2, 1);
		mainPane.add(dontKnowButton, 2, 10, 2, 1);
		mainPane.add(returnToMenuButton, 0, 11, 3, 1);
		mainPane.add(settingsButton, 3, 11, 2, 1);
		for(int col=0;col<5;col++) {
			mainPane.add(categoryLabels[col], col, 1);
			for (int row=0;row<5;row++) {
				mainPane.add(clueButtons[col][4-row], col, row+2);
			}
		}
		
		main = new Scene(mainPane, width, height);
		
		// Makes everything look prettier.
		File styleFile = new File("./src/quinzical/style.css");
		main.getStylesheets().clear();
		main.getStylesheets().add("file:///" + styleFile.getAbsolutePath().replace("\\", "/"));
		
		// Button functionality
		startButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent arg0) {
				controller.startButtonPressed(controller, winningLabel, categoryLabels,
						clueButtons, endingLabel, hintLabel, inputField, submitButton, dontKnowButton);
			}
		});
		
		submitButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent arg0) {
				controller.submitButtonPressed(inputField, hintLabel, clueButtons,
						winningLabel, submitButton, dontKnowButton, endingLabel);
			}
		});
		
		dontKnowButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent arg0) {
				controller.dontKnowButtonPressed(clueButtons, hintLabel,
						submitButton, inputField, dontKnowButton, endingLabel);
			}
		});		
		returnToMenuButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent arg0) {
				controller.returnToMenu();
			}
		});		
		settingsButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent arg0) {
				controller.goToSettings();
			}
		});

		//set up all clue buttons here
		for(int colindex=0;colindex<5;colindex++) {
			for (int rowindex=0;rowindex<5;rowindex++) {
				clueButtons[colindex][rowindex].setOnAction(new EventHandler<ActionEvent>() {
					@Override
					public void handle(ActionEvent arg0) {
						controller.clueButtonPressed(arg0, 
								hintLabel, inputField, submitButton, dontKnowButton, clueButtons);
					}
				});		
			}
		}
	}
	
	public Scene getScene() {
		return main;
	}
}
