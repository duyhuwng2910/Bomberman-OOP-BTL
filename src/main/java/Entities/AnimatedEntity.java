package main.java.Entities;

import javafx.scene.image.Image;
import main.java.Graphics.Sprite;

// TODO: Class dành cho enitity có hiệu ứng chuyển cảnh
public abstract class AnimatedEntity extends Entity {
  protected int animated = 0;
  protected final int MAX_ANIMATED = 7500;

  protected void animate() {
    if (animated < MAX_ANIMATED) {
      animated++;
    } else {
      animated = 0;
    }
  }

  public AnimatedEntity(double x, double y, Image img, Sprite sprite) {
    super(x, y, img, sprite);
  }

}
