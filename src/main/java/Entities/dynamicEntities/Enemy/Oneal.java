package main.java.Entities.dynamicEntities.Enemy;

import main.java.Board;
import main.java.Game;
import main.java.Entities.dynamicEntities.Enemy.AI_enemies.AIIntermediate;
import main.java.Graphics.Sprite;

/**
 * Class đại diện cho enemy Oneal.
 */
public class Oneal extends Enemy {
	public Oneal(int x, int y, Board board) {
		super(x, y, board, Sprite.oneal_dead, Game.getPlayerSpeed(), 200);
		sprite = Sprite.oneal_left1;
		ai = new AIIntermediate(this.board.getBomber(), this);
		direction = ai.calculateDirection();
	}

	@Override
	protected void chooseSprite() {
		switch (direction) {
			case 0:
			case 1:
        if (isMoving) {
          sprite =
              Sprite.movingSprite(
                  Sprite.oneal_right1, Sprite.oneal_right2, Sprite.oneal_right3, animate, 60);
        } else {
          sprite = Sprite.oneal_left1;
				} break;
			case 2:
			case 3:
        if (isMoving) {
          sprite =
              Sprite.movingSprite(
                  Sprite.oneal_left1, Sprite.oneal_left2, Sprite.oneal_left3, animate, 60);
        } else {
          sprite = Sprite.oneal_left1;
				}
				break;
		}
	}
}
