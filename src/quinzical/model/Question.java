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
	
	
	
	public Question(String clue, String answerFront, String answerBack) {
		this.clue = clue;
		this.answerFront = answerFront;
		this.answerBack = answerBack;
	}
	
	public String getClue() {
		return clue;
	}
	
	public char getFirstLetterOfAnswerBack() {
		return answerBack.charAt(1);
	}
	
	public String getAnswerBack() {
		return answerBack;
	}
	
	/**
	 * 
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
