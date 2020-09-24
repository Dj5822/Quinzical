package quinzical.controller;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

public class GameController {
	
	private SceneController sceneController;
	
	public GameController(SceneController sceneController) {
		this.sceneController = sceneController;
	}
	public void showErrorMessage(String headerMessage, String contentMessage) {
		Alert errorAlert = new Alert(AlertType.ERROR);
		errorAlert.setTitle("Error encountered");
		errorAlert.setHeaderText(headerMessage);
		errorAlert.setContentText(contentMessage);
		
		errorAlert.showAndWait();
	}
	public void generatedata(String[] categories, String[][][] questions) {
		try {
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
					int qno=0;
					while ((line = qinputReader.readLine()) != null) {
						String[] output = line.split("[\\(\\)]", 3);
						questions[catno][qno][0] = output[0];
						questions[catno][qno][1] = output[1];
						questions[catno][qno][2] = output[2];
						qno++;
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
	/**
	 * Used to go back to the menu scene.
	 */
	public void returnToMenu() {
		sceneController.changeScene("menu");
	}
}
