package quinzical.controller;

import javafx.collections.ObservableList;
import javafx.scene.control.ListView;

public class LeaderboardController {
	
	SceneController sceneController;
	
	private ObservableList<String> leaderboardList;
	
	public LeaderboardController(SceneController sceneController) {
		this.sceneController = sceneController;
	}
	
	public void setupLeaderboard(ObservableList<String> leaderboardList) {
		this.leaderboardList = leaderboardList;
	}
	
	public void updateLeaderboard() {
		leaderboardList.add("test");
	}
	
	public void returnToMenu() {
		sceneController.changeScene("menu");
	}
}
