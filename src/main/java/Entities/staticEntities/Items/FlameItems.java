package main.java.Entities.staticEntities.Items;

import java.io.IOException;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import main.java.Entities.Entity;
import main.java.Entities.dynamicEntities.Bomber;
import main.java.Game;
import main.java.Graphics.Sprite;
import main.java.Sound.Sound;

/**
 * Class FlameItems.
 */
public class FlameItems extends Items {
  public FlameItems(double x, double y, Sprite sprite) {
    super(x, y, sprite);
  }

  @Override
  public boolean collided(Entity entity)
      throws UnsupportedAudioFileException, LineUnavailableException, IOException {
    if (entity instanceof Bomber) {
      Sound.play("item");
      Game.addBombRadius(1);
      remove();
    }
    return false;
  }
}
