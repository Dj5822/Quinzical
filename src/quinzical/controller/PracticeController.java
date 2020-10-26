package quinzical.controller;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import quinzical.model.Question;
import quinzical.task.VoiceTask;

public class PracticeController {
	
	private SceneController sceneController;
	private SettingsController settingsController;
	
	private Question question;
	private int answerCount;
	
	// GUI components
	private BorderPane backgroundPane;
	private GridPane answerPane;
	private GridPane categoryPane;
	private ComboBox<String> categoryCB;
	private Text answerText;
	private TextArea answerTextBox;
	private Label hintLabel;
	private Button checkAnswerButton;
	
	public PracticeController(SceneController sceneController, SettingsController settingsController) {
		this.sceneController = sceneController;
		this.settingsController = settingsController;
	}
	
	/**
	 * Links the view components with the controller so that the controller
	 * can manipulate these components.
	 */
	public void setup(BorderPane backgroundPane,
	GridPane answerPane,
	GridPane categoryPane,
	ComboBox<String> categoryCB,
	Text answerText,
	TextArea answerTextBox,
	Label hintLabel,
	Button checkAnswerButton) {
		this.backgroundPane = backgroundPane;
		this.answerPane = answerPane;
		this.categoryPane = categoryPane;
		this.categoryCB = categoryCB;
		this.answerText = answerText;
		this.answerTextBox = answerTextBox;
		this.hintLabel = hintLabel;
		this.checkAnswerButton = checkAnswerButton;
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
	 * Used to show question in the practice view.
	 * Only works if a category is selected.
	 * If category is not selected, error message will appear.
	 */
	public void showQuestion() {
		if (categoryCB.getValue() == null) {
			showErrorMessage("You must select a category", "Please select a category from the combobox.");
		}
		else {
			answerText.setText(getQuestion(categoryCB.getValue().toString()));
			answerTextBox.setVisible(true);
			answerTextBox.clear();
			hintLabel.setText("");
			backgroundPane.setCenter(answerPane);
			checkAnswerButton.setText("Check Answer");
		}
	}
	
	/**
	 * Used to check if the answer is correct.
	 */
	public void submitAnswer() {
		if (checkAnswerButton.getText() == "Check Answer") {
			hintLabel.setText(checkAnswer(answerTextBox.getText()));
			answerTextBox.clear();
			
			if (getAnswerCount() >= 3 | hintLabel.getText() == "correct") {
				answerTextBox.setVisible(false);
				categoryCB.getSelectionModel().clearSelection();
				checkAnswerButton.setText("Return");
			}
		}
		else {
			backgroundPane.setCenter(categoryPane);
		}
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
			ProcessBuilder builder = new ProcessBuilder("bash", "-c", "./scripts/getRandomQuestion.sh nz/\"" + category + "\"");
			
			Process process = builder.start();
			InputStream inputStream = process.getInputStream();
			InputStream errorStream = process.getErrorStream();
			BufferedReader inputReader = new BufferedReader(new InputStreamReader(inputStream));
			BufferedReader errorReader = new BufferedReader(new InputStreamReader(errorStream));
			int exitStatus = process.waitFor();
			
			if (exitStatus == 0) {				
				String[] output = inputReader.readLine().split("[\\(\\)]", 3);
				question = new Question(output[0], output[1], output[2]);
				answerCount = 0;
				
				// read out the question.
				VoiceTask task1 = new VoiceTask(question.getClue(), settingsController.getSpeed(), settingsController.getVoiceType());
				Thread thread1 = new Thread(task1);
				thread1.start();
				return question.getClue();
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
		String hint = "" + question.getFirstLetterOfAnswerBack();
		
		if (answerCount < 0) {
			showErrorMessage("Invalid answer count", "Answer count can not be less than 0.");
		}
		
		answerCount ++;
		
		// For debugging purposes.
		/*
		System.out.println("Input: " + input.toLowerCase().strip());
		System.out.println("Answer p1: " + answer[0].toLowerCase().strip());
		System.out.println("Answer p2: " + answer[1].toLowerCase().strip());
		*/
		
		// check if answer is correct.
		if (question.checkAnswerIsCorrect(input)) {
			output = "correct";
		}
		else {
			if (answerCount >= 3) {
				// show clue and answer if the user has no attempts remaining.
				output = "wrong, correct answer was: " + question.getAnswerBack() + "\nHint: Answer starts with " + hint;
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
		VoiceTask task1 = new VoiceTask(output, settingsController.getSpeed(), settingsController.getVoiceType());
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
