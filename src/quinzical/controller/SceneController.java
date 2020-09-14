package quinzical.controller;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import quinzical.ui.GameView;
import quinzical.ui.MenuView;
import quinzical.ui.PracticeView;

public class SceneController extends Application {
	
	private final int HEIGHT = 500;
	private final int WIDTH = 1000;
	
	private Stage primaryStage;
	
	private MenuView menuView;
	private PracticeView practiceView;
	private GameView gameView;
	
	// Switches scene to the menu.
	public void showMenu() {
		primaryStage.setScene(menuView.getScene());
	}
	
	// Switches scene to the practice view.
	public void showPracticeView() {
		primaryStage.setScene(practiceView.getScene());
	}
	
	// Switches scene to the game view.
	public void showGameView() {
		primaryStage.setScene(gameView.getScene());
	}
	
	// Ends the game.
	public void quitGame() {
		primaryStage.close();
	}
	
	@Override
	public void start(Stage primaryStage) throws Exception {
		this.primaryStage = primaryStage;
		GameController gameController = new GameController();
		PracticeController practiceController = new PracticeController();
		
		// Setup the views.
		menuView = new MenuView(this, HEIGHT, HEIGHT);
		practiceView = new PracticeView(this, practiceController, HEIGHT, HEIGHT);
		gameView = new GameView(this, gameController, HEIGHT, HEIGHT);
		
		// Start at the menu.
		primaryStage.setTitle("Quinzical");
		showMenu();
		primaryStage.show();
		
	}
	
	public static void main(String[] args) {
		launch(args);
	}

}
