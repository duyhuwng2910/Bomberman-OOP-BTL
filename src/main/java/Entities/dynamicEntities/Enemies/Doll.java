package main.java.Entities.dynamicEntities.Enemies;

import main.java.Board;
import main.java.Entities.dynamicEntities.Enemies.AI_enemies.AIIntermediate;
import main.java.Graphics.Sprite;

public class Doll extends Enemy {
  public Doll(int x, int y, Board board) {
    super(x, y, board, Sprite.balloom_dead, 0.8, 100);

    this.sprite = Sprite.balloom_left1;
    this.ai = new AIIntermediate(board.getBomber(), this);
    this.direction = this.ai.calculateDirection();

  }

  @Override
  protected void chooseSprite() {
    switch (this.direction) {
      case 0:
      case 1:
        if (isMoving) {
          this.sprite = Sprite.movingSprite(Sprite.doll_right1, Sprite.doll_right2, Sprite.doll_right3, animated, 60);
        } else {
          this.sprite = Sprite.doll_left1;
        }
        break;
      case 2:
      case 3:
        if (isMoving) {
          this.sprite = Sprite.movingSprite(Sprite.doll_left1, Sprite.doll_left2, Sprite.doll_left3, animated, 60);
        } else {
          this.sprite = Sprite.doll_left1;
        }
        break;
    }
  }
}
