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
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;
import quinzical.controller.GameController;

/**
 * This class manage the view of game mode of quinzical.
 * Contains method to change the view when users are playing.
 */
public class GameView {
	
	private Scene main;
	
	// main pane components.
	private GridPane mainPane;
	private Button startButton;
	private Button submitButton;
	private Button dontKnowButton;
	private Button returnToMenuButton;
	private Button settingsButton;
	private GridPane menuGrid;
	private Label winningLabel;
	private Label timerLabel;
	
	private VBox reward;
	private Label rewardUsername;
	private Label rewardWinningLabel;
	private Label rewardWinning;
	private Label rewardCorrectNumberLabel;
	private Label rewardCorrectNumber;
	private Button ToLeaderBoardButton;
	
	private Label hintLabel;
	private Label[] categoryLabels;
	private Button[][] clueButtons;
	private TextField inputField;
	private GridPane gameGrid;
	
	// selection pane components.
	private GridPane selectionPane;
	private Label gameSelectionLabel;
	private Button nzButton;
	private Button internationalButton;
	
	// name pane components.
	private GridPane namePane;
	private Label nameLabel;
	private TextField nameTextbox;
	private Button submitNameButton;
	
	public GameView(GameController controller, int width, int height) {
		
		setupNamePane(controller, width, height);
		setupMainPane(controller, width, height);
		setupSelectionPane(controller, width, height);
		
		main = new Scene(selectionPane, width, height);
		
		controller.setup(main, mainPane, selectionPane, namePane, nameTextbox, winningLabel, timerLabel, categoryLabels, clueButtons,
				reward, rewardUsername, rewardWinning, rewardCorrectNumber, hintLabel, inputField, submitButton, dontKnowButton, gameGrid);

		// Makes everything look prettier.
		File styleFile = new File("./src/quinzical/style.css");
		main.getStylesheets().clear();
		main.getStylesheets().add("file:///" + styleFile.getAbsolutePath().replace("\\", "/"));
	}
	
