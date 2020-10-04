package quinzical.controller;

import java.io.BufferedReader;

import java.io.InputStream;
import java.io.InputStreamReader;

import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

/**
 * This class is used to manage the game data of game mode of quinzical.
 * Contains methods to update,get and set data of the game.
 */
public class GameController {
	
	private SceneController sceneController;
	private SettingsController settingsController;
	
	private String[] categories;
	private Clue[][] clues;
	private int[] enableButtons;
	private int currentWinning;
	
	private String question;
	private String[] answer;
	private int[] questionPosition;
	private int count;
	
	public GameController(SceneController sceneController, SettingsController settingsController) {
		this.sceneController = sceneController;
		this.settingsController = settingsController;
	}
	
	public void showErrorMessage(String headerMessage, String contentMessage) {
		Alert errorAlert = new Alert(AlertType.ERROR);
		errorAlert.setTitle("Error encountered");
		errorAlert.setHeaderText(headerMessage);
		errorAlert.setContentText(contentMessage);
		
		errorAlert.showAndWait();
	}
	
	/*
	 * This method is used to regenerate all the components and send notice
	 * for the game controller to to initialize the game data.
	 */
	public void startButtonPressed(GameController controller, Label winningLabel, Label[] categoryLabels,
			Button[][] clueButtons, Label endingLabel, Label hintLabel, TextField inputField,
			Button submitButton, Button dontKnowButton) {
		generateData();
		
		winningLabel.setText("Current Worth: $0");
		for(int i=0;i<5;i++) {
			categoryLabels[i].setText(controller.getCategory(i));
			for(int j=0;j<5;j++) {
				clueButtons[i][j].setVisible(true);
				clueButtons[i][j].setDisable(true);
			}
			clueButtons[i][0].setDisable(false);
		}
		endingLabel.setVisible(false);
		hintLabel.setText("Click one of the available buttons above to hear a clue~");
		hintLabel.setVisible(true);
		inputField.setVisible(false);
		submitButton.setVisible(false);
		dontKnowButton.setVisible(false);
	}
	
	/*
	 * This method is used to send request to the game controller and
	 * check if the user's answer is correct
	 */
	public void submitButtonPressed(TextField inputField, Label hintLabel, Button[][] clueButtons,
			Label winningLabel, Button submitButton, Button dontKnowButton, Label endingLabel) {
		if(checkAnswer(inputField.getText())) {
			hintLabel.setText("Correct! You can now continue on the next one~");
		}else {
			hintLabel.setText("Wrong. The correct answer was: "+answer[1]
			+". Click available buttons above to continue.");
		}	
		updateClueButtons(clueButtons);
		winningLabel.setText("Current Worth: $"+Integer.toString(currentWinning));
		updateQuestionComponents(submitButton, inputField, dontKnowButton, endingLabel);
	}
	
	public void dontKnowButtonPressed(Button[][] clueButtons, Label hintLabel,
			Button submitButton, TextField inputField, Button dontKnowButton, Label endingLabel) {
		/*
		 * This method is used to deal with data changes when
		 * user click dont know button.
		 */
		count++;
		//udate positions of clickable buttons
		if(enableButtons[questionPosition[0]]<4) {
			enableButtons[questionPosition[0]]++;
		}
		updateClueButtons(clueButtons);
		hintLabel.setText("The correct answer was: "+answer[1]+". Click one of the available buttons above to hear a clue~");
		updateQuestionComponents(submitButton, inputField, dontKnowButton, endingLabel);
	}
	
	/*
	 * This method deals with the event when one of the clue
	 * buttons are clicked. Storing the data of that question
	 * into states.
	 */
	public void clueButtonPressed(ActionEvent arg0, Label hintLabel, TextField inputField, Button submitButton,
			Button dontKnowButton, Button[][] clueButtons) {
		hintLabel.setText("You can click the button if you want to listen to it again.");
		Button x =(Button)arg0.getSource();
		int colnum = (Integer.parseInt(x.getId())/10);
		int rownum = (Integer.parseInt(x.getId())%10);
		inputField.setVisible(true);
		submitButton.setVisible(true);
		dontKnowButton.setVisible(true);
		questionPosition = new int[2];
		questionPosition[0] = colnum;
		questionPosition[1] = rownum;
		question = clues[colnum][rownum].getquestion();
		answer = new String[2];
		answer[0] = clues[colnum][rownum].getans_1();
		answer[1] = clues[colnum][rownum].getans_2();		
		VoiceTask task1 = new VoiceTask(question, settingsController.getSpeed(), settingsController.getVoiceType());
		Thread thread1 = new Thread(task1);
		thread1.start();
		System.out.println("{For test condition:"+question);
		for(int i=0;i<5;i++) {
			if(i != colnum) {
				clueButtons[i][enableButtons[i]].setDisable(true);
			}
		}
	}
	
