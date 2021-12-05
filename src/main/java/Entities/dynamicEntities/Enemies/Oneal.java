package main.java.Entities.dynamicEntities.Enemies;

import main.java.Board;
import main.java.Entities.dynamicEntities.Enemies.AI_enemies.AIBeginner;
import main.java.Graphics.Sprite;

public class Oneal extends Enemy {
  public Oneal(int x, int y, Board board) {
    super(x, y, board, Sprite.oneal_dead, 0.75, 80);
    this.sprite = Sprite.balloom_right1;
    this.ai = new AIBeginner();
    this.direction = this.ai.calculateDirection();
  }

  @Override
  protected void chooseSprite() {
    switch(this.direction) {
      case 0:
      case 1:
        if (isMoving) {
          this.sprite = Sprite.movingSprite(Sprite.oneal_right1, Sprite.oneal_right2, Sprite.oneal_right3, animated, 60);
        } else {
          this.sprite = Sprite.oneal_left1;
        }
        break;
      case 2:
      case 3:
        if (isMoving) {
          this.sprite = Sprite.movingSprite(Sprite.oneal_left1, Sprite.oneal_left2, Sprite.oneal_left3, animated, 60);
        } else {
          this.sprite = Sprite.oneal_left1;
        }
        break;
    }
  }
}