	private void setupSelectionPane(GameController controller, int width, int height) {
		// selection pane.
		selectionPane = new GridPane();
		selectionPane.setAlignment(Pos.CENTER);
		selectionPane.setVgap(height/15);
		selectionPane.setStyle("-fx-background-color: #edf4fc");
		
		// components.
		gameSelectionLabel = new Label("Please select game mode");
		nzButton = new Button("New Zealand");
		internationalButton = new Button("International");
		
		// adding components to pane.
		selectionPane.add(gameSelectionLabel, 0, 0, 2, 1);
		selectionPane.add(nzButton, 0, 1);
		selectionPane.add(internationalButton, 1, 1);
		
		// resizing components.
		gameSelectionLabel.setFont(new Font(height/9));
		nzButton.setPrefWidth(width/2);
		nzButton.setPrefHeight(height);
		internationalButton.setPrefWidth(width/2);
		internationalButton.setPrefHeight(height);
		GridPane.setHalignment(gameSelectionLabel, HPos.CENTER);
		GridPane.setHalignment(nzButton, HPos.CENTER);
		GridPane.setHalignment(internationalButton, HPos.CENTER);
		nzButton.setFocusTraversable(false);
		internationalButton.setFocusTraversable(false);
		
		// Button functionality
		nzButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent arg0) {
				controller.nzButtonPressed();
			}
		});
		
		internationalButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent arg0) {
				controller.internationalButtonPressed();
			}
		});
	}

	
	private void setupMainPane(GameController controller, int width, int height) {
		// Setup main pane.		
		mainPane = new GridPane();
		mainPane.setAlignment(Pos.BOTTOM_CENTER);
		mainPane.setVgap(height/30);
		mainPane.setStyle("-fx-background-color: #edf4fc");
		gameGrid = new GridPane();
		gameGrid.setHgap(width/15);
		gameGrid.setVgap(height/30);
		reward = new VBox();
		reward.setSpacing(height/10);
		menuGrid = new GridPane();
		menuGrid.setStyle("-fx-background-color: white");

		// Initialize buttons and labels.
		startButton = new Button("Start/Reset the game");
		winningLabel = new Label("Current Worth: $0");
		categoryLabels= new Label[5] ;
		clueButtons = new Button[5][5];
		timerLabel = new Label("Select a clue to start timer.");
		rewardUsername = new Label();
		rewardWinningLabel = new Label("TOTAL WINNING:");
		rewardWinning = new Label();
		rewardCorrectNumberLabel = new Label("Number of correct answers:");
		rewardCorrectNumber = new Label();
		ToLeaderBoardButton = new Button("Click to go to Leader board");

		hintLabel = new Label("Click one of the available buttons above to hear a clue."); // instruction label set up
		inputField = new TextField();
		submitButton = new Button("Submit my answer!");
		dontKnowButton = new Button("Don't know");
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
		ToLeaderBoardButton.setPrefHeight(height/9);

		startButton.setPrefWidth(width/3);
		submitButton.setPrefWidth(width/3);
		dontKnowButton.setPrefWidth(width/3);
		returnToMenuButton.setPrefWidth(width/3);
		settingsButton.setPrefWidth(width/3);
		ToLeaderBoardButton.setPrefWidth(width/3);

		inputField.setPrefHeight(height/9);
		inputField.setPrefWidth(width/1.1);

		rewardUsername.setFont(new Font(60));
		rewardWinningLabel.setFont(new Font(30));
		rewardWinning.setFont(new Font(40));
		rewardCorrectNumberLabel.setFont(new Font(30));
		rewardCorrectNumber.setFont(new Font(40));
		
		inputField.setFont(new Font(30));
		winningLabel.setFont(new Font(30));
		hintLabel.setFont(new Font(30));
		timerLabel.setFont(new Font(30));
		ToLeaderBoardButton.setFont(new Font(30));

		
		// Set alignmnet
		GridPane.setHalignment(gameGrid, HPos.CENTER);
		GridPane.setHalignment(winningLabel, HPos.CENTER);
		GridPane.setHalignment(hintLabel, HPos.CENTER);
		GridPane.setHalignment(menuGrid, HPos.CENTER);
		GridPane.setHalignment(submitButton, HPos.CENTER);
		GridPane.setHalignment(dontKnowButton, HPos.CENTER);
		GridPane.setValignment(menuGrid, VPos.BOTTOM);
		GridPane.setHalignment(timerLabel, HPos.CENTER);
		GridPane.setHalignment(rewardUsername, HPos.CENTER);
		GridPane.setHalignment(rewardWinningLabel, HPos.CENTER);
		GridPane.setHalignment(rewardWinning, HPos.CENTER);
		GridPane.setHalignment(rewardCorrectNumberLabel, HPos.CENTER);
		GridPane.setHalignment(rewardCorrectNumber, HPos.CENTER);
		GridPane.setHalignment(ToLeaderBoardButton, HPos.CENTER);
		
		gameGrid.setAlignment(Pos.CENTER);
		reward.setAlignment(Pos.CENTER);
		menuGrid.setAlignment(Pos.BOTTOM_CENTER);
		submitButton.setAlignment(Pos.CENTER);
		dontKnowButton.setAlignment(Pos.CENTER);
		ToLeaderBoardButton.setAlignment(Pos.CENTER);

		// set component visibility.
		reward.setVisible(false);
		hintLabel.setWrapText(true);
		hintLabel.setVisible(false);
		ToLeaderBoardButton.setFocusTraversable(false);
		inputField.setVisible(false);
		submitButton.setVisible(false);
		dontKnowButton.setVisible(false);
		timerLabel.setVisible(false);
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
				clueButtons[col][row].setFocusTraversable(false);
			}
		}
		reward.getChildren().add(rewardUsername);
		reward.getChildren().add(rewardWinningLabel);
		reward.getChildren().add(rewardWinning);
		reward.getChildren().add(rewardCorrectNumberLabel);
		reward.getChildren().add(rewardCorrectNumber);
		reward.getChildren().add(ToLeaderBoardButton);

		menuGrid.add(startButton, 0, 0);
		menuGrid.add(settingsButton, 1, 0);
		menuGrid.add(returnToMenuButton, 2, 0);

		mainPane.add(winningLabel, 0, 0, 3, 1);

		mainPane.add(gameGrid, 0, 1, 3, 1);
		mainPane.add(reward, 0, 0, 3, 5);

		mainPane.add(hintLabel, 0, 2, 3, 1);

		mainPane.add(inputField, 0, 3, 3, 1);
		
		mainPane.add(timerLabel, 0, 4, 3, 1);
		
		mainPane.add(submitButton, 1, 5);
		mainPane.add(dontKnowButton, 2, 5);

		mainPane.add(menuGrid, 0, 6, 3, 1);

		// Button functionality
		startButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent arg0) {
				controller.startButtonPressed();
			}
		});

		submitButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent arg0) {
				controller.submitButtonPressed();
			}
		});

		dontKnowButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent arg0) {
				controller.dontKnowButtonPressed();
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
		ToLeaderBoardButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent arg0) {
				controller.goToLeaderBoard();
			}
		});
		//set up all clue buttons here
		for(int colindex=0;colindex<5;colindex++) {
			for (int rowindex=0;rowindex<5;rowindex++) {
				clueButtons[colindex][rowindex].setOnAction(new EventHandler<ActionEvent>() {
					@Override
					public void handle(ActionEvent arg0) {
						controller.clueButtonPressed(arg0);
					}
				});		
			}
		}
	}
	
	
	private void setupNamePane(GameController controller, int width, int height) {
		
		// setup the name pane.
		namePane = new GridPane();
		namePane.setAlignment(Pos.CENTER);
		namePane.setVgap(height/15);
		namePane.setStyle("-fx-background-color: #edf4fc");
		
		// initialise components.
		nameLabel = new Label("Enter your name: ");
		nameTextbox = new TextField();
		submitNameButton = new Button("Submit");
		
		// add components to name pane.
		namePane.add(nameLabel, 0, 0);
		namePane.add(nameTextbox, 0, 1);
		namePane.add(submitNameButton, 0, 2);
		
		// resize components.
		nameLabel.setFont(new Font(height/9));
		nameTextbox.setPrefHeight(height/9);
		nameTextbox.setPrefWidth(width/1.1);
		nameTextbox.setFont(new Font(height/9));
		submitNameButton.setPrefHeight(height/9);
		submitNameButton.setPrefWidth(width/3);
		GridPane.setHalignment(nameLabel, HPos.CENTER);
		GridPane.setHalignment(nameTextbox, HPos.CENTER);
		GridPane.setHalignment(submitNameButton, HPos.CENTER);
		
		
		submitNameButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent arg0) {
				controller.submitName();
			}
		});
	}
	
	public Scene getScene() {
		return main;
	}
}
