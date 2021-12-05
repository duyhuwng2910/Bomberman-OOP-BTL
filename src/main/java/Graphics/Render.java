package main.java.Graphics;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.IOException;

public interface Render {
  public void update() throws UnsupportedAudioFileException, LineUnavailableException, IOException;

  public void render(Screen screen);
}
