package quinzical.controller;

public class GameController {
	
	private SceneController sceneController;
	
	public GameController(SceneController sceneController) {
		this.sceneController = sceneController;
	}
	
	public void returnToMenu() {
		sceneController.changeScene("menu");
	}
}
