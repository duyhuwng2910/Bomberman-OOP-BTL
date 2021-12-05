package main.java;

import main.java.GUI.Frame;
import main.java.Sound.Sound;

public class GameLauncher {
    public static void main(String[] args) {
        Sound.play("soundtrack");
        new Frame();
    }
}
