package main.java.Entities.staticEntities;

import java.io.IOException;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import main.java.Board;
import main.java.Entities.Entity;
import main.java.Entities.dynamicEntities.Bomber;
import main.java.Graphics.Sprite;
import main.java.Sound.Sound;

public class Portal extends Tile {
  protected Board board;
  public Portal(int x, int y, Board board, Sprite sprite) {
    super(x, y, sprite);
    this.board = board;
  }

  /**
   * xử lý khi Entities đi vào cổng portal.
   * @return true cho đi qua, false không cho đi qua.
   */
  @Override
  public boolean collided(Entity entity)
      throws UnsupportedAudioFileException, LineUnavailableException, IOException {
    // xử lý khi Bomber đi vào
    if (entity instanceof Bomber) {
      if (!this.board.detectNoEnemies()) {
        return false;
      }
      if (entity.getXTile() == getX() && entity.getYTile() == getY()) {
        if (this.board.detectNoEnemies()) {
          this.board.nextLevel();
          Sound.play("crystup");
        }
      }
      return true;
    }
    return true;
  }
}
