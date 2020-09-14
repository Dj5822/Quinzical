package quinzical.controller;

public class SettingsController {
	
	private SceneController sceneController;
	
	public SettingsController(SceneController sceneController) {
		this.sceneController = sceneController;
	}
	
	/**
	 * Used to go back to the previous scene.
	 */
	public void returnToLastScene() {
		sceneController.returnToPreviousScene();
	}
}
