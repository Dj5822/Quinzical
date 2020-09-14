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
	 * Ends the game.
	 */
	public void quitGame() {
		sceneController.quitGame();
	}
}
