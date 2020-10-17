package quinzical.controller;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

import javafx.scene.control.Alert;
import javafx.scene.control.TableView;
import javafx.scene.control.Alert.AlertType;

public class LeaderboardController {
	
	SceneController sceneController;
	TableView<LeaderboardItem> leaderboard;
	
	
	public LeaderboardController(SceneController sceneController) {
		this.sceneController = sceneController;
	}
	
	public void setupLeaderboard(TableView<LeaderboardItem> leaderboard) {
		this.leaderboard = leaderboard;
	}
	
	public void initialiseLeaderboard() {
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
				
				leaderboard.getItems().clear();
				while ((line = inputReader.readLine()) != null) {
					String[] lineArr = line.split(" ");
					leaderboard.getItems().add(new LeaderboardItem(lineArr[0], Integer.parseInt(lineArr[1])));
					leaderboard.sort();
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
	
	public void addToLeaderboard(String name, int score) {	
		try {
			ProcessBuilder builder = new ProcessBuilder("bash", "-c", "echo \"" + name + " " + score + "\" >> ./gamedata/leaderboard");
			leaderboard.getItems().add(new LeaderboardItem(name, score));
			leaderboard.sort();
			Process process = builder.start();
			InputStream errorStream = process.getErrorStream();
			BufferedReader errorReader = new BufferedReader(new InputStreamReader(errorStream));
			int exitStatus = process.waitFor();
			
			// If there is no error in the command, then output the result.
			if (exitStatus != 0) {
				Alert errorAlert = new Alert(AlertType.ERROR);
				errorAlert.setTitle("Error encountered");
				errorAlert.setHeaderText("Failed to add leaderboard data");
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
