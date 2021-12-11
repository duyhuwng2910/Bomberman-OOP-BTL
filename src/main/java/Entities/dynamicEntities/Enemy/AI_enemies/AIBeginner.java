package main.java.Entities.dynamicEntities.Enemy.AI_enemies;

/**
 * Class AI phiên bản đơn giản.
 */
public class AIBeginner extends AI {
	@Override
	public int calculateDirection() {
		return random.nextInt(4);
	}
}
