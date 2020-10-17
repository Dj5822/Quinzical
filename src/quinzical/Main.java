package quinzical;

import javafx.application.Application;
import javafx.geometry.Rectangle2D;
import javafx.stage.Screen;
import javafx.stage.Stage;
import quinzical.controller.DatabaseController;
import quinzical.controller.GameController;
import quinzical.controller.LeaderboardController;
import quinzical.controller.MenuController;
import quinzical.controller.PracticeController;
import quinzical.controller.SceneController;
import quinzical.controller.SettingsController;
import quinzical.ui.DatabaseView;
import quinzical.ui.GameView;
import quinzical.ui.LeaderboardView;
import quinzical.ui.MenuView;
import quinzical.ui.PracticeView;
import quinzical.ui.SettingsView;

public class Main extends Application {
	
	@Override
	public void start(Stage primaryStage) throws Exception {
		
		Rectangle2D screenBounds = Screen.getPrimary().getBounds();
		final int HEIGHT = (int) screenBounds.getHeight()- 60;
		final int WIDTH = (int) screenBounds.getWidth() - 80;
		
		// Initialise controllers.
		SceneController sceneController = new SceneController(primaryStage);
		MenuController menuController = new MenuController(sceneController);
		SettingsController settingsController = new SettingsController(sceneController);
		DatabaseController databaseController = new DatabaseController(sceneController);
		GameController gameController = new GameController(sceneController, settingsController);
		PracticeController practiceController = new PracticeController(sceneController, settingsController);
		LeaderboardController leaderboardController = new LeaderboardController(sceneController);
		
		// Initialise views.
		MenuView menuView = new MenuView(menuController, WIDTH, HEIGHT);
		GameView gameView = new GameView(gameController, WIDTH, HEIGHT);
		PracticeView practiceView = new PracticeView(practiceController, WIDTH, HEIGHT);
		LeaderboardView leaderboardView = new LeaderboardView(leaderboardController, WIDTH, HEIGHT);
		SettingsView settingsView = new SettingsView(settingsController, WIDTH, HEIGHT);
		DatabaseView databaseView = new DatabaseView(databaseController, WIDTH, HEIGHT);
		
		// Add scenes to scene controller.
		sceneController.addScene("menu", menuView.getScene());
		sceneController.addScene("game", gameView.getScene());
		sceneController.addScene("practice", practiceView.getScene());
		sceneController.addScene("leaderboard", leaderboardView.getScene());
		sceneController.addScene("settings", settingsView.getScene());
		sceneController.addScene("database",databaseView.getScene());
		
		// Start at the menu.
		primaryStage.setTitle("Quinzical");
		sceneController.changeScene("menu");
		primaryStage.show();
		
	}
	
	public static void main(String[] args) {
		launch(args);
	}

}
