package quinzical.controller;

public class MenuController {
	
	private SceneController sceneController;
	
	public MenuController(SceneController sceneController) {
		this.sceneController = sceneController;
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
	
	/**
	 * Switches the scene to the settings scene.
	 */
	public void showSettingsView() {
		sceneController.changeScene("settings");
	}	
	
	/**
	 * Ends the game.
	 */
	public void quitGame() {
		sceneController.quitGame();
	}
}