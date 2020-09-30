package quinzical.controller;

import java.io.File;

import javafx.scene.media.AudioClip;

public class MenuController {
	
	private SceneController sceneController;
	private AudioClip hoverSound;
	
	public MenuController(SceneController sceneController) {
		this.sceneController = sceneController;
		File soundFile = new File("./sound/sound1.wav");
    	hoverSound = new AudioClip(soundFile.toURI().toString());
	}
	
	public void playHoverSound() {
		hoverSound.play();
	}
	
	/**
	 * Switches the scene to the game scene.
	 */
	public void showGameView() {
		sceneController.changeScene("game");
	}
	
	/**
	 * Switches the scene to the practice scene.
	 */
	public void showPracticeView() {
		sceneController.changeScene("practice");
	}
	
	/**
	 * Switches the scene to the settings scene.
	 */
	public void showSettingsView() {
		sceneController.changeScene("settings");
	}	
	
	/**
	 * Ends the game.
	 */
	public void quitGame() {
		sceneController.quitGame();
	}
}
