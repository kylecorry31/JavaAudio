package com.kylecorry.audio.clip;

import java.io.IOException;
import java.net.URL;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.Mixer;
import javax.sound.sampled.UnsupportedAudioFileException;

public class Main {

	static Mixer mixer;
	static Clip clip;

	public static void main(String[] args) {
		mixer = AudioSystem.getMixer(AudioSystem.getMixerInfo()[0]);
		DataLine.Info dataInfo = new DataLine.Info(Clip.class, null);
		try {
			clip = (Clip) mixer.getLine(dataInfo);
		} catch (LineUnavailableException e) {
			e.printStackTrace();
		}

		try {
			URL soundUrl = Main.class.getResource("/res/terminator.wav");

			AudioInputStream audioInputStream = AudioSystem
					.getAudioInputStream(soundUrl);
			clip.open(audioInputStream);

		} catch (UnsupportedAudioFileException | IOException e) {
			e.printStackTrace();
		} catch (LineUnavailableException e) {
			e.printStackTrace();
		}

		clip.start();

		do {
			try {
				Thread.sleep(50);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		} while (clip.isActive());

	}

}
