package main.java.Entities.staticEntities;

import javafx.scene.image.Image;
import main.java.Entities.Entity;
import main.java.Graphics.Screen;
import main.java.Graphics.Sprite;
import main.java.Level.Coordinates;

/**
 * Những entity cố định, không di chuyển.
 */
public class Tile extends Entity {

  public Tile(double x, double y, Sprite sprite) {
    super(x, y, sprite);
  }

  @Override
  public void render(Screen screen) {
    screen.renderEntity(Coordinates.tileToPixel(x),
        Coordinates.tileToPixel(y), this);
  }

  @Override
  public void update() {

  }

  @Override
  public boolean collided(Entity entity) {
    return false;
  }
}