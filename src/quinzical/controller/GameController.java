package quinzical.controller;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Optional;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.util.Duration;
import quinzical.model.Category;
import quinzical.model.Question;
import quinzical.task.VoiceTask;

/**
 * This class is used to manage the game data of game mode.
 * Contains methods to update,get and set data of the game.
 */
public class GameController {
	
	// Controllers.
	private SceneController sceneController;
	private SettingsController settingsController;
	private LeaderboardController leaderboardController;
	
	// Used to store currently selected categories and clues.
	private Category[] categories;
	private Question currentQuestion;
	private int colnum;
	private int rownum;
	private int[] enabledButtons;
	
	// Used to store current winnings.
	private int currentWinnings;
	
	// Used to keep track of how many categories were completed.
	private int completedCategories = 0;
	private int correctcount = 0;
	
	// GUI components
	private Label winningLabel;
	private Label[] categoryLabels;
	private Button[][] clueButtons;
	private VBox reward;
	private Label rewardUsername;
	private Label rewardWinning;
	private Label rewardCorrectNumber;
	private Label hintLabel;
	private TextField inputField;
	private Button submitButton;
	private Button dontKnowButton;
	private GridPane gameGrid;
	private Label timerLabel;
	
	// Used for text to speech.
	private VoiceTask currentVoiceTask;
	
	// Scenes and panes.
	private Scene gameScene;
	private GridPane gamePane;
	private GridPane selectionPane;
	private GridPane namePane;
	private GridPane categoryPane;
	
	// Used in the name screen.
	private TextField nameTextbox;
	
	//category selection screen
	private ComboBox<String>[] categoryCBs;
	
	// Information required for gamemode.
	private String username;
	private String gameMode;
	private boolean internationalUnlocked = false;
	
	// The amount of time given to answer each question.
	private final int INITIALTIME = 20;
	
	// Fields relating to timer.
	private Timeline gameTimer;
	private int timeLeft = INITIALTIME;
	private boolean timerStart = false;
	
	public GameController(SceneController sceneController, SettingsController settingsController, LeaderboardController leaderboardController) {
		this.sceneController = sceneController;
		this.settingsController = settingsController;
		this.leaderboardController = leaderboardController;
		setupGameTimer();
	}
	
	/**
	 * Used to link game view components to the controller.
	 * @param categoryPane 
	 */
	public void setup(
			Scene gameScene,
			GridPane gamePane,
			GridPane selectionPane,
			GridPane namePane,
			GridPane categoryPane, 
			TextField nameTextbox,
			ComboBox<String>[] categoryCBs,
			Label winningLabel,
			Label timerLabel,
			Label[] categoryLabels,
			Button[][] clueButtons,
			VBox reward,
			Label rewardUsername, Label rewardWinning, Label rewardCorrectNumber,
			Label hintLabel, TextField inputField,
			Button submitButton,
			Button dontKnowButton,
			GridPane gameGrid) {
		this.gameScene = gameScene;
		this.gamePane = gamePane;
		this.selectionPane = selectionPane;
		this.namePane = namePane;
		this.categoryPane = categoryPane;
		this.nameTextbox = nameTextbox;
		this.categoryCBs = categoryCBs;
		this.winningLabel = winningLabel;
		this.timerLabel = timerLabel;
		this.categoryLabels = categoryLabels;
		this.clueButtons = clueButtons;
		this.reward = reward;
		this.rewardUsername = rewardUsername;
		this.rewardWinning = rewardWinning;
		this.rewardCorrectNumber = rewardCorrectNumber;
		this.hintLabel = hintLabel;
		this.inputField = inputField;
		this.submitButton = submitButton;
		this.dontKnowButton = dontKnowButton;
		this.gameGrid = gameGrid;
	}
	
	/**
	 * When nz mode is selected, player is given nz categories.
	 */
	public void nzButtonPressed() {
		gameMode = "nz";
		gameScene.setRoot(namePane);
		
		if (checkIfInternationalUnlocked()) {
			internationalUnlocked = true;
		}
		else {
			internationalUnlocked = false;
		}
	}
	
