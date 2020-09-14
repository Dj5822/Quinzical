package quinzical;

import javafx.application.Application;
import javafx.stage.Stage;
import quinzical.controller.GameController;
import quinzical.controller.MenuController;
import quinzical.controller.PracticeController;
import quinzical.controller.SceneController;
import quinzical.ui.GameView;
import quinzical.ui.MenuView;
import quinzical.ui.PracticeView;

public class Main extends Application {
	
	private final int HEIGHT = 500;
	private final int WIDTH = 1000;
	
	@Override
	public void start(Stage primaryStage) throws Exception {	
		
		// Initialise controllers.
		SceneController sceneController = new SceneController(primaryStage);
		MenuController menuController = new MenuController(sceneController);
		GameController gameController = new GameController(sceneController);
		PracticeController practiceController = new PracticeController(sceneController);
		
		// Initialise views.
		MenuView menuView = new MenuView(menuController, WIDTH, HEIGHT);
		GameView gameView = new GameView(gameController, WIDTH, HEIGHT);
		PracticeView practiceView = new PracticeView(practiceController, WIDTH, HEIGHT);
		
		// Add scenes to scene controller.
		sceneController.addScene("menu", menuView.getScene());
		sceneController.addScene("game", gameView.getScene());
		sceneController.addScene("practice", practiceView.getScene());
		
		// Start at the menu.
		primaryStage.setTitle("Quinzical");
		sceneController.changeScene("menu");
		primaryStage.show();
		
	}
	
	public static void main(String[] args) {
		launch(args);
	}

}
