package main.java.Input;

import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.input.KeyEvent;

public class Keyboard implements EventHandler<KeyEvent> {
  protected boolean[] check = new boolean[200];

  public Keyboard(Scene scene) {
    scene.setOnKeyPressed(this);
    scene.setOnKeyReleased(this);
  }

  public void update() {
  }

  @Override
  public void handle(KeyEvent keyEvent) {
    if (KeyEvent.KEY_PRESSED.equals(keyEvent.getEventType())) {
      switch (keyEvent.getCode()) {
        case DOWN:
          check[java.awt.event.KeyEvent.VK_DOWN] = true;
          break;
        case UP:
          check[java.awt.event.KeyEvent.VK_UP] = true;
          break;
        case LEFT:
          check[java.awt.event.KeyEvent.VK_LEFT] = true;
          break;
        case RIGHT:
          check[java.awt.event.KeyEvent.VK_RIGHT] = true;
          break;
      }
    } else if (keyEvent.KEY_RELEASED.equals(keyEvent.getEventType())) {
      switch (keyEvent.getCode()) {
        case DOWN:
          check[java.awt.event.KeyEvent.VK_DOWN] = false;
          break;
        case UP:
          check[java.awt.event.KeyEvent.VK_UP] = false;
          break;
        case LEFT:
          check[java.awt.event.KeyEvent.VK_LEFT] = false;
          break;
        case RIGHT:
          check[java.awt.event.KeyEvent.VK_RIGHT] = false;
          break;
      }
    }
  }
  public boolean isPressed(int keyEvent) {
    return check[keyEvent];
  }
}