	/*
	 * This method is used to update the question showing label and 
	 * visibility of submit and dont know buttons
	 */
	public void updateQuestionComponents(Button submitButton, TextField inputField, Button dontKnowButton, Label endingLabel) {
		submitButton.setVisible(false);
		inputField.setVisible(false);
		inputField.setText("Type your answer here: ");
		dontKnowButton.setVisible(false);
		if(count== 25) {
			endingLabel.setText("Congrats! All questions completed!! You have a reward of $"
					+currentWinning+" . Click restart"
					+" button to start a new game or return to the menu.");
			endingLabel.setVisible(true);
		}
	}
	
	public void updateClueButtons(Button[][] clueButtons) {
		clueButtons[questionPosition[0]][questionPosition[1]].setVisible(false);
		for(int i=0;i<5;i++) {
			clueButtons[i][enableButtons[i]].setDisable(false);
		}
	}
	
	/*
	 * This method is used to generate data for a quinzical game 
	 * and get questions from txt files.
	 */
	public void generateData() {
		categories = new String[5];
		clues = new Clue[5][5];
		enableButtons = new int[] {0,0,0,0,0};
		currentWinning = 0;
		count=0;
		try {
			//select 5 random categories
			ProcessBuilder builder = new ProcessBuilder("bash", "-c", "ls categories | shuf -n 5");			
			Process process = builder.start();
			InputStream inputStream = process.getInputStream();
			InputStream errorStream = process.getErrorStream();
			BufferedReader inputReader = new BufferedReader(new InputStreamReader(inputStream));
			BufferedReader errorReader = new BufferedReader(new InputStreamReader(errorStream));
			int exitStatus = process.waitFor();
			
			// If there is no error in the command, then output the result.
			if (exitStatus == 0) {
				String line;
				int i = 0;
				while ((line = inputReader.readLine()) != null) {
					String[] catname = line.split("\\.",2);
			        categories[i]=catname[0];
			        i++;
				}
			} 
			else {
				showErrorMessage("Failed to get categories", errorReader.readLine());
			}
			process.destroy();
			//for each categories select 5 random questions
			for(int catno=0;catno<5;catno++) {
				ProcessBuilder qbuilder = new ProcessBuilder("bash", "-c", "./scripts/get5RandomQuestion.sh \"" + categories[catno] + "\"");
				Process qprocess = qbuilder.start();
				InputStream qinputStream = qprocess.getInputStream();
				InputStream qerrorStream = qprocess.getErrorStream();
				BufferedReader qinputReader = new BufferedReader(new InputStreamReader(qinputStream));
				BufferedReader qerrorReader = new BufferedReader(new InputStreamReader(qerrorStream));
				int qexitStatus = qprocess.waitFor();			
				if (qexitStatus == 0) {
					String line;
					int qsno=0;
					while ((line = qinputReader.readLine()) != null) {
						String[] output = line.split("[\\(\\)]", 3);
						clues[catno][qsno] = new Clue(output[0],output[1],output[2]);
						qsno++;
					}	
				} 
				else {
					showErrorMessage("Failed to get a random question", qerrorReader.readLine());
				}
				qprocess.destroy();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/*
	 * This method is used to check if the user's input is
	 * correct corresponding to the question. Return true if
	 * correct and false if incorrect.
	 */
	public boolean checkAnswer(String text) {
		count++;
		if(enableButtons[questionPosition[0]]<4) {
			enableButtons[questionPosition[0]]++;
		}
		for (String potentialAnswer : answer[1].split("/")) {
			String answerRegex = "(" + answer[0].toLowerCase().strip() + " )?" + potentialAnswer.replace(".", "").toLowerCase().strip();
			
			if (text.toLowerCase().strip().matches(answerRegex)) {
				currentWinning+= (questionPosition[1]+1)*100;
				return true;
			}
			else if (("the " + text.toLowerCase().strip()).matches(answerRegex)) {
				return true;
			}
			else if (("a " + text.toLowerCase().strip()).matches(answerRegex)) {
				return true;
			}
		}
		return false;
		
	}
	
	public String getCategory(int position) {
		return categories[position];
	}
	
	/*
	 * Used to go back to the menu scene.
	 */
	public void returnToMenu() {
		generateData();
		sceneController.changeScene("menu");
	}
	
	/*
	 * Used to go to setting scene.
	 */
	public void goToSettings() {
		sceneController.changeScene("settings");
	}
}
