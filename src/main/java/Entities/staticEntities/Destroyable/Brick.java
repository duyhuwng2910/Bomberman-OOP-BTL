package main.java.Entities.staticEntities.Destroyable;

import javafx.scene.image.Image;
import main.java.Entities.Entity;
import main.java.Graphics.Screen;
import main.java.Graphics.Sprite;
import main.java.Level.Coordinates;

public class Brick extends DestroyableTile {
  public Brick(double x, double y, Sprite sprite) {
    super(x, y, sprite);
  }

  @Override
  public void render(Screen screen) {
    int x = Coordinates.tileToPixel(this.x);
    int y = Coordinates.tileToPixel(this.y);

    if(this.destroyed) {
      sprite = movingSprite(Sprite.brick_exploded,
          Sprite.brick_exploded1, Sprite.brick_exploded2);
      screen.renderEntityWithBelowSprite(x, y, this, this.belowSprite);
    } else {
      screen.renderEntity(x, y, this);
    }
  }

  @Override
  public void update() {
    super.update();
  }

}