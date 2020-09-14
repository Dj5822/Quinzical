package quinzical.controller;

public class GameController {
	
	private SceneController sceneController;
	
	public GameController(SceneController sceneController) {
		this.sceneController = sceneController;
	}
	
	/**
	 * Used to go back to the menu scene.
	 */
	public void returnToMenu() {
		sceneController.changeScene("menu");
	}
}
