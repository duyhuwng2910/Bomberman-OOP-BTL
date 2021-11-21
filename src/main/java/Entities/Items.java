package main.java.Entities;

import javafx.scene.image.Image;

public class Items extends Entities {

  public Items(double x, double y, Image img) {
    super(x, y, img);
  }

  class bombItem extends Entities {

    public bombItem(int x, int y, Image img) {
      super(x, y, img);
    }

    @Override
    public void update() {

    }
  }

  @Override
  public void update() {

  }
}
