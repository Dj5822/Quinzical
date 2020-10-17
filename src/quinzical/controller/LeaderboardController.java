package quinzical.controller;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import javafx.scene.control.ListView;
import javafx.scene.control.Alert.AlertType;

public class LeaderboardController {
	
	SceneController sceneController;
	
	public LeaderboardController(SceneController sceneController) {
		this.sceneController = sceneController;
	}
	
	public void setupLeaderboard() {
		
	}
	
	public void updateLeaderboard() {
		try {
			ProcessBuilder builder = new ProcessBuilder("bash", "-c", "cat gamedata/leaderboard");
			
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
					System.out.println(line);
				}
			} 
			else {
				Alert errorAlert = new Alert(AlertType.ERROR);
				errorAlert.setTitle("Error encountered");
				errorAlert.setHeaderText("Failed to get leaderboard data");
				errorAlert.setContentText(errorReader.readLine());
				
				errorAlert.showAndWait();
			}
			
			process.destroy();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void returnToMenu() {
		sceneController.changeScene("menu");
	}
}
