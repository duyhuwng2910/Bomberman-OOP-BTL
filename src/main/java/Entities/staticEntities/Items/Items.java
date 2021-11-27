package main.java.Entities.staticEntities.Items;

import javafx.scene.image.Image;
import main.java.Entities.Entity;
import main.java.Graphics.Sprite;

public class Items extends Entity {

  public Items(double x, double y, Image img, Sprite sprite) {
    super(x, y, img, sprite);
  }

  class bombItem extends Entity {

    public bombItem(int x, int y, Image img, Sprite sprite) {
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

  @Override
  public void update() {

  }

  @Override
  public boolean collided(Entity entity) {
    return false;
  }
}
