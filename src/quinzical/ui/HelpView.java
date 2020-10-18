package quinzical.ui;

import java.io.File;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import quinzical.controller.HelpController;

public class HelpView {
	
	private Scene helpScene;
	
	private GridPane mainPane;
	private ScrollPane bodyPane;
	private Label title;
	private TextFlow body;
	private Button returnToMenuButton;
	
	public HelpView(HelpController controller, int width, int height){
		
		// setup main pane
		mainPane = new GridPane();
		mainPane.setAlignment(Pos.CENTER);
		mainPane.setVgap(height/15);
		mainPane.setStyle("-fx-background-color: #edf4fc");
		helpScene = new Scene(mainPane, width, height);
		File styleFile = new File("./src/quinzical/style.css");
		helpScene.getStylesheets().clear();
		helpScene.getStylesheets().add("file:///" + styleFile.getAbsolutePath().replace("\\", "/"));
		
		// setup components
		title = new Label("Help");
		returnToMenuButton = new Button("Return to Menu");
		
		// headers
		Text practiceHeader = new Text("Practice mode\n");
        Text gameHeader = new Text("\n\nGame mode\n");
        Text leaderboardHeader = new Text("\n\nLeaderboard\n");
        Text settingsHeader = new Text("\n\nSettings\n");
        Text databaseHeader = new Text("\n\nAdd Questions\n");
        Text resetHeader = new Text("\n\nReset\n");
        
        // set header style
        practiceHeader.setStyle("-fx-font-size: " + height/20 + "; -fx-fill: #6a718d;");
		gameHeader.setStyle("-fx-font-size: " + height/20 + "; -fx-fill: #6a718d;");
		leaderboardHeader.setStyle("-fx-font-size: " + height/20 + "; -fx-fill: #6a718d;");
		settingsHeader.setStyle("-fx-font-size: " + height/20 + "; -fx-fill: #6a718d;");
		databaseHeader.setStyle("-fx-font-size: " + height/20 + "; -fx-fill: #6a718d;");
		resetHeader.setStyle("-fx-font-size: " + height/20 + "; -fx-fill: #6a718d;");
        
        // NOTE: move this text to text file later.
        Text practiceBody = new Text("You can enter practice mode by pressing 'Practice' in the menu.\n"
        		+ "After that, select a category from the combobox that you want to practice.\n"
        		+ "You get three tries to get the answer right. If you get it wrong twice, you are shown a clue.\n"
        		+ "If you get it wrong three times you are shown the correct answer.\n");
        Text gameBody = new Text("You can enter the game mode by pressing the 'Play Game' in the menu.\n"
        		+ "After that, you can choose from either the New Zealand question set of the international questions set.\n"
        		+ "The international question set is only unlocked once you have completed two categories in the New Zealand question set.\n"
        		+ "After that, you must enter your name. This is the name that will appear in the leaderboard once you complete a game.\n"
        		+ "Next, you will enter a game screen that will have three buttons at the bottom. Which are: \n"
        		+ "'Start/Reset the game', 'Voice speed setting', and 'Return to menu'. \n"
        		+ "Start/Reset the game will start or reset the game by clearing the board and selecting 5 random categories.\n"
        		+ "Voice speed settings allows you to access the settings from the game mode.\n"
        		+ "Return to menu will send you back to menu, however all your gamedata from your current session will be deleted.\n"
        		+ "When you start the game, you will need to click on one of the clues valued at 100.\n"
        		+ "Once you have selected a clue, the clue will be read out.\n"
        		+ "You get 20 seconds to submit an answer.\n"
        		+ "Type in the answer in the textbox and press submit to check the answer.\n"
        		+ "If your answer is correct, then your winnings (located at the top of the screen will increase).\n"
        		+ "Otherwise, your winnings will not change.\n"
        		+ "You can also click on 'Don't Know' if you are unsure of the answer. This is the same as getting the question wrong.\n"
        		+ "Note that there is a timer for each question. If the timer runs out, you will automatically get the questions wrong.\n"
        		+ "Once you have completed all the questions, then you will be presented with a reward.\n"
        		+ "Also, your name and score will go up onto the leaderboard.\n");
        Text leaderboardBody = new Text("Players that have completed a game will go up onto the leaderboard.\n"
        		+ "The leaderboard is sorted from the players with the highest to the lowest score.\n"
        		+ "Reseting the game will clear the leaderboard.\n");
        Text settingsBody = new Text("Here you can adjust the speed at which the clues are read out.\n"
        		+ "You can also change the voice type as long as you have the respective voice type install in your system.\n"
        		+ "You can test the voice by pressing the 'Test' button.\n");
        Text databaseBody = new Text("This is where you can add, delete, and modify questions.\n");
        Text resetBody = new Text("This is where you can reset the game.\n"
        		+ "Reseting the game will clear the leaderboard and will also lock international mode\n "
        		+ "until you complete 2 categories in NZ mode again.");
        
        // set body style
        practiceBody.setStyle("-fx-font-size: " + height/40 + ";");
		gameBody.setStyle("-fx-font-size: " + height/40 + ";");
		leaderboardBody.setStyle("-fx-font-size: " + height/40 + "; ");
		settingsBody.setStyle("-fx-font-size: " + height/40 + ";");
		databaseBody.setStyle("-fx-font-size: " + height/40 + ";");
		resetBody.setStyle("-fx-font-size: " + height/40 + ";");

		// add text to the body.
		body = new TextFlow(practiceHeader, practiceBody, gameHeader, gameBody, leaderboardHeader, leaderboardBody,
				settingsHeader, settingsBody, databaseHeader, databaseBody, resetHeader, resetBody);
		bodyPane = new ScrollPane(body);
		
		// resize and align components.
		title.setFont(new Font(height/9));
		bodyPane.setPrefWidth(width/1.1);
		bodyPane.setPrefHeight(height/2);
		returnToMenuButton.setPrefHeight(height/9);
		returnToMenuButton.setPrefWidth(width/5);
		GridPane.setHalignment(title, HPos.CENTER);
		GridPane.setHalignment(returnToMenuButton, HPos.CENTER);
		returnToMenuButton.setFocusTraversable(false);
		
		// add components to the main pane.
		mainPane.add(title, 0, 0);
		mainPane.add(bodyPane, 0, 1);
		mainPane.add(returnToMenuButton, 0, 2);
		
		returnToMenuButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent arg0) {
				controller.returnToMenu();
			}
		});		
	}
	
	public Scene getScene() {
		return helpScene;
	}
}
