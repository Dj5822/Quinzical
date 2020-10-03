package quinzical.controller;

import java.io.OutputStream;
import java.io.PrintWriter;

import javafx.concurrent.Task;

public class VoiceTask extends Task<Object> {
	
	private String text;
	private double speed;
	
	public VoiceTask(String text, double speed) {
		this.text = text;
		this.speed = speed;
	}

	@Override
	protected Object call() throws Exception {
		try {
			ProcessBuilder builder = new ProcessBuilder("festival");			
			Process process = builder.start();
			OutputStream in = process.getOutputStream();
			PrintWriter stdin = new PrintWriter(in);
			//stdin.println("(voice_akl_nz_jdt_diphone)");
			stdin.println("(Parameter.set `Duration_Stretch " + 1/speed + ")");
			stdin.println("(SayText \"" + text + "\")");
			stdin.close();
			process.waitFor();
			
			process.destroy();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

}
