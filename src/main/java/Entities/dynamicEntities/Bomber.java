package main.java.Entities.dynamicEntities;

import java.awt.event.KeyEvent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import main.java.Entities.Entity;
import main.java.GameLauncher;
import main.java.Graphics.Sprite;
import main.java.Input.Keyboard;

public class Bomber extends Entity {
  protected Keyboard keyboard;
  public Bomber(double x, double y, Image img, Scene scene) {
    this.x = x;
    this.y = y;
    this.img = img;
    keyboard = new Keyboard(scene);
  }

  @Override
  public void update() {
    if (keyboard.isPressed(KeyEvent.VK_DOWN) && y < Sprite.SCALED_SIZE * (GameLauncher.HEIGHT - 2)) {
      y += 2;
      System.out.println(this.x + " " + this.y);
    }
    if (keyboard.isPressed(KeyEvent.VK_UP) && y > Sprite.SCALED_SIZE) {
      y -= 2;
      System.out.println(this.x + " " + this.y);
    }
    if (keyboard.isPressed(KeyEvent.VK_LEFT) && x > Sprite.SCALED_SIZE) {
      x -= 2;
      System.out.println(this.x + " " + this.y);
    }
    if (keyboard.isPressed(KeyEvent.VK_RIGHT) && x < 940) {
      x += 2;
      System.out.println(this.x + " " + this.y);
    }
  }

  @Override
  public boolean collided(Entity entity) {
    return false;
  }
}
