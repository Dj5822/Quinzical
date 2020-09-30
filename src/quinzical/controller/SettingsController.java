package quinzical.controller;

public class SettingsController {
	
	private SceneController sceneController;
	private String testText = "What is the meaning of life?";
	private double speed;
	
	public SettingsController(SceneController sceneController) {
		this.sceneController = sceneController;
		this.speed = 1;
	}
	
	public void testSpeech() {
		VoiceTask task1 = new VoiceTask(testText, getSpeed());
		Thread thread1 = new Thread(task1);
		thread1.start();
	}
	
	public String getTestText() {
		return testText;
	}
	
	public void setSpeed(double newSpeed) {
		this.speed = newSpeed;
	}
	
	public double getSpeed() {
		return speed;
	}
	
	/**
	 * Used to go back to the previous scene.
	 */
	public void returnToLastScene() {
		sceneController.returnToPreviousScene();
	}
}
