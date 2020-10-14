package quinzical.controller;

import java.io.BufferedReader;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Optional;

import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;

/**
 * This class is used to manage the game data of game mode of quinzical.
 * Contains methods to update,get and set data of the game.
 */
public class GameController {
	
	private SceneController sceneController;
	private SettingsController settingsController;
	
	// Used to store currently selected categories and clues.
	private Category[] categories;
	private Question currentQuestion;
	private int colnum;
	private int rownum;
	private int[] enabledButtons;
	
	// Used to store current winnings.
	private int currentWinnings;
	
	// Used to keep track of how many questions were answered.
	private int count = 0;
	
	// GUI components
	private Label winningLabel;
	private Label[] categoryLabels;
	private Button[][] clueButtons;
	private Label endingLabel;
	private Label hintLabel;
	private TextField inputField;
	private Button submitButton;
	private Button dontKnowButton;
	private GridPane gameGrid;
	
	public GameController(SceneController sceneController, SettingsController settingsController) {
		this.sceneController = sceneController;
		this.settingsController = settingsController;
	}
	
	public void setup(
			Label winningLabel,
			Label[] categoryLabels,
			Button[][] clueButtons,
			Label endingLabel,
			Label hintLabel,
			TextField inputField,
			Button submitButton,
			Button dontKnowButton,
			GridPane gameGrid) {
		this.winningLabel = winningLabel;
		this.categoryLabels = categoryLabels;
		this.clueButtons = clueButtons;
		this.endingLabel = endingLabel;
		this.hintLabel = hintLabel;
		this.inputField = inputField;
		this.submitButton = submitButton;
		this.dontKnowButton = dontKnowButton;
		this.gameGrid = gameGrid;
	}
	
	public void showErrorMessage(String headerMessage, String contentMessage) {
		Alert errorAlert = new Alert(AlertType.ERROR);
		errorAlert.setTitle("Error encountered");
		errorAlert.setHeaderText(headerMessage);
		errorAlert.setContentText(contentMessage);
		
		errorAlert.showAndWait();
	}
	
	/**
	 * This method is used to regenerate all the components and send notice
	 * for the game controller to to initialize the game data.
	 */
	public void startButtonPressed() {		
		ButtonType yes = new ButtonType("Yes", ButtonData.YES);
		ButtonType no = new ButtonType("No", ButtonData.CANCEL_CLOSE);
		Alert confirmationAlert = new Alert(AlertType.CONFIRMATION, "WARNING: All your progress will be lost.", yes, no);
		confirmationAlert.setTitle("WARNING");
		confirmationAlert.setHeaderText("Are you sure you want to start/restart the game?");
		confirmationAlert.setContentText("If you restart, your progress will be deleted.");
		Optional<ButtonType> result = confirmationAlert.showAndWait();
		
		if (result.orElse(no) == yes) {
			generateData();
			generateView();
			for(int i=0;i<5;i++) {
				categoryLabels[i].setText(categories[i].getName());
				clueButtons[i][0].setDisable(false);
			}
			endingLabel.setVisible(false);
			gameGrid.setVisible(true);
			hintLabel.setVisible(true);
			inputField.setVisible(true);
			submitButton.setVisible(true);
			dontKnowButton.setVisible(true);
		}
	}
	
	/**
	 * This method is used to send request to the game controller and
	 * check if the user's answer is correct
	 */
	public void submitButtonPressed() {	
		count++;
		String text;
		
		if(checkAnswer(inputField.getText())) {
			text = "Correct!";
		}else {
			text = "Wrong. The correct answer was: "+ currentQuestion.getAnswerBack();
		}
		
		hintLabel.setText(text);
		VoiceTask task1 = new VoiceTask(text, settingsController.getSpeed(), settingsController.getVoiceType());
		Thread thread1 = new Thread(task1);
		thread1.start();
		
		winningLabel.setText("Current Worth: $" + Integer.toString(currentWinnings));
		
		updateClueButtons();
		updateQuestionComponents();
	}
	
	/**
	 * This method is used to deal with data changes when
	 * user click dont know button.
	 */
	public void dontKnowButtonPressed() {		
		count++;
		String text = "The correct answer was: "+ currentQuestion.getAnswerBack();
		hintLabel.setText(text);
		
		VoiceTask task1 = new VoiceTask(text, settingsController.getSpeed(), settingsController.getVoiceType());
		Thread thread1 = new Thread(task1);
		thread1.start();
		
		updateClueButtons();		
		updateQuestionComponents();
	}
	
