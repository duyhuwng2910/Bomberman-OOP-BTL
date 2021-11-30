package main.java.Entities.dynamicEntities.Enemies;

import javafx.scene.image.Image;
import main.java.Board;
import main.java.Entities.Entity;
import main.java.Graphics.Sprite;

public class Oneal extends Enemy {
  public Oneal(int x, int y, Board board) {
    super(x, y, board, Sprite.oneal_dead, 0.75, 150);
    this.sprite = Sprite.balloom_right1;
  }

  @Override
  protected void afterKilled() {

  }

  @Override
  protected void chooseSprite() {

  }
}
