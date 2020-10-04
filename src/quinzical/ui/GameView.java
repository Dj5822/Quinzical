package quinzical.ui;

import java.io.File;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;
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
	private GridPane menuGrid;

	private Label winningLabel;
	private Label endingLabel;
	private Label hintLabel;
	
	private Label[] categoryLabels;
	private Button[][] clueButtons;
	private TextField inputField;
	private GridPane gameGrid;
	
	public GameView(GameController controller, int width, int height) {
		
		// Setup main pane.		
		GridPane mainPane = new GridPane();
		mainPane.setAlignment(Pos.BOTTOM_CENTER);
		mainPane.setVgap(height/30);
		mainPane.setStyle("-fx-background-color: #edf4fc");
		gameGrid = new GridPane();
		gameGrid.setHgap(width/20);
		menuGrid = new GridPane();
		menuGrid.setStyle("-fx-background-color: white");
		
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
			categoryLabels[col] = new Label();
			categoryLabels[col].setMinWidth(150);
			categoryLabels[col].setAlignment(Pos.CENTER);
			categoryLabels[col].setTextAlignment(TextAlignment.CENTER);
			categoryLabels[col].setFont(new Font(30));
			GridPane.setHalignment(categoryLabels[col], HPos.CENTER);
			for (int row=1;row<=5;row++) {
				// Creating 5x5 grid clue buttons.
				clueButtons[col][row-1]=new Button(Integer.toString(row*100));
				clueButtons[col][row-1].setId(Integer.toString(10*(col)+row-1));
				clueButtons[col][row-1].setDisable(true);
				clueButtons[col][row-1].setFocusTraversable(false);
				clueButtons[col][row-1].setAlignment(Pos.CENTER);
				GridPane.setHalignment(clueButtons[col][row-1], HPos.CENTER);
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
		
		inputField.setPrefHeight(height/9);
		inputField.setPrefWidth(width/1.1);
		
		inputField.setFont(new Font(30));
		winningLabel.setFont(new Font(30));
		endingLabel.setFont(new Font(30));
		hintLabel.setFont(new Font(30));
		
		// Set alignmnet
		gameGrid.setAlignment(Pos.CENTER);
		menuGrid.setAlignment(Pos.BOTTOM_CENTER);
		submitButton.setAlignment(Pos.CENTER);
		dontKnowButton.setAlignment(Pos.CENTER);
		GridPane.setHalignment(gameGrid, HPos.CENTER);
		GridPane.setHalignment(winningLabel, HPos.CENTER);
		GridPane.setHalignment(hintLabel, HPos.CENTER);
		GridPane.setHalignment(menuGrid, HPos.CENTER);
		GridPane.setHalignment(submitButton, HPos.CENTER);
		GridPane.setHalignment(dontKnowButton, HPos.CENTER);
		GridPane.setValignment(menuGrid, VPos.BOTTOM);
		
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
		for(int col=0;col<5;col++) {
			gameGrid.add(categoryLabels[col], col, 0);
			for (int row=0;row<5;row++) {
				gameGrid.add(clueButtons[col][4-row], col, row+1);
			}
		}
		
		menuGrid.add(startButton, 0, 0);
		menuGrid.add(settingsButton, 1, 0);
		menuGrid.add(returnToMenuButton, 2, 0);
		
		mainPane.add(winningLabel, 0, 0, 2, 1);
		
		mainPane.add(gameGrid, 0, 1, 2, 1);
		mainPane.add(endingLabel, 0, 1, 2, 1);
		
		mainPane.add(hintLabel, 0, 2, 2, 1);
		
		mainPane.add(inputField, 0, 3, 2, 1);
		
		mainPane.add(submitButton, 0, 4);
		mainPane.add(dontKnowButton, 1, 4);
		
		mainPane.add(menuGrid, 0, 5, 2, 1);

		
		
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
						clueButtons, endingLabel, hintLabel, inputField, submitButton, dontKnowButton, gameGrid);
			}
		});
		
		submitButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent arg0) {
				controller.submitButtonPressed(inputField, hintLabel, clueButtons,
						winningLabel, submitButton, dontKnowButton, endingLabel, gameGrid);
			}
		});
		
		dontKnowButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent arg0) {
				controller.dontKnowButtonPressed(clueButtons, hintLabel,
						submitButton, inputField, dontKnowButton, endingLabel, gameGrid);
			}
		});		
		returnToMenuButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent arg0) {
				controller.returnToMenu(controller, winningLabel, categoryLabels,
						clueButtons, endingLabel, hintLabel, inputField, submitButton, dontKnowButton, gameGrid);
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
