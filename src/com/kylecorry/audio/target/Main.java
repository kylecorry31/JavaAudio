package com.kylecorry.audio.target;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;

import javax.sound.sampled.AudioFileFormat;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.TargetDataLine;

public class Main {
	

	public static void main(String[] args) {
		
		Scanner scan = new Scanner(System.in);
		System.out.println("How many seconds of audio would you like to record?");
		int seconds = scan.nextInt();

		try{
			AudioFormat format = new AudioFormat(AudioFormat.Encoding.PCM_SIGNED, 44100, 16, 2, 4, 44100, false);
			DataLine.Info info = new DataLine.Info(TargetDataLine.class, format);
			if(!AudioSystem.isLineSupported(info))
				System.err.println("Line not supported");
			final TargetDataLine targetLine = (TargetDataLine) AudioSystem.getLine(info);
			targetLine.open();
			System.out.println("Starting recording");
			targetLine.start();
			Thread thread = new Thread(){
				@Override
				public void run(){
					AudioInputStream audioInputStream = new AudioInputStream(targetLine);
					File audioFile = new File("recording-" + System.currentTimeMillis() + ".wav");
					try {
						AudioSystem.write(audioInputStream, AudioFileFormat.Type.WAVE, audioFile);
					} catch (IOException e) {
						e.printStackTrace();
					}
					System.out.println("Stopped recording");
				}
			};
			thread.start();
			Thread.sleep(seconds * 1000);
			targetLine.stop();
			targetLine.close();
		} catch (Exception e){
			e.printStackTrace();
		}
	}

}
