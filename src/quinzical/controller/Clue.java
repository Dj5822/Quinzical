package quinzical.controller;
/**
 * This class is used to create a clue object which contains
 * the question part and two parts of its answer. Contains 
 * method to return parts of this clue.
 *
 */
public class Clue {
	String question;
	String ans_1;
	String ans_2;
	public Clue(String question, String ans_1, String ans_2) {
		this.question=question;
		this.ans_1=ans_1;
		this.ans_2=ans_2;
	}
	public String getquestion() {
		return question;
	}
	public String getans_1() {
		return ans_1;
	}
	public String getans_2() {
		return ans_2;
	}
}
