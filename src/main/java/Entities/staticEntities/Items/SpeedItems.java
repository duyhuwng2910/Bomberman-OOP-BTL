package main.java.Entities.staticEntities.Items;

import main.java.Entities.Entity;
import main.java.Graphics.Sprite;

public class SpeedItems extends Items {
  public SpeedItems(double x, double y, Sprite sprite) {
    super(x, y, sprite);
  }

  @Override
  public boolean collided(Entity entity) {
    return false;
  }
}
