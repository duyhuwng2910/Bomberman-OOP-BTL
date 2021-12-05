package main.java;

import main.java.GUI.Frame;
import main.java.Sound.Sound;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.IOException;

public class GameLauncher {
    public static void main(String[] args) throws UnsupportedAudioFileException, LineUnavailableException, IOException {
        Sound.play("soundtrack");
        new Frame();
    }
}
