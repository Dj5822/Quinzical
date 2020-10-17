package quinzical.controller;

public class HelpController {
	
	private SceneController sceneController;
	
	public HelpController(SceneController sceneController){
		this.sceneController = sceneController;
	}
	
	public void returnToMenu() {
		sceneController.changeScene("menu");
	}
}
