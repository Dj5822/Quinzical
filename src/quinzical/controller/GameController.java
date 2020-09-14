package quinzical.controller;

public class GameController {
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
	 * The Quinzical clue/money grid is disGameplayed. There are 5 clues in each category. These 
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
}
