package quinzical.controller;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Optional;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonBar.ButtonData;

public class MenuController {
	
	private SceneController sceneController;
	
	public void showErrorMessage(String headerMessage, String contentMessage) {
		Alert errorAlert = new Alert(AlertType.ERROR);
		errorAlert.setTitle("Error encountered");
		errorAlert.setHeaderText(headerMessage);
		errorAlert.setContentText(contentMessage);
		
		errorAlert.showAndWait();
	}
	
	public MenuController(SceneController sceneController) {
		this.sceneController = sceneController;
		generateGamedata();
	}
	
	/**
	 * Switches the scene to the game scene.
	 */
	public void showGameView() {
		sceneController.changeScene("game");
	}
	
	/**
	 * Switches the scene to the practice scene.
	 */
	public void showPracticeView() {
		sceneController.changeScene("practice");
	}
	
	
	public void showLeaderBoardView() {
		sceneController.changeScene("leaderboard");
	}
	
	/**
	 * Switches the scene to the settings scene.
	 */
	public void showSettingsView() {
		sceneController.changeScene("settings");
	}	
	
	/**
	 * Switches the scene to database scene.
	 */
	public void showDatabaseView() {
		sceneController.changeScene("database");
	}
	
	public void resetGame() {
		ButtonType yes = new ButtonType("Yes", ButtonData.YES);
		ButtonType no = new ButtonType("No", ButtonData.CANCEL_CLOSE);
		Alert confirmationAlert = new Alert(AlertType.CONFIRMATION, "WARNING: All your progress will be lost.", yes, no);
		confirmationAlert.setTitle("WARNING");
		confirmationAlert.setHeaderText("Are you sure you want to delete all progress?");
		confirmationAlert.setContentText("Leadboard will be wiped and International mode will be locked.");
		Optional<ButtonType> result = confirmationAlert.showAndWait();
		
		if (result.orElse(no) == yes) {
			try {
				ProcessBuilder builder = new ProcessBuilder("bash", "-c", "rm -r gamedata");			
				Process process = builder.start();
				InputStream errorStream = process.getErrorStream();
				BufferedReader errorReader = new BufferedReader(new InputStreamReader(errorStream));
				int exitStatus = process.waitFor();
				
				if (exitStatus != 0) {
					showErrorMessage("Error encountered", "Failed to reset the game");
				}
				process.destroy();			
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		generateGamedata();
	}
	
	/**
	 * Ends the game.
	 */
	public void quitGame() {
		sceneController.quitGame();
	}
	
	private void generateGamedata() {
		try {
			//generate gameData
			ProcessBuilder builder = new ProcessBuilder("bash", "-c", "./scripts/generateGameData.sh");			
			Process process = builder.start();
			InputStream errorStream = process.getErrorStream();
			BufferedReader errorReader = new BufferedReader(new InputStreamReader(errorStream));
			int exitStatus = process.waitFor();
			
			if (exitStatus != 0) {
				showErrorMessage("Failed to generate gamedata", errorReader.readLine());
			}
			process.destroy();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
