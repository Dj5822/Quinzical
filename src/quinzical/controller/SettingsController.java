package quinzical.controller;

import quinzical.task.VoiceTask;

public class SettingsController {
	
	private SceneController sceneController;
	private String testText = "What is the meaning of life?";
	private double speed;
	private String voiceType;
	
	/**
	 * The settings controller basically manages the voice task.
	 * @param sceneController
	 */
	public SettingsController(SceneController sceneController) {
		this.sceneController = sceneController;
		this.speed = 1;
		this.voiceType = "default";
	}
	
	/**
	 * Reads out the test speech so that users can test the voice.
	 */
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
	
	public void returnToPreviousScreen() {
		sceneController.returnToPreviousScene();
	}
}
