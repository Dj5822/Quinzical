package quinzical.controller;

public class PracticeController {
	
	private SceneController sceneController;
	
	public PracticeController(SceneController sceneController) {
		this.sceneController = sceneController;
	}
	
	/**
	 * Used to go back to the menu scene.
	 */
	public void returnToMenu() {
		sceneController.changeScene("menu");
	}
}
