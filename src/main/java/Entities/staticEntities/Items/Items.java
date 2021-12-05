package main.java.Entities.staticEntities.Items;

import java.io.IOException;
import javafx.scene.image.Image;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import main.java.Entities.Entity;
import main.java.Entities.staticEntities.Tile;
import main.java.Graphics.Screen;
import main.java.Graphics.Sprite;

public class Items extends Tile {
  protected int duration = -1;
  protected boolean active = false;
  protected int level;
  public Items(double x, double y, Sprite sprite) {
    super(x, y, sprite);
  }

  @Override
  public boolean collided(Entity entity)
      throws UnsupportedAudioFileException, LineUnavailableException, IOException {
    return false;
  }
}
