package main.java.Entities.bomb;

import main.java.Board;
import main.java.Entities.Entity;
import main.java.Graphics.Screen;

public class Flame extends Entity {

  public Flame(int x, int y, int bombRadius, Board board) {

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

  public FlameSegment flameSegmentAt(int x, int y) {}
}
