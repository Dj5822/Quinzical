package quinzical;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class GameController extends Application {
	
	private final int HEIGHT = 500;
	private final int WIDTH = 1000;
	
	private Stage primaryStage;
	
	private MenuView menuView;
	
	// Switches scene to the menu.
	public void showMenu() {
		primaryStage.setScene(menuView.getScene());
	}
	
	@Override
	public void start(Stage primaryStage) throws Exception {
		this.primaryStage = primaryStage;
		
		// Setup the views.
		menuView = new MenuView(this, HEIGHT, HEIGHT);
		
		// Start at the menu.
		primaryStage.setTitle("Quinzical");
		showMenu();
		primaryStage.show();
		
	}
	
	public static void main(String[] args) {
		launch(args);
	}

}
