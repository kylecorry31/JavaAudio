package com.kylecorry.audio;

import java.io.IOException;
import java.net.URL;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.Mixer;
import javax.sound.sampled.UnsupportedAudioFileException;

public class Output {

	private static Clip clip = null;

	public static boolean isPlaying() {
		if (clip == null)
			return false;
		return clip.isActive();
	}

	public static void playInBackground(String url) {
		Mixer mixer = AudioSystem.getMixer(AudioSystem.getMixerInfo()[0]);
		DataLine.Info dataInfo = new DataLine.Info(Clip.class, null);
		try {
			clip = (Clip) mixer.getLine(dataInfo);

			URL soundUrl = Output.class.getResource(url);

			AudioInputStream audioInputStream = AudioSystem
					.getAudioInputStream(soundUrl);
			clip.open(audioInputStream);

		} catch (UnsupportedAudioFileException | IOException e) {
			e.printStackTrace();
		} catch (LineUnavailableException e) {
			e.printStackTrace();
		}

		clip.start();
	}

	public static void stop() {
		if (clip != null)
			clip.stop();
	}

	public static void play(String url) {
		playInBackground(url);
		do {
			try {
				Thread.sleep(50);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		} while (isPlaying());
	}

}
