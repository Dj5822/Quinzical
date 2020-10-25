package quinzical.model;
/**
 * Used in the game mode to represent a clue.
 * Also contains the answer to that respective clue.
 *
 */
public class Question {
	
	private String clue;
	private String answerFront;
	private String answerBack;
	
	/**
	 * The question is split into a clue component and an answer component.
	 * Note that the answer has a front and back section.
	 * Front section is typically something like 'what is'
	 * and back section is typically something like 'a potato'.
	 * @param clue
	 * @param answerFront
	 * @param answerBack
	 */
	public Question(String clue, String answerFront, String answerBack) {
		this.clue = clue;
		this.answerFront = answerFront;
		this.answerBack = answerBack;
	}
	
	/**
	 * Return the clue for the question.
	 */
	public String getClue() {
		return clue;
	}
	
	/**
	 * Returns a hint for the answer.
	 * @return
	 */
	public char getFirstLetterOfAnswerBack() {
		return answerBack.charAt(1);
	}
	
	/**
	 * Returns the back part of the answer.
	 * @return
	 */
	public String getAnswerBack() {
		return answerBack;
	}
	
	/**
	 * Checks if the inputed answer is correct.
	 * @param input
	 * @return true if answer is correct, false if answer is wrong.
	 */
	public Boolean checkAnswerIsCorrect(String input) {
		for (String potentialAnswer : answerBack.split("/")) {
			String answerRegex = "(" + answerFront.toLowerCase().strip() + " )?" + potentialAnswer.replace(".", "").toLowerCase().strip();
			
			if (input.toLowerCase().strip().matches(answerRegex)) {
				return true;
			}
			else if (("the " + input.toLowerCase().strip()).matches(answerRegex)) {
				return true;
			}
			else if (("a " + input.toLowerCase().strip()).matches(answerRegex)) {
				return true;
			}
		}
		return false;
	}
}
