package main.java.Entities;

import javafx.scene.image.Image;

public class Items extends Entity {

  public Items(double x, double y, Image img) {
    super(x, y, img);
  }

  class bombItem extends Entity {

    public bombItem(int x, int y, Image img) {
      super(x, y, img);
    }

    @Override
    public void update() {

    }

    @Override
    public void collided(Entity entity) {

    }
  }

  @Override
  public void update() {

  }

  @Override
  public void collided(Entity entity) {

  }
}
