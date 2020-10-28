package quinzical.ui;

import java.io.File;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
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
	private Button restartButton;
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
	
	//category pane components.
	private GridPane categoryPane;
	private Label categoryLabel;
	private ObservableList<String> categoryOptions;
	private ComboBox<String>[] categoryCBs;
	private Button confirmCategoryButton;
	
	public GameView(GameController controller, int width, int height) {
		
		setupNamePane(controller, width, height);
		setupMainPane(controller, width, height);
		setupSelectionPane(controller, width, height);
		setupCategoryPane(controller, width, height);
		
		main = new Scene(selectionPane, width, height);
		
		controller.setup(main, mainPane, selectionPane, namePane, categoryPane, nameTextbox, categoryCBs, winningLabel, timerLabel, categoryLabels, clueButtons,
				reward, rewardUsername, rewardWinning, rewardCorrectNumber, hintLabel, inputField, submitButton, dontKnowButton, gameGrid);

		// Makes everything look prettier.
		File styleFile = new File("./src/quinzical/style.css");
		main.getStylesheets().clear();
		main.getStylesheets().add("file:///" + styleFile.getAbsolutePath().replace("\\", "/"));
		internationalButton.setStyle("-fx-font-size:"+(width/20));
		nzButton.setStyle("-fx-font-size:"+(width/20));
		submitNameButton.setStyle("-fx-font-size:"+(width/20));
	}

	private void setupSelectionPane(GameController controller, int width, int height) {
		// selection pane.
		selectionPane = new GridPane();
		selectionPane.setAlignment(Pos.CENTER);
		selectionPane.setVgap(height/15);
		selectionPane.setStyle("-fx-background-color: #edf4fc");
		selectionPane.setId("grassbackground");
		
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
	
	private void setupNamePane(GameController controller, int width, int height) {
		
		// setup the name pane.
		namePane = new GridPane();
		namePane.setAlignment(Pos.CENTER);
		namePane.setVgap(height/15);
		namePane.setStyle("-fx-background-color: #edf4fc");
		namePane.setId("grassbackground");
		
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
				controller.updateCategoryCBs(categoryOptions);
				controller.submitName();
			}
		});
	}
	
	private void setupCategoryPane(GameController controller, int width, int height) {
		categoryPane = new GridPane();
		categoryPane.setAlignment(Pos.CENTER);
		categoryPane.setVgap(height/15);
		categoryPane.setStyle("-fx-background-color: #edf4fc");
		categoryPane.setId("grassbackground");
		
		// initialise components.
		categoryLabel = new Label("You can choose any five categories below");
		confirmCategoryButton = new Button("Confirm selection and start");
		categoryCBs = new ComboBox[5];
		categoryOptions = FXCollections.observableArrayList();
		for(int i=0; i<5; i++) {
			categoryCBs[i] = new ComboBox<String>(categoryOptions);
			categoryPane.add(categoryCBs[i], 0, i+1);
			categoryCBs[i].setPrefHeight(height/20);
			categoryCBs[i].setPrefWidth(width/4);
			GridPane.setHalignment(categoryCBs[i], HPos.CENTER);
		}
		
		// add components to category pane.
		categoryPane.add(categoryLabel, 0, 0);
		categoryPane.add(confirmCategoryButton, 0, 6);
		
		// resize components.
		categoryLabel.setFont(new Font(height/20));
		confirmCategoryButton.setFont(new Font(height/20));
		confirmCategoryButton.setPrefHeight(height/9);
		confirmCategoryButton.setPrefWidth(width/3);
		
		GridPane.setHalignment(categoryLabel, HPos.CENTER);
		GridPane.setHalignment(confirmCategoryButton, HPos.CENTER);
				
		confirmCategoryButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent arg0) {
				controller.submitCategory();
			}
		});
	}
	
	private void setupMainPane(GameController controller, int width, int height) {
		// Setup main pane.		
		mainPane = new GridPane();
		mainPane.setAlignment(Pos.BOTTOM_CENTER);
		mainPane.setVgap(height/30);
		mainPane.setStyle("-fx-background-color: #edf4fc");
		mainPane.setId("grassbackground");
		gameGrid = new GridPane();
		gameGrid.setHgap(width/15);
		gameGrid.setVgap(height/30);
		reward = new VBox();
		reward.setSpacing(height/12);
		menuGrid = new GridPane();
		menuGrid.setStyle("-fx-background-color: rgba(255, 255, 255, 0.7)");

		// Initialize buttons and labels.
		restartButton = new Button("Restart game");
		winningLabel = new Label("Current Worth: $0");
		categoryLabels= new Label[5] ;
		clueButtons = new Button[5][5];
		timerLabel = new Label("Select a clue to start timer.");
		rewardUsername = new Label();
		rewardWinningLabel = new Label("TOTAL WINNING:");
		rewardWinning = new Label();
		rewardCorrectNumberLabel = new Label("Number of correct answers:");
		rewardCorrectNumber = new Label();
		ToLeaderBoardButton = new Button("Click to go to Leaderboard");

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
			categoryLabels[col].setFont(new Font(24));
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
		restartButton.setPrefHeight(height/9);
		submitButton.setPrefHeight(height/9);
		dontKnowButton.setPrefHeight(height/9);
		returnToMenuButton.setPrefHeight(height/9);
		settingsButton.setPrefHeight(height/9);
		ToLeaderBoardButton.setPrefHeight(height/9);

		restartButton.setPrefWidth(width/3);
		submitButton.setPrefWidth(width/3);
		dontKnowButton.setPrefWidth(width/3);
		returnToMenuButton.setPrefWidth(width/3);
		settingsButton.setPrefWidth(width/3);
		ToLeaderBoardButton.setPrefWidth(width/3);
		
		inputField.setPrefHeight(height/9);
		inputField.setPrefWidth(width/1.1);
		
		rewardUsername.setFont(new Font(height/15));
		rewardWinningLabel.setFont(new Font(height/30));
		rewardWinning.setFont(new Font(height/20));
		rewardCorrectNumberLabel.setFont(new Font(height/30));
		rewardCorrectNumber.setFont(new Font(height/20));
		
		inputField.setFont(new Font(height/30));
		winningLabel.setFont(new Font(height/30));
		hintLabel.setFont(new Font(height/30));
		timerLabel.setFont(new Font(height/30));
		ToLeaderBoardButton.setFont(new Font(height/30));

		
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
		restartButton.setFocusTraversable(false);
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

		menuGrid.add(restartButton, 0, 0);
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
		restartButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent arg0) {
				controller.restartButtonPressed();
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
	
	public Scene getScene() {
		return main;
	}
}
