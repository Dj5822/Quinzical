package quinzical;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class GameController extends Application {
	
	private final int HEIGHT = 500;
	private final int WIDTH = 1000;

	@Override
	public void start(Stage primaryStage) throws Exception {
		primaryStage.setTitle("Quinzical");
		primaryStage.setScene(new Scene(new GridPane(), WIDTH, HEIGHT));
		primaryStage.show();
		
	}
	
	public static void main(String[] args) {
		launch(args);
	}

}
