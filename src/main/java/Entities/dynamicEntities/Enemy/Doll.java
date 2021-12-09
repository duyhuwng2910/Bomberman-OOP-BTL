package main.java.Entities.dynamicEntities.Enemy;


import main.java.Board;
import main.java.Entities.dynamicEntities.Enemy.AI_enemies.AIBeginner;
import main.java.Graphics.Sprite;

public class Doll extends Enemy {

	public Doll(int x, int y, Board board) {
		super(x, y, board, Sprite.doll_dead, 0.8, 100);
		sprite = Sprite.doll_right1;
		ai = new AIBeginner();
		direction = ai.calculateDirection();
	}

	@Override
	protected void chooseSprite() {
		switch(direction) {
			case 0:
			case 1:
				if(isMoving)
					sprite = Sprite.movingSprite(Sprite.doll_right1, Sprite.doll_right2, Sprite.doll_right3, animate, 60);
				else
					sprite = Sprite.doll_left1;
				break;
			case 2:
			case 3:
				if(isMoving)
					sprite = Sprite.movingSprite(Sprite.doll_left1, Sprite.doll_left2, Sprite.doll_left3, animate, 60);
				else
					sprite = Sprite.doll_left1;
				break;
		}
	}
}
