package quinzical;

import javafx.application.Application;
import javafx.geometry.Rectangle2D;
import javafx.stage.Screen;
import javafx.stage.Stage;
import quinzical.controller.DatabaseController;
import quinzical.controller.GameController;
import quinzical.controller.HelpController;
import quinzical.controller.LeaderboardController;
import quinzical.controller.MenuController;
import quinzical.controller.PracticeController;
import quinzical.controller.SceneController;
import quinzical.controller.SettingsController;
import quinzical.ui.DatabaseView;
import quinzical.ui.GameView;
import quinzical.ui.HelpView;
import quinzical.ui.LeaderboardView;
import quinzical.ui.MenuView;
import quinzical.ui.PracticeView;
import quinzical.ui.SettingsView;

public class Main extends Application {
	
	/**
	 * Initialises all the components required to run the game.
	 */
	@Override
	public void start(Stage primaryStage) throws Exception {
		
		Rectangle2D screenBounds = Screen.getPrimary().getBounds();

		int height = (int) (screenBounds.getHeight()/1.1);
		int width = (int) screenBounds.getWidth();
		
		if (height > 1200) {
			height = 1200;
		}
		else if (width > 1800) {
			width = 1800;
		}
	
		
		// Initialise controllers.
		SceneController sceneController = new SceneController(primaryStage);
		LeaderboardController leaderboardController = new LeaderboardController(sceneController);
		MenuController menuController = new MenuController(sceneController, leaderboardController);
		SettingsController settingsController = new SettingsController(sceneController);
		DatabaseController databaseController = new DatabaseController(sceneController);
		GameController gameController = new GameController(sceneController, settingsController, leaderboardController);
		PracticeController practiceController = new PracticeController(sceneController, settingsController);
		HelpController helpController = new HelpController(sceneController);
		
		// Initialise views.
		MenuView menuView = new MenuView(menuController, width, height);
		GameView gameView = new GameView(gameController, width, height);
		PracticeView practiceView = new PracticeView(practiceController, width, height);
		LeaderboardView leaderboardView = new LeaderboardView(leaderboardController, width, height);
		SettingsView settingsView = new SettingsView(settingsController, width, height);
		DatabaseView databaseView = new DatabaseView(databaseController, width, height);
		HelpView helpView = new HelpView(helpController, width, height);
		
		// Add scenes to scene controller.
		sceneController.addScene("menu", menuView.getScene());
		sceneController.addScene("game", gameView.getScene());
		sceneController.addScene("practice", practiceView.getScene());
		sceneController.addScene("leaderboard", leaderboardView.getScene());
		sceneController.addScene("settings", settingsView.getScene());
		sceneController.addScene("database",databaseView.getScene());
		sceneController.addScene("help", helpView.getScene());
		
		// Start at the menu.
		primaryStage.setTitle("Quinzical");
		sceneController.changeScene("menu");
		primaryStage.show();
		
	}
	
	public static void main(String[] args) {
		launch(args);
	}

}
