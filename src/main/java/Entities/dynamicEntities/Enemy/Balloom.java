package main.java.Entities.dynamicEntities.Enemy;

import main.java.Board;
import main.java.Entities.dynamicEntities.Enemy.AI_enemies.AIBeginner;
import main.java.Graphics.Sprite;

/**
 * Class đại diện cho enemy Balloom.
 */
public class Balloom extends Enemy {
	public Balloom(int x, int y, Board board) {
		super(x, y, board, Sprite.balloom_dead, 0.5, 50);
		sprite = Sprite.balloom_left1;
		ai = new AIBeginner();
		direction = ai.calculateDirection();
	}

	@Override
	protected void chooseSprite() {
		switch (direction) {
			case 0:
			case 1:
					sprite = Sprite.movingSprite(Sprite.balloom_right1, Sprite.balloom_right2, Sprite.balloom_right3, animate, 60);
				break;
			case 2:
			case 3:
					sprite = Sprite.movingSprite(Sprite.balloom_left1, Sprite.balloom_left2, Sprite.balloom_left3, animate, 60);
				break;
		}
	}
}
