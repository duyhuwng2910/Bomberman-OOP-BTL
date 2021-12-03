package main.java.Input;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/**
 * Class Keyboard dùng để nhận dữ liệu từ bàn phím.
 */
public class Keyboard implements KeyListener {
  private boolean[] keys = new boolean[200];
  public boolean up, down, left, right, space;

  public void update() {
    up = keys[KeyEvent.VK_UP] || keys[KeyEvent.VK_W];
    down = keys[KeyEvent.VK_DOWN] || keys[KeyEvent.VK_S];
    left = keys[KeyEvent.VK_LEFT] || keys[KeyEvent.VK_A];
    right = keys[KeyEvent.VK_RIGHT] || keys[KeyEvent.VK_D];
    space = keys[KeyEvent.VK_SPACE] || keys[KeyEvent.VK_X];
  }

  @Override
  public void keyTyped(KeyEvent keyEvent) {}

  @Override
  public void keyPressed(KeyEvent keyEvent) {
    keys[keyEvent.getKeyCode()] = true;

  }

  @Override
  public void keyReleased(KeyEvent keyEvent) {
    keys[keyEvent.getKeyCode()] = false;

  }
}
