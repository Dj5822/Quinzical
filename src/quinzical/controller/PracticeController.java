package quinzical.controller;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

public class PracticeController {
	
	private SceneController sceneController;
	
	private String question;
	private String[] answer;
	private int answerCount;
	
	public PracticeController(SceneController sceneController) {
		this.sceneController = sceneController;
	}
	
	public void showErrorMessage(String headerMessage, String contentMessage) {
		Alert errorAlert = new Alert(AlertType.ERROR);
		errorAlert.setTitle("Error encountered");
		errorAlert.setHeaderText(headerMessage);
		errorAlert.setContentText(contentMessage);
		
		errorAlert.showAndWait();
	}
	
	public ObservableList<String> updateCategoryOptions(ObservableList<String> categoryOptions) {
		
		categoryOptions.clear();
		
		try {
			ProcessBuilder builder = new ProcessBuilder("bash", "-c", "./scripts/getCategories.sh");
			
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
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return categoryOptions;
	}
	
	public String getQuestion(String category) {
		
		try {
			ProcessBuilder builder = new ProcessBuilder("bash", "-c", "./scripts/getRandomQuestion.sh \"" + category + "\"");
			
			Process process = builder.start();
			InputStream inputStream = process.getInputStream();
			InputStream errorStream = process.getErrorStream();
			BufferedReader inputReader = new BufferedReader(new InputStreamReader(inputStream));
			BufferedReader errorReader = new BufferedReader(new InputStreamReader(errorStream));
			int exitStatus = process.waitFor();
			
			if (exitStatus == 0) {
				String[] output = inputReader.readLine().split("[\\(\\)]", 3);
				question = output[0];
				answer = new String[2];
				answer[0] = output[1];
				answer[1] = output[2];
				answerCount = 0;
				return question;
			} 
			else {
				showErrorMessage("Failed to get a random question", errorReader.readLine());
			}
			
			process.destroy();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return "Failed to get a random question.";
	}
	
	public String checkAnswer(String input) {
		
		String hint = "" + answer[1].charAt(1);
		
		if (answerCount < 0) {
			showErrorMessage("Invalid answer count", "Answer count can not be less than 0.");
		}
		
		answerCount ++;
		
		// check if answer is correct.
		System.out.println("Input: " + input.toLowerCase().strip());
		System.out.println("Answer p1: " + answer[0].toLowerCase().strip());
		System.out.println("Answer p2: " + answer[1].toLowerCase().strip());		
		
		if (input.toLowerCase().strip().matches("(" + answer[0].toLowerCase().strip() + " )?" + answer[1].toLowerCase().strip() + "(.*)")) {
			return "correct";
		}
		
		// show clue if the user is on the third attempt.
		if (answerCount >= 3) {
			return "wrong, correct answer was: " + answer[1] + "\nHint: Answer starts with " + hint;
		}
		else if (answerCount >= 2) {
			return "wrong, you have 1 attempt remaining.\n Hint: Answer starts with: " + hint;
		}
		else {
			return "wrong, you have " + (3-answerCount) + " attempts remaining.";
		}
	}
	
	public int getAnswerCount() {
		return answerCount;
	}
	
	/**
	 * Used to go back to the menu scene.
	 */
	public void returnToMenu() {
		sceneController.changeScene("menu");
	}
}
