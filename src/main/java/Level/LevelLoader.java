package main.java.Level;

import main.java.Board;
import main.java.Exception.LoadLevelException;

/**
 * Class dùng để load và lưu trữ
 * thông tin bản đồ của các màn chơi.
 */
public abstract class LevelLoader {
  protected int width, height, level;
  protected Board board;

  public LevelLoader(Board board, int level) throws LoadLevelException {
    this.board = board;
    loadLevel(level);
  }

  public abstract void loadLevel(int level) throws LoadLevelException;

  public abstract void createEntities();

  public int getWidth() {
    return width;
  }

  public int getHeight() {
    return height;
  }

  public int getLevel() {
    return level;
  }
}
