package main.java.Entities.dynamicEntities.Enemies;

import main.java.Board;
import main.java.Entities.dynamicEntities.Enemies.AI_enemies.AIBeginner;
import main.java.Graphics.Sprite;

public class Balloom extends Enemy {
  public Balloom(int x, int y, Board board) {
    super(x, y, board, Sprite.balloom_dead, 0.5, 50);
    this.sprite = Sprite.balloom_left1;
    this.ai = new AIBeginner();
    this.direction = this.ai.calculateDirection();
  }

  @Override
  protected void chooseSprite() {
    switch(this.direction) {
      case 0:
      case 1:
        this.sprite = Sprite.movingSprite(Sprite.balloom_right1, Sprite.balloom_right2, Sprite.balloom_right3, animated, 60);
        break;
      case 2:
      case 3:
        this.sprite = Sprite.movingSprite(Sprite.balloom_left1, Sprite.balloom_left2, Sprite.balloom_left3, animated, 60);
        break;
    }
  }
}