	private void updateClueButtons() {
		clueButtons[colnum][rownum].setVisible(false);
		if (enabledButtons[colnum] < 4) {
			enabledButtons[colnum]++;
		}
		for(int i=0;i<5;i++) {
			clueButtons[i][enabledButtons[i]].setDisable(false);
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
		
		// read out the question.
		VoiceTask task1 = new VoiceTask(currentQuestion.getClue(), settingsController.getSpeed(), settingsController.getVoiceType());
		Thread thread1 = new Thread(task1);
		thread1.start();
		
		// set component visibility.
		for(int i=0;i<5;i++) {
			if(i != colnum) {
				clueButtons[i][enabledButtons[i]].setDisable(true);
			}
		}
		inputField.setVisible(true);
		submitButton.setVisible(true);
		dontKnowButton.setVisible(true);
	}
	
	/**
	 * Used to go back to the menu scene.
	 * Resets the gameview.
	 */
	public void returnToMenu() {
		ButtonType yes = new ButtonType("Yes", ButtonData.YES);
		ButtonType no = new ButtonType("No", ButtonData.CANCEL_CLOSE);
		Alert confirmationAlert = new Alert(AlertType.CONFIRMATION, "WARNING: All your progress will be lost.", yes, no);
		confirmationAlert.setTitle("WARNING");
		confirmationAlert.setHeaderText("All game progress will be reset");
		Optional<ButtonType> result = confirmationAlert.showAndWait();
		
		if (result.orElse(no) == yes) {
			generateView();
			sceneController.changeScene("menu");
		}
	}
	
	/**
	 * Used to go to setting scene.
	 */
	public void goToSettings() {
		sceneController.changeScene("settings");
	}
	
	/**
	 * This method is used to update the question showing label and 
	 * visibility of submit and dont know buttons
	 */
	public void updateQuestionComponents() {
		submitButton.setVisible(false);
		inputField.setVisible(false);
		dontKnowButton.setVisible(false);
		inputField.setText("");
		
		if(count == 25) {
			endingLabel.setText("Congrats! All questions completed!! You have a totoal reward of $"
					+currentWinnings+" . Click restart."
					+" button to start a new game or return to the menu.");
			gameGrid.setVisible(false);
			endingLabel.setVisible(true);
		}
	}
	
	/**
	 * Resets all GUI components in the game view.
	 */
	public void generateView() {
		winningLabel.setText("Current Worth: $0");
		hintLabel.setText("Click one of the available buttons above to hear a clue~");
		for(int i=0;i<5;i++) {
			categoryLabels[i].setText("");
			for(int j=0;j<5;j++) {
				clueButtons[i][j].setVisible(true);
				clueButtons[i][j].setDisable(true);
			}
		}
		endingLabel.setVisible(false);
		gameGrid.setVisible(true);
		hintLabel.setVisible(false);
		inputField.setVisible(false);
		submitButton.setVisible(false);
		dontKnowButton.setVisible(false);
	}
	
	/**
	 * This method is used to generate data for a quinzical game 
	 * and get questions from txt files.
	 */
	public void generateData() {
		categories = new Category[5];
		enabledButtons = new int[5];
		for (int i=0; i<5; i++) {
			enabledButtons[i] = 0;
		}
		
		currentWinnings = 0;
		try {
			//select 5 random categories
			ProcessBuilder builder = new ProcessBuilder("bash", "-c", "ls categories | shuf -n 5");			
			Process process = builder.start();
			InputStream inputStream = process.getInputStream();
			InputStream errorStream = process.getErrorStream();
			BufferedReader inputReader = new BufferedReader(new InputStreamReader(inputStream));
			BufferedReader errorReader = new BufferedReader(new InputStreamReader(errorStream));
			int exitStatus = process.waitFor();
			
			if (exitStatus == 0) {
				String line;
				int i = 0;
				while ((line = inputReader.readLine()) != null) {
					String[] categoryName = line.split("\\.",2);
			        categories[i] = new Category(categoryName[0]);
			        i++;
				}
			} 
			else {
				showErrorMessage("Failed to get categories", errorReader.readLine());
			}
			process.destroy();
			
			//for each categories select 5 random questions
			for(Category category: categories) {
				category.selectQuestions(this);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * This method is used to check if the user's input is
	 * correct corresponding to the question. Return true if
	 * correct and false if incorrect.
	 */
	public boolean checkAnswer(String text) {
		return currentQuestion.checkAnswerIsCorrect(text);
		
	}
}
