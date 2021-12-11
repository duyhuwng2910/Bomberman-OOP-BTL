package main.java.Sound;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.File;
import java.io.IOException;
import javax.sound.sampled.*;

/**
 * Class Sound thực hiện quá trình
 * phát và dừng nhạc.
 */
public class Sound {
    public static void play(String sound) throws LineUnavailableException, UnsupportedAudioFileException, IOException {
        File file = new File ( "./res/sound/"+ sound + ".wav");
        AudioInputStream audioStream = AudioSystem.getAudioInputStream (file);
        Clip clip = AudioSystem.getClip();
        clip.open(audioStream);
        clip.start();
    }
}