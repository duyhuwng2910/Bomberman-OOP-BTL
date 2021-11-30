package main.java.Entities.dynamicEntities.Enemies;

import javafx.scene.image.Image;
import main.java.Board;
import main.java.Entities.Entity;
import main.java.Graphics.Sprite;

public class Balloom extends Enemy {
  public Balloom(int x, int y, Board board) {
    super(x, y, board, Sprite.balloom_dead, 0.5, 100);
  }

  @Override
  protected void afterKilled() {

  }

  @Override
  protected void chooseSprite() {

  }
}
