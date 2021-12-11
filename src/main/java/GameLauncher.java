package main.java;

import main.java.Exception.GameException;
import main.java.GUI.Frame;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.IOException;
import main.java.Sound.Sound;

public class GameLauncher {
	
	public static void main(String[] args) throws GameException, UnsupportedAudioFileException, LineUnavailableException, IOException {
		Frame mainwindow = new Frame();
		Sound.play("soundtrack");
	}
}
