package main.java.Entities.staticEntities.Items;

import main.java.Entities.Entity;
import main.java.Graphics.Sprite;

public class FlameItems extends Items {
  public FlameItems(double x, double y, Sprite sprite) {
    super(x, y, sprite);
  }

  @Override
  public boolean collided(Entity entity) {
    return false;
  }
}
