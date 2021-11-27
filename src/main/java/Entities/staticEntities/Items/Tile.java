package main.java.Entities.staticEntities.Items;

import javafx.scene.image.Image;
import main.java.Entities.Entity;
import main.java.Graphics.Sprite;

/**
 * Những entity cố định, không di chuyển.
 */
public class Tile extends Entity {

  public Tile(double x, double y, Image img, Sprite sprite) {
    super(x, y, img, sprite);
  }

  @Override
  public void update() {

  }

  @Override
  public boolean collided(Entity entity) {
    return false;
  }
}
