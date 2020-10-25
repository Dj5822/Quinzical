package quinzical.model;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

import quinzical.controller.GameController;

/**
 * 
 * @author Dylan Jung
 * 
 * Used in the game mode to represent a category.
 * Each category will typically store 5 questions.
 *
 */
public class Category {
	
	private String name;
	private ArrayList<Question> questions;
	
	/**
	 * When a new category is created, it must contain 5 random clues
	 * belonging to that category
	 */
	public Category(String categoryName) {
		this.name = categoryName;
		this.questions = new ArrayList<Question>();
	}
	
	/**
	 * Gets five random questions and adds them to the questions arraylist.
	 * @param controller
	 * @param gameMode
	 */
	public void selectQuestions(GameController controller, String gameMode) {
		try {
			ProcessBuilder qbuilder = new ProcessBuilder("bash", "-c", "./scripts/get5RandomQuestion.sh \"" + name + "\" " + gameMode);
			Process qprocess = qbuilder.start();
			InputStream qinputStream = qprocess.getInputStream();
			InputStream qerrorStream = qprocess.getErrorStream();
			BufferedReader qinputReader = new BufferedReader(new InputStreamReader(qinputStream));
			BufferedReader qerrorReader = new BufferedReader(new InputStreamReader(qerrorStream));
			int qexitStatus = qprocess.waitFor();			
			if (qexitStatus == 0) {
				String line;
				while ((line = qinputReader.readLine()) != null) {
					String[] output = line.split("[\\(\\)]", 3);
					questions.add(new Question(output[0],output[1],output[2]));
				}	
			} 
			else {
				controller.showErrorMessage("Failed to get random questions", qerrorReader.readLine());
			}
			qprocess.destroy();
		}
		catch(Exception e) {
			controller.showErrorMessage("Failed to get random questions", "Could not read script.");
		}
	}
	
	/**
	 * Get one of the questions (in an inputed position)
	 * belonging to the category.
	 */
	public Question getQuestion(int position) {
		return questions.get(position);
	}
	
	/**
	 * Returns the name of the category.
	 * @return
	 */
	public String getName() {
		return name;
	}
	
}
