package com.effecia.utility;

import java.io.IOException;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;

public class UtilClient {

	public static String convertObjectToJsonString(Object object) {
		ObjectMapper objectMapper = new ObjectMapper();
		try {
			if (object != null) {
				return objectMapper.writeValueAsString(object);
			}

		} catch (JsonGenerationException ex) {
			ex.printStackTrace();
		} catch (JsonMappingException ex) {
			ex.printStackTrace();
		} catch (IOException ex) {
			ex.printStackTrace();
		}
		return null;
	}
	
	public static synchronized void playSound() {
		new Thread(new Runnable() {

			public void run() {
				try {
					Clip clip = AudioSystem.getClip();
					AudioInputStream audioInputStream = AudioSystem
							.getAudioInputStream(this.getClass().getClassLoader().getResourceAsStream("Korg-TR-Rack-Standard-Kit-Crash-Cymbal.wav"));
					clip.open(audioInputStream);
					clip.start();
					Thread.sleep(1000);
				} catch (LineUnavailableException e) {
					e.printStackTrace();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}).start();
	}
}
