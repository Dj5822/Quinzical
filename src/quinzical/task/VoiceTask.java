package quinzical.task;

import java.io.OutputStream;
import java.io.PrintWriter;

import javafx.concurrent.Task;

public class VoiceTask extends Task<Object> {
	
	private String text;
	private double speed;
	private String type;
	
	/**
	 * Text is the text that is going to be read out by festival.
	 * Speed is the speed at which the text is read out. Higher speed means
	 * the text is read out at a faster rate.
	 * Type is the voice type that is used to read out the text.
	 * @param text
	 * @param speed
	 * @param type
	 */
	public VoiceTask(String text, double speed, String type) {
		this.text = text;
		this.speed = speed;
		this.type = type;
	}
	
	/**
	 * Uses festival to read out the text.
	 */
	@Override
	protected Object call() throws Exception {
		try {
			ProcessBuilder builder = new ProcessBuilder("festival");			
			Process process = builder.start();
			OutputStream in = process.getOutputStream();
			PrintWriter stdin = new PrintWriter(in);
			
			if (type.equals("nz male")) {
				stdin.println("(voice_akl_nz_jdt_diphone)");
			}
			else if (type.equals("nz female")) {
				stdin.println("(voice_akl_nz_cw_cg_cg)");
			}
			else {
				stdin.println("(voice_kal_diphone)");
			}
			
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
