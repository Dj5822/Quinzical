package quinzical.controller;

import java.io.OutputStream;
import java.io.PrintWriter;

import javafx.concurrent.Task;

public class AudioTask extends Task<Object> {
	
	private String text;
	
	public AudioTask(String text) {
		this.text = text;
	}

	@Override
	protected Object call() throws Exception {
		try {
			ProcessBuilder builder = new ProcessBuilder("festival", "--tts");			
			Process process = builder.start();
			OutputStream in = process.getOutputStream();
			PrintWriter stdin = new PrintWriter(in);
			stdin.println(text);
			stdin.close();
			process.waitFor();
			
			process.destroy();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

}
