package quinzical;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;

public class MenuView {
	
	private GameController controller;
	
	private Scene mainMenuScene;
	
	private Label title;
	private Button practiceButton;
	private Button gameButton;
	private Button quitButton;
	
	public MenuView(GameController controller, int width, int height) {
		
		this.controller = controller;
		
		GridPane mainPane = new GridPane();
		mainMenuScene = new Scene(mainPane, width, height);
		
		title = new Label("Quinzical");
		practiceButton = new Button("Practice");
		gameButton = new Button("Play Game");
		quitButton = new Button("Quit");
		
		
		mainPane.add(title, 0, 0);
		mainPane.add(practiceButton, 0, 1);
		mainPane.add(gameButton, 0, 2);
		mainPane.add(quitButton, 0, 3);
	}
	
	public Scene getScene() {
		return mainMenuScene;
	}
}
