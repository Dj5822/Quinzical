package quinzical.controller;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;

public class DatabaseController {
	private SceneController sceneController;
	private Scene main;
	private GridPane mainPane;
	private GridPane modifyPane;
	
	private ComboBox<String> categoryCB;
	private ListView listView;
	
	private Label typeInput;
	private TextField clueInput;
	private TextField answerFrontInput;
	private TextField answerBackInput;
	
	public DatabaseController(SceneController sceneController) {
		this.sceneController = sceneController;
	}
	public void setup(Scene main, GridPane mainPane, GridPane modifyPane, 
			ComboBox<String> categoryCB, ListView listView, Label typeInput, 
			TextField clueInput, TextField answerFrontInput, TextField answerBackInput) {
		this.main = main;
		this.mainPane = mainPane;
		this.modifyPane = modifyPane;
		this.categoryCB = categoryCB;
		this.listView = listView;
		this.typeInput = typeInput;
		this.clueInput = clueInput;
		this.answerFrontInput = answerFrontInput;
		this.answerBackInput = answerBackInput;
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
	public void updateListView(String category) {
		try {
			String cmd = "cat categories/\""+category+"\".txt";
			ProcessBuilder builder = new ProcessBuilder("bash", "-c", cmd);
			
			Process process = builder.start();
			InputStream inputStream = process.getInputStream();
			InputStream errorStream = process.getErrorStream();
			BufferedReader inputReader = new BufferedReader(new InputStreamReader(inputStream));
			BufferedReader errorReader = new BufferedReader(new InputStreamReader(errorStream));
			int exitStatus = process.waitFor();
			
			// If there is no error in the command, then output the result.
			if (exitStatus == 0) {
				String line;
				
				listView.getItems().clear();
				while ((line = inputReader.readLine()) != null) {
					listView.getItems().add(line);
				}
			} 
			else {
				Alert errorAlert = new Alert(AlertType.ERROR);
				errorAlert.setTitle("Error encountered");
				errorAlert.setHeaderText("Failed to get categories data");
				errorAlert.setContentText(errorReader.readLine());
				
				errorAlert.showAndWait();
			}			
			process.destroy();		
		} catch (Exception e) {
			e.printStackTrace();
		}	
	}	
	public void returnToLastScene() {
		sceneController.returnToPreviousScene();
	}
	public void addQuestion() {
		typeInput.setText("Add");
		main.setRoot(modifyPane);	
	}
	public void modifyQuestion() {
		typeInput.setText("Modify");
		String question = (String)listView.getSelectionModel().getSelectedItem();
		String[]output = question.split("[\\(\\)]", 3);
		clueInput.setText(output[0].replace(",", "").strip());
		answerFrontInput.setText(output[1].strip());
		answerBackInput.setText(output[2].strip());
		main.setRoot(modifyPane);
	}
	public void deleteQuestion() {
		int index = listView.getSelectionModel().getSelectedIndex();
		String lineNumber = Integer.toString(index+1);	
		try {
			String category = categoryCB.getValue().toString();
			String cmd = "sed -i \'"+lineNumber+"d\' categories/\""+category+"\".txt";
			ProcessBuilder builder = new ProcessBuilder("bash", "-c", cmd);
			Process process = builder.start();
			InputStream errorStream = process.getErrorStream();
			BufferedReader errorReader = new BufferedReader(new InputStreamReader(errorStream));
			int exitStatus = process.waitFor();
			
			// If there is no error in the command, then output the result.
			if (exitStatus != 0) {
				Alert errorAlert = new Alert(AlertType.ERROR);
				errorAlert.setTitle("Error encountered");
				errorAlert.setHeaderText("Failed to modify question data");
				errorAlert.setContentText(errorReader.readLine());
				errorAlert.showAndWait();
			}
			process.destroy();		
			updateListView(category);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public void confirmModification() {
		try {
			String clue = clueInput.getText();
			String ansFront = answerFrontInput.getText();
			String ansBack = answerBackInput.getText();
			String category = categoryCB.getValue().toString();
			String cmd = "echo \'"+clue+", ( "+ansFront+") "+ansBack+"\' >> categories/\""+category+"\".txt";
			ProcessBuilder builder = new ProcessBuilder("bash", "-c", cmd);
			Process process = builder.start();
			InputStream errorStream = process.getErrorStream();
			BufferedReader errorReader = new BufferedReader(new InputStreamReader(errorStream));
			int exitStatus = process.waitFor();
			
			// If there is no error in the command, then output the result.
			if (exitStatus != 0) {
				Alert errorAlert = new Alert(AlertType.ERROR);
				errorAlert.setTitle("Error encountered");
				errorAlert.setHeaderText("Failed to modify question data");
				errorAlert.setContentText(errorReader.readLine());
				errorAlert.showAndWait();
			}
			process.destroy();
			if(typeInput.getText().equals("Modify")) {
				deleteQuestion();
			}
			typeInput.setText("");
			clueInput.setText("");
			answerFrontInput.setText("");
			answerBackInput.setText("");
			updateListView(category);
			main.setRoot(mainPane);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public void cancelModification() {
		main.setRoot(mainPane);
		
	}
}
