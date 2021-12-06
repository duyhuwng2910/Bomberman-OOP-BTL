package main.java.Entities.dynamicEntities.Enemies;

import main.java.Board;
import main.java.Entities.dynamicEntities.Enemies.AI_enemies.AIIntermediate;
import main.java.Game;
import main.java.Graphics.Sprite;

public class Kondoria extends Enemy {
  public Kondoria(int x, int y, Board board) {
    super(x, y, board, Sprite.kondoria_dead, Game.getPlayerSpeed() / 4, 1000);
    sprite = Sprite.kondoria_right1;
    ai = new AIIntermediate(board.getPlayer(), this);
    direction  = ai.calculateDirection();
  }

  @Override
  protected void chooseSprite() {
    switch(this.direction) {
      case 0:
      case 1:
        if (isMoving) {
          sprite = Sprite.movingSprite(Sprite.kondoria_right1, Sprite.kondoria_right2, Sprite.kondoria_right3, animated, 60);
        } else {
          sprite = Sprite.kondoria_left1;
        }
        break;
      case 2:
      case 3:
        if (isMoving) {
          sprite = Sprite.movingSprite(Sprite.kondoria_left1, Sprite.kondoria_left2, Sprite.kondoria_left3, animated, 60);
        } else {
          sprite = Sprite.kondoria_left1;
        }
        break;
    }
  }
}
