package main.java.Entities.dynamicEntities.Enemy.AI_enemies;

import java.util.Random;

/**
 * Class trừu tượng của AI dành cho việc
 * di chuyển của Enemy.
 */
public abstract class AI {
	protected Random random = new Random();
	
	public abstract int calculateDirection();
}
