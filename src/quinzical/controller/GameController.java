package quinzical.controller;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

public class GameController {
	
	private SceneController sceneController;
	
	private String[] categories;
	private String[][][] questions;
	private int[] enablebtns;
	private int currentwinning;
	
	private String question;
	private String[] answer;
	private int[] qspos;
	private int count;
	
	public GameController(SceneController sceneController) {
		this.sceneController = sceneController;
	}
	public void showErrorMessage(String headerMessage, String contentMessage) {
		Alert errorAlert = new Alert(AlertType.ERROR);
		errorAlert.setTitle("Error encountered");
		errorAlert.setHeaderText(headerMessage);
		errorAlert.setContentText(contentMessage);
		
		errorAlert.showAndWait();
	}
	public void generatedata() {
		categories = new String[5];
		questions = new String[5][5][3];
		enablebtns = new int[] {0,0,0,0,0};
		currentwinning = 0;
		count=0;
		try {
			ProcessBuilder builder = new ProcessBuilder("bash", "-c", "ls categories | shuf -n 5");
			
			Process process = builder.start();
			InputStream inputStream = process.getInputStream();
			InputStream errorStream = process.getErrorStream();
			BufferedReader inputReader = new BufferedReader(new InputStreamReader(inputStream));
			BufferedReader errorReader = new BufferedReader(new InputStreamReader(errorStream));
			int exitStatus = process.waitFor();
			
			// If there is no error in the command, then output the result.
			if (exitStatus == 0) {
				String line;
				int i = 0;
				while ((line = inputReader.readLine()) != null) {
					String[] catname = line.split("\\.",2);
			        categories[i]=catname[0];
			        i++;
				}
			} 
			else {
				showErrorMessage("Failed to get categories", errorReader.readLine());
			}
			process.destroy();
			for(int catno=0;catno<5;catno++) {
				ProcessBuilder qbuilder = new ProcessBuilder("bash", "-c", "./scripts/get5RandomQuestion.sh \"" + categories[catno] + "\"");
				Process qprocess = qbuilder.start();
				InputStream qinputStream = qprocess.getInputStream();
				InputStream qerrorStream = qprocess.getErrorStream();
				BufferedReader qinputReader = new BufferedReader(new InputStreamReader(qinputStream));
				BufferedReader qerrorReader = new BufferedReader(new InputStreamReader(qerrorStream));
				int qexitStatus = qprocess.waitFor();			
				if (qexitStatus == 0) {
					String line;
					int qno=0;
					while ((line = qinputReader.readLine()) != null) {
						String[] output = line.split("[\\(\\)]", 3);
						questions[catno][qno][0] = output[0];
						questions[catno][qno][1] = output[1];
						questions[catno][qno][2] = output[2];
						qno++;
					}	
				} 
				else {
					showErrorMessage("Failed to get a random question", qerrorReader.readLine());
				}
				qprocess.destroy();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	public void cluebtnclicked(int colindex, int rowindex) {
		// festival here
		qspos = new int[2];
		qspos[0] = colindex;
		qspos[1] = rowindex;
		question = questions[colindex][rowindex][0];
		answer = new String[2];
		answer[0] = questions[colindex][rowindex][1];
		answer[1] = questions[colindex][rowindex][2];
		AudioTask task1 = new AudioTask(question, 1);
		Thread thread1 = new Thread(task1);
		thread1.start();
		System.out.println(question);
	}
	public boolean checkAnswer(String text) {
		count++;
		if(enablebtns[qspos[0]]<4) {
			enablebtns[qspos[0]]++;
		}
		for (String potentialAnswer : answer[1].split("/")) {
			String answerRegex = "(" + answer[0].toLowerCase().strip() + " )?" + potentialAnswer.replace(".", "").toLowerCase().strip();
			
			if (text.toLowerCase().strip().matches(answerRegex)) {
				currentwinning+= (qspos[1]+1)*100;
				return true;
			}
		}
		return false;
		
	}
	public void dkbtnclicked() {
		count++;
		if(enablebtns[qspos[0]]<4) {
			enablebtns[qspos[0]]++;
		}
	}
	/**
	 * Used to go back to the menu scene.
	 */
	public void returnToMenu() {
		sceneController.changeScene("menu");
	}
	public String[] getcat() {
		return categories;
	}
	public String[][][] getqs(){
		return questions;
	}
	public int[] getenablebtns() {
		return enablebtns;
	}
	public int[] getqspos() {
		return qspos;
	}
	public String getans() {
		return answer[1];
	}
	public int getcurrentwinning() {
		return currentwinning;
	}
	public int getcount() {
		return count;
	}
}
