package main.java.Sound;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import java.io.File;


public class Sound {
    public static void play(String sound) {
        String mp3 = sound + ".mp3";
        Media media = new Media(new File(mp3).toURI().toString());
        MediaPlayer mediaPlayer = new MediaPlayer(media);
        mediaPlayer.play();
    }
    public static void stop(String sound){
        String mp3 = sound + ".mp3";
        Media media = new Media(new File(mp3).toURI().toString());
        MediaPlayer mediaPlayer = new MediaPlayer(media);
        mediaPlayer.pause();
    }
}