package main.java.Entities.staticEntities;

import main.java.Board;
import main.java.Entities.Entity;
import main.java.Graphics.Screen;
import main.java.Graphics.Sprite;

public class Portal extends Tile {
  protected Board board;
  public Portal(int x, int y, Board board, Sprite sprite) {
    super(x, y, sprite);
    this.board = board;
  }

  @Override
  public void render(Screen screen) {

  }

  @Override
  public void update() {

  }

  @Override
  public boolean collided(Entity entity) {
    return false;
  }
}
