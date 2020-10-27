package quinzical.controller;

import java.io.BufferedReader;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Optional;

import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;

/**
 * This class is used to manage the operation in the Question
 * database management functionality in the app. The user should
 * be able to view the category and questions in a list and add,
 * modify or delete the questions.
 * @author se2062020
 *
 */

public class DatabaseController {
	private SceneController sceneController;
	private Scene main;
	private GridPane mainPane;
	private GridPane modifyPane;
	
	private ComboBox<String> sectionCB;
	private ComboBox<String> categoryCB;
	private ListView listView;
	private Button addButton, modifyButton, deleteButton;
	
	private Label typeInput;
	private TextField clueInput;
	private TextField answerFrontInput;
	private TextField answerBackInput;
	
	public DatabaseController(SceneController sceneController) {
		this.sceneController = sceneController;
	}
	/**
	 * Link the components in database view and controller.
	 * 
	 * @param main
	 * @param mainPane
	 * @param modifyPane
	 * @param sectionCB
	 * @param categoryCB
	 * @param listView
	 * @param addButton
	 * @param modifyButton
	 * @param deleteButton
	 * @param typeInput
	 * @param clueInput
	 * @param answerFrontInput
	 * @param answerBackInput
	 */
	public void setup(Scene main, GridPane mainPane, GridPane modifyPane, ComboBox<String> sectionCB,
			ComboBox<String> categoryCB, ListView listView, Button addButton, Button modifyButton,
			Button deleteButton, Label typeInput, TextField clueInput, TextField answerFrontInput, 
			TextField answerBackInput) {
		this.main = main;
		this.mainPane = mainPane;
		this.modifyPane = modifyPane;
		this.sectionCB = sectionCB;
		this.categoryCB = categoryCB;
		this.listView = listView;
		this.addButton = addButton;
		this.modifyButton = modifyButton;
		this.deleteButton = deleteButton;
				
		this.typeInput = typeInput;
		this.clueInput = clueInput;
		this.answerFrontInput = answerFrontInput;
		this.answerBackInput = answerBackInput;
	}
	/** 
	 * This method is used to show a confirmation dialog with
	 * input header and content.
	 * 
	 * @param headerMessage
	 * @param contentMessage
	 * @return
	 */
	public Optional<ButtonType> showConfirmationDialog(String headerMessage, String contentMessage){
		Alert confirmAlert = new Alert(AlertType.CONFIRMATION);
		confirmAlert.setTitle("Confirmation Dialog");
		confirmAlert.setHeaderText(headerMessage);
		confirmAlert.setContentText(contentMessage);
		return confirmAlert.showAndWait();
	}
	/**
	 * This method is used to show a error message dialog with input
	 * header and content.
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
	 * This method is used to update the list of elements in the section
	 * combbox component
	 * @param sectionOptions
	 */
	public void updateSectionOptions(ObservableList<String> sectionOptions) {
		sectionOptions.clear();
		sectionOptions.add("nz");
		sectionOptions.add("international");	
	}
	/**
	 * THis method is used to update the list of elements in the 
	 * category combbox component
	 * @param categoryOptions
	 * @param section
	 * @return
	 */
	public ObservableList<String> updateCategoryOptions(ObservableList<String> categoryOptions, String section) {		
		categoryOptions.clear();				
		try {
			ProcessBuilder builder = new ProcessBuilder();
			if(section.equals("nz")) {
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
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return categoryOptions;
	}
	/**
	 * This mothod is used to update the list view component so that it correctly
	 * shows all the question in a category
	 */
	public void updateListView() {
		try {
			String section = sectionCB.getValue().toString();
			String category = categoryCB.getValue().toString();
			String cmd = "cat categories/"+section+"/\""+category+"\".txt";
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
	
	/**
	 * This method is used to handle add operation and
	 * switch the scene to question modify pane.
	 */
	public void addQuestion() {
		typeInput.setText("Add");
		main.setRoot(modifyPane);	
	}
	
	/**
	 * This method is used to handle modify operation and
	 * switch the scene to question modify pane.
	 */
	public void modifyQuestion() {
		typeInput.setText("Modify");
		String question = (String)listView.getSelectionModel().getSelectedItem();
		String[]output = question.split("[\\(\\)]", 3);
		clueInput.setText(output[0].replace(",", "").strip());
		answerFrontInput.setText(output[1].strip());
		answerBackInput.setText(output[2].strip());
		main.setRoot(modifyPane);
	}
	
	/**
	 * This method is used to handle delete operation and
	 * switch the scene to question modify pane.
	 */
	public void deleteQuestion() {
		int index = listView.getSelectionModel().getSelectedIndex();
		String lineNumber = Integer.toString(index+1);	
		try {
			String section = sectionCB.getValue().toString();
			String category = categoryCB.getValue().toString();
			String cmd = "sed -i \'"+lineNumber+"d\' categories/"+section+"/\""+category+"\".txt";
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
			modifyButton.setDisable(true);
			deleteButton.setDisable(true);
			updateListView();
				
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * This method is used to confirm the modification
	 * of a user operation that could be add, delete or
	 * modify.
	 */
	public void confirmModification() {
		try {
			String clue = clueInput.getText();
			String ansFront = answerFrontInput.getText();
			String ansBack = answerBackInput.getText();
			String section = sectionCB.getValue().toString();
			String category = categoryCB.getValue().toString();
			String cmd = "echo \'"+clue+", ( "+ansFront+") "+ansBack+"\' >> categories/"+section+"/\""+category+"\".txt";
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
			
			modifyButton.setDisable(true);
			deleteButton.setDisable(true);
			updateListView();
			main.setRoot(mainPane);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * This method is called when the user clicks cancel in
	 * question modify pane and go back to main pane.
	 */
	public void cancelModification() {
		typeInput.setText("");
		clueInput.setText("");
		answerFrontInput.setText("");
		answerBackInput.setText("");
		main.setRoot(mainPane);		
	}
	
	public void clearListView() {
		listView.getItems().clear();
		
	}
	/**
	 * This method is used to activate the modify and delete
	 * button when the user actually choose a question in the
	 * list.
	 */
	public void questionSelected() {
		int i = listView.getSelectionModel().getSelectedIndex();
		if(i != -1) {
			if(modifyButton.isDisabled()) {
				modifyButton.setDisable(false);
				deleteButton.setDisable(false);
			}
		}
	}
}
