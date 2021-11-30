package main.java.Entities.staticEntities.Destroyable;

import javafx.scene.image.Image;
import main.java.Entities.Entity;
import main.java.Entities.staticEntities.Tile;
import main.java.Graphics.Sprite;

public class DestroyableTile extends Tile {

  private final int MAX_ANIMATE = 7500;
  private int animate = 0;
  protected boolean destroyed = false;
  protected int timeToDisapear = 20;
  protected Sprite belowSprite = Sprite.grass;

  public DestroyableTile(double x, double y, Image img, Sprite sprite) {
    super(x, y, img, sprite);
  }

  @Override
  public void update() {
    if (destroyed) {
      if (animate < MAX_ANIMATE) {
        animate++;
      } else {
        animate = 0;
      }

      if (timeToDisapear > 0) {
        timeToDisapear--;
      } else {
        remove();
      }
    }
  }

  public void destroy() {
    destroyed = true;
  }

  @Override
  public boolean collided(Entity entity) {
    return false;
  }

  public void addBelowImage(Sprite sprite) {
    belowSprite = sprite;
  }

}
