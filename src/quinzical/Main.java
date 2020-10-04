package quinzical;

import javafx.application.Application;
import javafx.geometry.Rectangle2D;
import javafx.stage.Screen;
import javafx.stage.Stage;
import quinzical.controller.GameController;
import quinzical.controller.MenuController;
import quinzical.controller.PracticeController;
import quinzical.controller.SceneController;
import quinzical.controller.SettingsController;
import quinzical.ui.GameView;
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
		GameController gameController = new GameController(sceneController, settingsController);
		PracticeController practiceController = new PracticeController(sceneController, settingsController);
		
		// Initialise views.
		MenuView menuView = new MenuView(menuController, WIDTH, HEIGHT);
		GameView gameView = new GameView(gameController, WIDTH, HEIGHT);
		PracticeView practiceView = new PracticeView(practiceController, WIDTH, HEIGHT);
		SettingsView settingsView = new SettingsView(settingsController, WIDTH, HEIGHT);
		
		// Add scenes to scene controller.
		sceneController.addScene("menu", menuView.getScene());
		sceneController.addScene("game", gameView.getScene());
		sceneController.addScene("practice", practiceView.getScene());
		sceneController.addScene("settings", settingsView.getScene());
		
		// Start at the menu.
		primaryStage.setTitle("Quinzical");
		sceneController.changeScene("menu");
		primaryStage.show();
		
	}
	
	public static void main(String[] args) {
		launch(args);
	}

}
