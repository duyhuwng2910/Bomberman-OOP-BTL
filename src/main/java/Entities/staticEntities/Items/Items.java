package main.java.Entities.staticEntities.Items;

import main.java.Entities.staticEntities.Tile;
import main.java.Graphics.Sprite;

public abstract class Items extends Tile {
  protected int duration = -1;
  protected boolean active = false;
  protected int level;

  public Items(double x, double y, int level, Sprite sprite) {
    super(x, y, sprite);
    this.level = level;
  }

  public abstract void setValues();

  public boolean isActive() {
    return active;
  }

  public int getLevel() {
    return level;
  }
}
