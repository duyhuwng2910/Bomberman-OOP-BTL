package main.java.Entities.bomb;

import main.java.Board;
import main.java.Entities.Entity;
import main.java.Entities.dynamicEntities.Bomber;
import main.java.Entities.dynamicEntities.Enemies.Enemy;
import main.java.Graphics.Screen;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.IOException;

public class Flame extends Entity {
  protected Board board;
  protected int direction;
  private int radius;
  protected int xOrigin, yOrigin;
  protected FlameSegment[] flameSegments = new FlameSegment[0];

  public Flame(int x, int y, int direction, int radius, Board board) throws UnsupportedAudioFileException, LineUnavailableException, IOException {
    this.xOrigin = x;
    this.yOrigin = y;
    this.x = x;
    this.y = y;
    this.direction = direction;
    this.radius = radius;
    this.board = board;
    createFlameSegments();
  }

  /**
   * Phương thức tạo các FlameSegment,
   * mỗi segment ứng một đơn vị độ dài.
   */
  private void createFlameSegments() throws UnsupportedAudioFileException, LineUnavailableException, IOException {
    /**
     * tính toán độ dài Flame, tương ứng với số lượng segment.
     */
    flameSegments = new FlameSegment[calculatePermittedDistance()];

    /**
     * biến last dùng để đánh dấu cho segment cuối cùng
     */
    boolean last = false;

    int x = (int) this.x;
    int y = (int) this.y;
    for (int i = 0; i < flameSegments.length; i++) {
      last = i == flameSegments.length -1 ? true : false;

      switch (this.direction) {
        case 0:
          y--;
          break;
        case 1:
          x++;
          break;
        case 2:
          y++;
          break;
        case 3:
          x--;
          break;
      }
      flameSegments[i] = new FlameSegment(x, y, direction, last);
    }
  }

  /**
   * Phương thức dùng để tính toán độ dài của Flame,
   * nếu gặp vật cản là Brick/Wall thì độ dài sẽ bị cắt ngắn.
   */
  private int calculatePermittedDistance() throws UnsupportedAudioFileException, LineUnavailableException, IOException {
    int radius = 0;
    int x = (int) this.x;
    int y = (int) this.y;
    while (radius < this.radius) {
      if (direction == 0) y--;
      if (direction == 1) x++;
      if (direction == 2) y++;
      if (direction == 3) x--;

      Entity entity = board.getEntity(x, y, null);

      if (entity instanceof Bomb) {
        ++radius; //explosion has to be below the bom
      }
      if (entity.collided(this) == false) {
        break;
      }
      ++radius;
    }
    return radius;
  }

  public FlameSegment flameSegmentAt(int x, int y) {
    for (int i = 0; i < flameSegments.length; i++) {
      if (flameSegments[i].getX() == x && flameSegments[i].getY() == y) {
        return flameSegments[i];
      }
    }
    return null;
  }

  @Override
  public void update() {}

  @Override
  public void render(Screen screen) {
    for (int i = 0; i < flameSegments.length; i++) {
      flameSegments[i].render(screen);
    }
  }

  @Override
  public boolean collided(Entity entity) throws UnsupportedAudioFileException, LineUnavailableException, IOException {
    // TODO: xử lý va chạm với Bomber, Enemy. Chú ý đối tượng này có vị trí chính là vị trí của Bomb đã nổ
    if(entity instanceof Bomber) {
      ((Bomber) entity).kill();
    }
    if(entity instanceof Enemy) {
      ((Enemy) entity).kill();
    }
    return true;
  }
}
