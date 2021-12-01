package main.java.Sound;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

import javax.sound.sampled.*;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class Sound {
    private Media media;
    private MediaPlayer mediaPlayer;
    public static void play(String sound) throws LineUnavailableException, IOException, UnsupportedAudioFileException {
        File file = new File("./res/sound" + sound + ".mp3");
        AudioInputStream audio = AudioSystem.getAudioInputStream(file);
        Clip clip = AudioSystem.getClip();
        clip.open(audio);
        clip.start();
    }
    public static void stop(String sound){
        new Thread(new Runnable() {
            public void run() {
                try {
                    Clip clip = AudioSystem.getClip();
                    AudioInputStream inputStream = AudioSystem.getAudioInputStream(
                            Main.class.getResourceAsStream("/sound/" + sound + ".wav"));
                    clip.open(inputStream);
                    clip.stop();
                } catch (Exception e) {
                    System.err.println(e.getMessage());
                }
            }
        }).start();
    }
}