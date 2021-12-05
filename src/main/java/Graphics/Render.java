package main.java.Graphics;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.IOException;

public interface Render {
  void update() throws UnsupportedAudioFileException, LineUnavailableException, IOException;

  void render(Screen screen);
}
