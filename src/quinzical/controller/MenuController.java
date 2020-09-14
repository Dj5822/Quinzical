package quinzical.controller;

public class MenuController {
	
	private SceneController sceneController;
	
	public MenuController(SceneController sceneController) {
		this.sceneController = sceneController;
	}
	
	public void showPracticeView() {
		sceneController.changeScene("practice");
	}
	
	public void showGameView() {
		sceneController.changeScene("game");
	}
	
	public void quitGame() {
		sceneController.quitGame();
	}
}
