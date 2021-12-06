package main.java.Entities.dynamicEntities.Enemies;

import main.java.Board;
import main.java.Entities.dynamicEntities.Enemies.AI_enemies.AIIntermediate;
import main.java.Game;
import main.java.Graphics.Sprite;

public class Minvo extends Enemy {
  public Minvo(int x, int y, Board board) {
    super(x, y, board, Sprite.minvo_dead, Game.getPlayerSpeed() * 2, 800);
    sprite = Sprite.minvo_right1;
    ai = new AIIntermediate(board.getPlayer(), this);
    direction  = ai.calculateDirection();
  }

  @Override
  protected void chooseSprite() {
    switch(this.direction) {
      case 0:
      case 1:
        if (isMoving) {
          sprite = Sprite.movingSprite(Sprite.minvo_right1, Sprite.minvo_right2, Sprite.minvo_right3, animated, 60);
        } else {
          sprite = Sprite.minvo_left1;
        }
        break;
      case 2:
      case 3:
        if (isMoving) {
          sprite = Sprite.movingSprite(Sprite.minvo_left1, Sprite.minvo_left2, Sprite.minvo_left3, animated, 60);
        } else {
          sprite = Sprite.minvo_left1;
        }
        break;
    }
  }
}
