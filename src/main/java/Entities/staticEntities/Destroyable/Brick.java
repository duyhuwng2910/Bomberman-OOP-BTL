package main.java.Entities.staticEntities.Destroyable;

import javafx.scene.image.Image;
import main.java.Entities.Entity;
import main.java.Graphics.Sprite;

public class Brick extends Entity {

  public Brick(double x, double y, Image img, Sprite sprite) {
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