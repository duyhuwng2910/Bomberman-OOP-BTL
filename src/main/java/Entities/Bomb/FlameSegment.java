package main.java.Entities.Bomb;

import main.java.Board;
import main.java.Entities.Entity;
import main.java.Entities.dynamicEntities.Bomber;
import main.java.Entities.dynamicEntities.Character;
import main.java.Entities.dynamicEntities.Enemies.Enemy;
import main.java.Graphics.Screen;
import main.java.Graphics.Sprite;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.IOException;

public class FlameSegment extends Entity {
  protected boolean last = false;
  protected Board board;
  protected Sprite sprite1, sprite2;

  /**
   * @param last cho biết segment này là cuối cùng của Flame hay không,
   * segment cuối có sprite khác so với các segment còn lại
   */
  public FlameSegment(int x, int y, int direction, boolean last, Board board) {
    this.x = x;
    this.y = y;
    this.last = last;
    this.board = board;

    switch (direction) {
      case 0:
        if (!last) {
          sprite = Sprite.explosion_vertical2;
        } else {
          sprite = Sprite.explosion_vertical_top_last2;
        }
        break;
      case 1:
        if (!last) {
          sprite = Sprite.explosion_horizontal2;
        } else {
          sprite = Sprite.explosion_horizontal_right_last2;
        }
        break;
      case 2:
        if (!last) {
          sprite = Sprite.explosion_vertical2;
        } else {
          sprite = Sprite.explosion_vertical_down_last2;
        }
        break;
      case 3:
        if (!last) {
          sprite = Sprite.explosion_horizontal2;
        } else {
          sprite = Sprite.explosion_horizontal_left_last2;
        }
        break;
    }
  }

  @Override
  public void render(Screen screen) {
    int xt = (int) this.x << 4;
    int yt = (int) this.y << 4;
    screen.renderEntity(xt, yt , this);
  }

  @Override
  public void update() {}

  @Override
  public boolean collided(Entity entity) throws UnsupportedAudioFileException, LineUnavailableException, IOException {
    // TODO: xử lý khi FlameSegment va chạm với Character
    if (entity instanceof Character) {
      ((Character) entity).kill();
    }
    return true;
  }
}
