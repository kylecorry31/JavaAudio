package com.kylecorry.audio;

import java.io.File;
import java.io.IOException;

import javax.sound.sampled.AudioFileFormat;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.TargetDataLine;

public class Input {
	
	
	private static TargetDataLine targetLine = null;

	public static void record(String filename, int duration) {
		startRecording(filename);
		try {
			Thread.sleep(duration * 1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		stopRecording();
	}
	
	public static void startRecording(String filename){
		try {
			AudioFormat format = new AudioFormat(
					AudioFormat.Encoding.PCM_SIGNED, 44100, 16, 2, 4, 44100,
					false);
			DataLine.Info info = new DataLine.Info(TargetDataLine.class, format);
			if (!AudioSystem.isLineSupported(info))
				System.err.println("Line not supported");
			targetLine = (TargetDataLine) AudioSystem.getLine(info);
			targetLine.open();
			targetLine.start();
			Thread thread = new Thread() {
				@Override
				public void run() {
					AudioInputStream audioInputStream = new AudioInputStream(
							targetLine);
					File audioFile = new File(filename);
					try {
						AudioSystem.write(audioInputStream,
								AudioFileFormat.Type.WAVE, audioFile);
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			};
			thread.start();
		} catch (LineUnavailableException e1) {
			e1.printStackTrace();
		}
	}
	
	public static void stopRecording(){
		if(targetLine == null)
			return;
		targetLine.stop();
		targetLine.close();
	}

}
