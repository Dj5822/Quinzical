package quinzical.controller;

/**
 * The controller for the help view.
 *
 */
public class HelpController {
	
	private SceneController sceneController;
	
	public HelpController(SceneController sceneController){
		this.sceneController = sceneController;
	}
	
	public void returnToMenu() {
		sceneController.changeScene("menu");
	}
}
