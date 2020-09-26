package quinzical.controller;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

public class PracticeController {
	
	private SceneController sceneController;
	private SettingsController settingsController;
	
	private String question;
	private String[] answer;
	private int answerCount;
	
	public PracticeController(SceneController sceneController, SettingsController settingsController) {
		this.sceneController = sceneController;
		this.settingsController = settingsController;
	}
	
	/**
	 * Used to show error messages.
	 * @param headerMessage
	 * @param contentMessage
	 */
	public void showErrorMessage(String headerMessage, String contentMessage) {
		Alert errorAlert = new Alert(AlertType.ERROR);
		errorAlert.setTitle("Error encountered");
		errorAlert.setHeaderText(headerMessage);
		errorAlert.setContentText(contentMessage);
		
		errorAlert.showAndWait();
	}
	
	/**
	 * Used to initialise the category selection combobox.
	 * @param categoryOptions
	 * @return categoryOptions
	 */
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
	
	/**
	 * Used to get a random question.
	 * @param category
	 * @return question
	 */
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
				
				// read out the question.
				AudioTask task1 = new AudioTask(question, settingsController.getSpeed());
				Thread thread1 = new Thread(task1);
				thread1.start();
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
	
	/**
	 * Used to check whether the supplied input is a valid answer.
	 * @param input
	 * @return "correct" if correct or "wrong" if incorrect.
	 */
	public String checkAnswer(String input) {
		
		String output = "";
		String hint = "" + answer[1].charAt(1);
		
		if (answerCount < 0) {
			showErrorMessage("Invalid answer count", "Answer count can not be less than 0.");
		}
		
		answerCount ++;
		
		// check if answer is correct.
		System.out.println("Input: " + input.toLowerCase().strip());
		System.out.println("Answer p1: " + answer[0].toLowerCase().strip());
		System.out.println("Answer p2: " + answer[1].toLowerCase().strip());
		
		for (String potentialAnswer : answer[1].split("/")) {
			String answerRegex = "(" + answer[0].toLowerCase().strip() + " )?" + potentialAnswer.replace(".", "").toLowerCase().strip();
			
			if (input.toLowerCase().strip().matches(answerRegex)) {
				output = "correct";
			}
		}
		
		if (output == "") {
			if (answerCount >= 3) {
				// show clue and answer if the user has no attempts remaining.
				output = "wrong, correct answer was: " + answer[1] + "\nHint: Answer starts with " + hint;
			}
			else if (answerCount >= 2) {
				// show clue if the user is on the third attempt.
				output = "wrong, you have 1 attempt remaining.\n Hint: Answer starts with: " + hint;
			}
			else {
				output = "wrong, you have " + (3-answerCount) + " attempts remaining.";
			}
		}
		
		// read out and show the output.
		AudioTask task1 = new AudioTask(output, settingsController.getSpeed());
		Thread thread1 = new Thread(task1);
		thread1.start();
		return output;
	}
	
	/**
	 * A count of the number of times the user has attempted the current question.
	 * @return answerCount
	 */
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
