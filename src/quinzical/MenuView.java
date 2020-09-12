package quinzical;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
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
		
		// Initialize buttons and labels.
		title = new Label("Quinzical");
		practiceButton = new Button("Practice");
		gameButton = new Button("Play Game");
		quitButton = new Button("Quit");
		
		// On practice button activation.
		practiceButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent arg0) {
				controller.showPracticeView();
			}
			
		});
		
		// On game button activation.
		gameButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent arg0) {
				controller.showGameView();
			}
			
		});
		
		// on quit button activation.
		quitButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent arg0) {
				controller.quitGame();
			}
			
		});
		
		// Add buttons and labels to the view.
		mainPane.add(title, 0, 0);
		mainPane.add(practiceButton, 0, 1);
		mainPane.add(gameButton, 0, 2);
		mainPane.add(quitButton, 0, 3);
	}
	
	public Scene getScene() {
		return mainMenuScene;
	}
}
