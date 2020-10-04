package quinzical.controller;

public class SettingsController {
	
	private SceneController sceneController;
	private String testText = "What is the meaning of life?";
	private double speed;
	private String voiceType;
	
	public SettingsController(SceneController sceneController) {
		this.sceneController = sceneController;
		this.speed = 1;
		this.voiceType = "default";
	}
	
	public void testSpeech() {
		VoiceTask task1 = new VoiceTask(testText, getSpeed(), getVoiceType());
		Thread thread1 = new Thread(task1);
		thread1.start();
	}
	
	public String getTestText() {
		return testText;
	}
	
	public void setVoiceType(String voiceType) {
		this.voiceType = voiceType;
	}
	
	public String getVoiceType() {
		return voiceType;
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
