package main.java.Entities.staticEntities.Items;

import java.io.IOException;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import main.java.Entities.Entity;
import main.java.Entities.dynamicEntities.Bomber;
import main.java.Game;
import main.java.Graphics.Sprite;
import main.java.Sound.Sound;

public class SpeedItems extends Items {
  public SpeedItems(double x, double y, int level, Sprite sprite) {
    super(x, y, level, sprite);
  }

  @Override
  public void setValues() {
    active = true;
    Game.addBomberSpeed(0.25);
  }

  @Override
  public boolean collided(Entity entity)
      throws UnsupportedAudioFileException, LineUnavailableException, IOException {
    if (entity instanceof Bomber) {
      ((Bomber) entity).addItem(this);
      Sound.play("item");
      remove();
      return true;
    }
    return false;
  }
}
