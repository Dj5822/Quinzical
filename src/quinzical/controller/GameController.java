package quinzical.controller;

import java.io.BufferedReader;

import java.io.InputStream;
import java.io.InputStreamReader;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

/**
 * This class is used to manage the game data of game mode of quinzical.
 * Contains methods to update,get and set data of the game.
 */
public class GameController {
	
	private SceneController sceneController;
	private SettingsController settingsController;
	
	private String[] categories;
	private Clue[][] clues;
	private int[] enablebtns;
	private int currentwinning;
	
	private String question;
	private String[] answer;
	private int[] qspos;
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
	 * This method is used to generate data for a quinzical game 
	 * and get questions from txt files.
	 */
	public void generatedata() {
		categories = new String[5];
		clues = new Clue[5][5];
		enablebtns = new int[] {0,0,0,0,0};
		currentwinning = 0;
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
	 * This methode deals with the event when one of the clue
	 * buttons are clicked. Storing the data of that question
	 * into states.
	 */
	public void cluebtnclicked(int colindex, int rowindex) {
		qspos = new int[2];
		qspos[0] = colindex;
		qspos[1] = rowindex;
		question = clues[colindex][rowindex].getquestion();
		answer = new String[2];
		answer[0] = clues[colindex][rowindex].getans_1();
		answer[1] = clues[colindex][rowindex].getans_2();		
		AudioTask task1 = new AudioTask(question, settingsController.getSpeed());
		Thread thread1 = new Thread(task1);
		thread1.start();
		System.out.println("{For test condition:"+question);
	}
	/*
	 * This method is used to check if the user's input is
	 * correct correspoding to the question. Return true if
	 * correct and false if incorrect.
	 */
	public boolean checkAnswer(String text) {
		count++;
		if(enablebtns[qspos[0]]<4) {
			enablebtns[qspos[0]]++;
		}
		for (String potentialAnswer : answer[1].split("/")) {
			String answerRegex = "(" + answer[0].toLowerCase().strip() + " )?" + potentialAnswer.replace(".", "").toLowerCase().strip();
			
			if (text.toLowerCase().strip().matches(answerRegex)) {
				currentwinning+= (qspos[1]+1)*100;
				return true;
			}
		}
		return false;
		
	}
	/*
	 * This method is used to deal with data changes when
	 * user click dont know button.
	 */
	public void dkbtnclicked() {
		count++;
		//udate positions of clickable buttons
		if(enablebtns[qspos[0]]<4) {
			enablebtns[qspos[0]]++;
		}
	}
	/*
	 * Used to go back to the menu scene.
	 */
	public void returnToMenu() {
		sceneController.changeScene("menu");
	}
	/*
	 * Used to go to setting scene.
	 */
	public void goToSettings() {
		sceneController.changeScene("settings");
	}
	public String[] getcat() {
		return categories;
	}
	public int[] getenablebtns() {
		return enablebtns;
	}
	public int[] getqspos() {
		return qspos;
	}
	public String getans() {
		return answer[1];
	}
	public int getcurrentwinning() {
		return currentwinning;
	}
	public int getcount() {
		return count;
	}
}