	/**
	 * Check if international is unlocked, if it is unlocked, then
	 * set the game mode to international.
	 */
	public void internationalButtonPressed() {		
		if (checkIfInternationalUnlocked()) {
			gameMode = "international";
			gameScene.setRoot(namePane);
		}
		else {
			showErrorMessage("International mode has not been unlocked", "You must complete at least two categories on New Zealand mode.");
		}
	}
	
	/**
	 * Used to check whether international mode is unlocked.
	 * Returns true if international mode is unlocked.
	 * Returns false if international mode is locked.
	 */
	public boolean checkIfInternationalUnlocked() {
		String output = "false";
		
		try {
			ProcessBuilder builder = new ProcessBuilder("bash", "-c", "cat gamedata/internationalUnlocked");			
			Process process = builder.start();
			InputStream inputStream = process.getInputStream();
			InputStream errorStream = process.getErrorStream();
			BufferedReader inputReader = new BufferedReader(new InputStreamReader(inputStream));
			BufferedReader errorReader = new BufferedReader(new InputStreamReader(errorStream));
			int exitStatus = process.waitFor();
			
			if (exitStatus == 0) {
				output = inputReader.readLine();
			} 
			else {
				showErrorMessage("Failed to check internationalUnlocked file", errorReader.readLine());
			}
			process.destroy();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		if (output.strip().equals("true")) {
			internationalUnlocked = true;
			return true;
		}
		else {
			internationalUnlocked = false;
			return false;
		}
	}
	
	/**
	 * Used to store the player's name.
	 * Name is used to record the player's score in the leaderboard.
	 */
	public void submitName() {
		username = nameTextbox.getText().replace(" ", "");
		nameTextbox.setText("");
		gameScene.setRoot(categoryPane);
		
	}
	/**
	 * used to update categoriy combobox components in category
	 * selection pane
	 * @param categoryOptions
	 */
	public void updateCategoryCBs(ObservableList<String> categoryOptions) {
		categoryOptions.clear();					
		try {
			ProcessBuilder builder = new ProcessBuilder();
			if(gameMode.equals("nz")) {
				builder.command("bash", "-c", "./scripts/getCategories.sh");
			}else {
				builder.command("bash", "-c", "./scripts/getInternationalCategories.sh");
			}			
			Process process = builder.start();
			InputStream inputStream = process.getInputStream();
			InputStream errorStream = process.getErrorStream();
			BufferedReader inputReader = new BufferedReader(new InputStreamReader(inputStream));
			BufferedReader errorReader = new BufferedReader(new InputStreamReader(errorStream));
			int exitStatus = process.waitFor();
				
			// If there is no error in the command, then output the result.
			if (exitStatus == 0) {
				String line;
					
				while ((line = inputReader.readLine()) != null) {
				    	categoryOptions.add(line);
				}
			} 
			else {
				showErrorMessage("Failed to get categories", errorReader.readLine());
			}				
			process.destroy();	
			
			String[] randomCategories = new String[5];
			int index = 0;
			builder.command("bash", "-c", "ls categories/" + gameMode + " | shuf -n 5");
			Process randomCategoryProcess = builder.start();
			InputStream randomCategoryinputStream = randomCategoryProcess.getInputStream();
			InputStream randomCategoryerrorStream = randomCategoryProcess.getErrorStream();
			BufferedReader randomCategoryinputReader = new BufferedReader(new InputStreamReader(randomCategoryinputStream));
			BufferedReader randomCategoryerrorReader = new BufferedReader(new InputStreamReader(randomCategoryerrorStream));
			int randomexitStatus = process.waitFor();
				
			// If there is no error in the command, then output the result.
			if (randomexitStatus == 0) {
				String line;
					
				while ((line = randomCategoryinputReader.readLine()) != null) {
					String[] categoryName = line.split("\\.");
				    	randomCategories[index] = categoryName[0];
				    	index++;
				}
			} 
			else {
				showErrorMessage("Failed to get categories", randomCategoryerrorReader.readLine());
			}				
			randomCategoryProcess.destroy();	
			for(int i=0;i<5;i++) {
				categoryCBs[i].setValue(randomCategories[i]);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	/**
	 * used to submit which five categories the user has chosen
	 * in category selection screen and start the game directly
	 */
	public void submitCategory() {
		String[] selectedCategories = new String[5];
		boolean hasSameCategory = false;
		for(int i=0;i<5;i++) {
			if(categoryCBs[i].getValue()==null) {
				break;
			}
			selectedCategories[i] = categoryCBs[i].getValue();
			for(int j=0; j<i; j++) {
				if(selectedCategories[i].equals(selectedCategories[j])) {
					hasSameCategory = true;
				}
			}
		}
		if(selectedCategories[0]==null ||selectedCategories[1]==null
				||selectedCategories[2]==null || selectedCategories[3]==null
				||selectedCategories[4]==null) {
			showErrorMessage("Failed to start game", "You must select 5 categories!");
		}else {
			if(hasSameCategory) {
				showErrorMessage("Failed to start game", "You must select 5 different categories!"
						+ "\nPlease check whether there are same categories.");
			}else {
				resetTime();
				generateData(selectedCategories[0],selectedCategories[1],selectedCategories[2],selectedCategories[3],selectedCategories[4]);
				generateView();
				for(int i=0;i<5;i++) {
					categoryLabels[i].setText(categories[i].getName());
					clueButtons[i][0].setDisable(false);
				}
				reward.setVisible(false);
				gameGrid.setVisible(true);
				hintLabel.setVisible(true);
		
				timerLabel.setVisible(false);
				inputField.setVisible(false);
				submitButton.setVisible(false);
				dontKnowButton.setVisible(false);
		
				gameScene.setRoot(gamePane);
			}
		}
		
	}
		
	/**
	 * This method is used to regenerate all the components and send notice
	 * for the game controller to to initialize the game data.
	 */
	public void restartButtonPressed() {		
		ButtonType yes = new ButtonType("Yes", ButtonData.YES);
		ButtonType no = new ButtonType("No", ButtonData.CANCEL_CLOSE);
		Alert confirmationAlert = new Alert(AlertType.CONFIRMATION, "WARNING: All your progress will be lost.", yes, no);
		confirmationAlert.setTitle("WARNING");
		confirmationAlert.setHeaderText("Are you sure you want to restart the game mode?");
		confirmationAlert.setContentText("If you restart, your progress will be deleted.");
		Optional<ButtonType> result = confirmationAlert.showAndWait();
		
		if (result.orElse(no) == yes) {
			gameScene.setRoot(selectionPane);
		}
	}
	
	/**
	 * This method deals with the event when one of the clue
	 * buttons are clicked. Storing the data of that question
	 * into states.
	 */
	public void clueButtonPressed(ActionEvent arg0) {
	
		hintLabel.setText("To listen to the clue again, click the clue button.");
		
		// used to get the position of the clicked clue.
		Button x =(Button) arg0.getSource();
		colnum = (Integer.parseInt(x.getId())/10);
		rownum = (Integer.parseInt(x.getId())%10);
		
		// set current question.
		currentQuestion = categories[colnum].getQuestion(rownum);
		
		playVoice(currentQuestion.getClue());
		
		// set component visibility.
		for(int i=0;i<5;i++) {
			if(i != colnum) {
				clueButtons[i][enabledButtons[i]].setDisable(true);
			}
		}
		
		if (!timerStart) {
			gameTimer.play();
			timerStart = true;
		}
		timerLabel.setVisible(true);
		inputField.setVisible(true);
		submitButton.setVisible(true);
		dontKnowButton.setVisible(true);
	}
	
	/**
	 * This method is used to send request to the game controller and
	 * check if the user's answer is correct
	 */
	public void submitButtonPressed() {	
		String text;
		
		resetTime();
		
		if(checkAnswer(inputField.getText())) {
			text = "Correct!";
			currentWinnings += Integer.parseInt(clueButtons[colnum][rownum].getText());
			correctcount++;
		}else {
			text = "Wrong. The correct answer was: "+ currentQuestion.getAnswerBack();
		}
		
		hintLabel.setText(text);
		
		timerLabel.setVisible(false);
		inputField.setVisible(false);
		submitButton.setVisible(false);
		dontKnowButton.setVisible(false);
		
		playVoice(text);
		
		winningLabel.setText("Current Worth: $" + Integer.toString(currentWinnings));
		
		updateClueButtons();
		updateQuestionComponents();
	}
	
	/**
	 * This method is used to deal with data changes when
	 * user click don't know button.
	 */
	public void dontKnowButtonPressed() {		
		String text = "The correct answer was: "+ currentQuestion.getAnswerBack();
		hintLabel.setText(text);
		timerLabel.setVisible(false);
		inputField.setVisible(false);
		submitButton.setVisible(false);
		dontKnowButton.setVisible(false);
		
		resetTime();
		
		playVoice(text);
		
		updateClueButtons();		
		updateQuestionComponents();
	}
	
	/**
	 * Keeps track of which buttons should be visible and/or disabled.
	 * Also, if a category is completed, it will increment completedCategories.
	 */
	private void updateClueButtons() {
		clueButtons[colnum][rownum].setVisible(false);
		if (enabledButtons[colnum] == 4) {
			completedCategories ++;
		}
		else if (enabledButtons[colnum] < 4) {
			enabledButtons[colnum]++;
		}
		for(int i=0;i<5;i++) {
			clueButtons[i][enabledButtons[i]].setDisable(false);
		}
	}
	
	/**
	 * Used to go back to the menu scene.
	 * Resets the game view.
	 */
	public void returnToMenu() {
		ButtonType yes = new ButtonType("Yes", ButtonData.YES);
		ButtonType no = new ButtonType("No", ButtonData.CANCEL_CLOSE);
		Alert confirmationAlert = new Alert(AlertType.CONFIRMATION, "WARNING: All your progress will be lost.", yes, no);
		confirmationAlert.setTitle("WARNING");
		confirmationAlert.setHeaderText("All game progress will be reset");
		Optional<ButtonType> result = confirmationAlert.showAndWait();
		
		if (result.orElse(no) == yes) {
			gameScene.setRoot(selectionPane);
			generateView();
			resetTime();
			sceneController.changeScene("menu");
		}
	}
	
	public void goToSettings() {
		sceneController.changeScene("settings");
	}
	
	public void goToLeaderBoard() {
		gameScene.setRoot(selectionPane);
		sceneController.changeScene("leaderboard");		
	}
	
	/**
	 * This method is used to update the question showing label and 
	 * visibility of submit and don't know buttons
	 */
	private void updateQuestionComponents() {
		submitButton.setVisible(false);
		inputField.setVisible(false);
		dontKnowButton.setVisible(false);
		inputField.setText("");
		
		if (completedCategories==2 && internationalUnlocked == false) {
			unlockInternational();
		}
		
		if(completedCategories==5) {
			gameComplete();
		}
	}
	
	/**
	 * Activated at the end of a game.
	 */
	private void gameComplete() {
		rewardUsername.setText(username);
		rewardWinning.setText("$ "+currentWinnings);
		rewardCorrectNumber.setText(correctcount+" / 25");
		winningLabel.setVisible(false);
		timerLabel.setVisible(false);
		gameGrid.setVisible(false);
		hintLabel.setVisible(false);
		reward.setVisible(true);
		leaderboardController.addToLeaderboard(username, currentWinnings);
	}
	
	/**
	 * Used to unlock international mode.
	 */
	private void unlockInternational() {
		internationalUnlocked = true;
		try {
			ProcessBuilder builder = new ProcessBuilder("bash", "-c", "echo true > gamedata/internationalUnlocked");			
			Process process = builder.start();
			InputStream errorStream = process.getErrorStream();
			BufferedReader errorReader = new BufferedReader(new InputStreamReader(errorStream));
			int exitStatus = process.waitFor();
			
			if (exitStatus == 0) {
				Alert unlockAlert = new Alert(AlertType.INFORMATION);
				unlockAlert.setTitle("Congratulations");
				unlockAlert.setHeaderText("International mode has been unlocked");
				unlockAlert.setContentText("You can now play international mode.");
				
				unlockAlert.showAndWait();
			}
			else {
				showErrorMessage("Failed to unlock international mode", errorReader.readLine());
			} 
			process.destroy();			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Resets all GUI components in the game view.
	 */
	private void generateView() {
		winningLabel.setText("Current Worth: $0");
		hintLabel.setText("Click one of the available buttons above to hear a clue~");
		for(int i=0;i<5;i++) {
			categoryLabels[i].setText("");
			for(int j=0;j<5;j++) {
				clueButtons[i][j].setVisible(true);
				clueButtons[i][j].setDisable(true);
			}
		}
		reward.setVisible(false);
		timerLabel.setVisible(false);
		gameGrid.setVisible(true);
		hintLabel.setVisible(false);
		inputField.setVisible(false);
		submitButton.setVisible(false);
		dontKnowButton.setVisible(false);
	}
	
	/**
	 * This method is used to generate game data
	 * and get questions from text files.
	 * @param category5 
	 * @param scategory4 
	 * @param category3 
	 * @param category2 
	 * @param category1 
	 */
	private void generateData(String category0, String category1, String category2, String category3, String category4) {
		categories = new Category[5];
		enabledButtons = new int[5];
		for (int i=0; i<5; i++) {
			enabledButtons[i] = 0;
		}
		
		completedCategories = 0;
		currentWinnings = 0;
		
		categories[0]= new Category(category0);
		categories[1]= new Category(category1);
		categories[2]= new Category(category2);
		categories[3]= new Category(category3);
		categories[4]= new Category(category4);
			
		//for each categories select 5 random questions
		for(Category category: categories) {
			category.selectQuestions(this, gameMode);
		}
			
	}
	
	/**
	 * This method is used to check if the user's input is
	 * correct corresponding to the question. Return true if
	 * correct and false if incorrect.
	 */
	private boolean checkAnswer(String text) {
		return currentQuestion.checkAnswerIsCorrect(text);
	}
	
	/**
	 * Used to play the voice for the question.
	 */
	private void playVoice(String text) {		
		currentVoiceTask = new VoiceTask(text, settingsController.getSpeed(), settingsController.getVoiceType());
		Thread voiceThread = new Thread(currentVoiceTask);
		voiceThread.start();
	}
	
	/**
	 * Used to show error message.
	 */
	public void showErrorMessage(String headerMessage, String contentMessage) {
		Alert errorAlert = new Alert(AlertType.ERROR);
		errorAlert.setTitle("Error encountered");
		errorAlert.setHeaderText(headerMessage);
		errorAlert.setContentText(contentMessage);		
		errorAlert.showAndWait();
	}
	
	/**
	 * Used to set up the timer for the questions.
	 */
	private void setupGameTimer() {
		gameTimer = new Timeline(new KeyFrame(Duration.seconds(1), new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				timeLeft --;
				timerLabel.setText("Time Left: " + timeLeft + " sec");
				
				if (timeLeft <= 0) {
					dontKnowButtonPressed();
				}
			}
		}));
		gameTimer.setCycleCount(Timeline.INDEFINITE);
	}
	
	/**
	 * Used to reset the timer.
	 */
	private void resetTime() {
		gameTimer.stop();
		timeLeft = INITIALTIME;
		timerStart = false;
	}
}
