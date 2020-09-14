package quinzical.controller;

public class PracticeController {
	
	private SceneController sceneController;
	
	public PracticeController(SceneController sceneController) {
		this.sceneController = sceneController;
	}
	
	public void returnToMenu() {
		sceneController.changeScene("menu");
	}
}
