package main.java.Entities.staticEntities.Items;

import javafx.scene.image.Image;
import main.java.Entities.Entity;
import main.java.Graphics.Screen;
import main.java.Graphics.Sprite;

public class Items extends Entity {

  public Items(double x, double y, Sprite sprite) {
    super(x, y, sprite);
  }

  @Override
  public void render(Screen screen) {

  }

  @Override
  public void update() {

  }

  @Override
  public boolean collided(Entity entity) {
    return false;
  }
}
