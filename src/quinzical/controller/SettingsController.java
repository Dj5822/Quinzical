package quinzical.controller;

public class SettingsController {
	
	private SceneController sceneController;
	private String testText = "What is the meaning of life?";
	private int speed;
	
	public SettingsController(SceneController sceneController) {
		this.sceneController = sceneController;
	}
	
	public void testSpeech() {
		AudioTask task1 = new AudioTask(testText);
		Thread thread1 = new Thread(task1);
		thread1.start();
	}
	
	public String getTestText() {
		return testText;
	}
	
	/**
	 * Used to go back to the previous scene.
	 */
	public void returnToLastScene() {
		sceneController.returnToPreviousScene();
	}
}
