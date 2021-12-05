package main.java.Sound;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import javax.sound.sampled.*;


public class Sound {
    public static void play(String sound) throws LineUnavailableException, UnsupportedAudioFileException, IOException {
        File file = new File ( "./res/sound/"+ sound + ".wav");
        AudioInputStream audioStream = AudioSystem.getAudioInputStream (file);
        Clip clip = AudioSystem.getClip ();
        clip.open (audioStream);
    }
//    public static void stop(String sound){
//        String mp3 = sound + ".mp3";
//      Media media = new Media(new File(mp3).toURI().toString());
//        MediaPlayer mediaPlayer = new MediaPlayer(media);
//        mediaPlayer.pause();
//    }
}