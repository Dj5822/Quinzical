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
	
	/**
	 * 5 Categories from the New Zealand Question Set are randomly selected.
	 * 
	 * @return list/array of 5 random categories.
	 */
	public String[] selectCategories() {
		// needs to be implemented.
		
		return null;
	}
	
	/**
	 * The Quinzical clue/money grid is displayed. There are 5 clues in each category. These 
	 * 5 clues are randomly selected from the list.
	 * 
	 * @param list/array of 5 random categories.
	 * @return two-dimensional array/list of clues for each category.
	 */
	public String[][] selectClues(){
		// needs to be implemented.
		
		return null;
	}
	
	/**
	 * If answer is correct, call correctAnswer().
	 * If answer is incorrect, call wrongAnswer().
	 * @param answer
	 */
	public void checkIfAnswerIsCorrect(String answer) {
		
	}
	
	/**
	 * When the user types in the correct answer, their worth increases accordingly.
	 */
	private void correctAnswer() {
		// needs to be implemented.
	}
	
	/**
	 * If the user does not type in the correct answer or clicks the “Don’t know” the clue
	 * disappears off the grid, but their worth does not increase.
	 */
	private void wrongAnswer() {
		// needs to be implemented.
	}
	
	@Override
	public void start(Stage primaryStage) throws Exception {
		this.primaryStage = primaryStage;
		
		// Setup the views.
		menuView = new MenuView(this, HEIGHT, HEIGHT);
		practiceView = new PracticeView(this, HEIGHT, HEIGHT);
		gameView = new GameView(this, HEIGHT, HEIGHT);
		
		// Start at the menu.
		primaryStage.setTitle("Quinzical");
		showMenu();
		primaryStage.show();
		
	}
	
	public static void main(String[] args) {
		launch(args);
	}

}
