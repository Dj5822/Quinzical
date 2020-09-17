package quinzical.controller;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

public class PracticeController {
	
	private SceneController sceneController;
	
	public PracticeController(SceneController sceneController) {
		this.sceneController = sceneController;
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
				Alert errorAlert = new Alert(AlertType.ERROR);
				errorAlert.setTitle("Error encountered");
				errorAlert.setHeaderText("Failed to get categories");
				String error = errorReader.readLine();
				errorAlert.setContentText(error);
				
				errorAlert.showAndWait();
			}
			
			process.destroy();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return categoryOptions;
	}
	
	/**
	 * Used to go back to the menu scene.
	 */
	public void returnToMenu() {
		sceneController.changeScene("menu");
	}
}
