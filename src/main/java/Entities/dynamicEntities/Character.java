package main.java.Entities.dynamicEntities;

import main.java.Board;
import main.java.Entities.AnimatedEntity;
import main.java.Game;
import main.java.Graphics.Screen;

/**
 * Class trừu tượng Character dùng để quản lý
 * các sự kiện của các nhân vật có chuyển động
 * hoạt hình bao gồm Bomber và Enemy.
 */
public abstract class Character extends AnimatedEntity {
  protected Board board;
  protected int direction = -1;
  protected boolean isAlive = true;
  protected boolean isMoving = false;
  public int time_left = 40;

  public Character(double x, double y, Board board) {
    this.x = x;
    this.y = y;
    this.board = board;
  }

  @Override
  public abstract void update();

  @Override
  public abstract void render(Screen screen);

  /**
   * Tính toán hướng đi của nhân vật.
   */
  protected abstract void calculateMove();

  protected abstract void move(double xa, double ya);

  /**
   * Phương thức được gọi khi đối tượng bị tiêu diệt.
   */
  public abstract void kill();

  /**
   * Xử lý hiệu ứng khi đối tượng bị tiêu diệt.
   */
  protected abstract void afterKilled();

  /**
   * Kiểm tra xem đối tượng có thể
   * di chuyển tới vị trí đã tính toán hay không.
   */
  protected abstract boolean canMove(double x, double y);

  protected double getXNotification() {
    return (x * Game.SCALE) + (sprite.SIZE / 2 * Game.SCALE);
  }

  protected double getYNotification() {
    return (y* Game.SCALE) - (sprite.SIZE / 2 * Game.SCALE);
  }


}
