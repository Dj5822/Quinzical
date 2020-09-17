package quinzical.controller;

import javafx.collections.ObservableList;

public class PracticeController {
	
	private SceneController sceneController;
	
	public PracticeController(SceneController sceneController) {
		this.sceneController = sceneController;
	}
	
	public ObservableList<String> updateCategoryOptions(ObservableList<String> categoryOptions) {
		return categoryOptions;
	}
	
	/**
	 * Used to go back to the menu scene.
	 */
	public void returnToMenu() {
		sceneController.changeScene("menu");
	}
}
