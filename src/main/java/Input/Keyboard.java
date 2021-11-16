package main.java.Input;

import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.input.KeyEvent;

public class Keyboard {
  protected EventHandler<KeyEvent> eventHandler;
  protected String result;

  public Keyboard(Scene scene) {
    eventHandler = new EventHandler<KeyEvent>() {
      @Override
      public void handle(KeyEvent keyEvent) {
        switch (keyEvent.getCode()) {
          case UP:
            result = keyEvent.getCode().toString();
            System.out.println("UP");
            break;
          case LEFT:
            result = keyEvent.getCode().toString();
            System.out.println("LEFT");
            break;
          case DOWN:
            result = keyEvent.getCode().toString();
            System.out.println("DOWN");
            break;
          case RIGHT:
            result = keyEvent.getCode().toString();
            System.out.println("RIGHT");
            break;
          case SPACE:
            result = keyEvent.getCode().toString();
            System.out.println("MAKE BOMB");
            break;
          default:
            result = keyEvent.getCode().toString();
            System.out.println("Another");
            break;
        }
      }
    };
    scene.setOnKeyPressed(eventHandler);
  }

  public void update() {
  }

  public String getKey() {
    return result;
  }
}
