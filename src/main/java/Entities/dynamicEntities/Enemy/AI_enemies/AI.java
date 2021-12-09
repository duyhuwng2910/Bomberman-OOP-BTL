package main.java.Entities.dynamicEntities.Enemy.AI_enemies;

import java.util.Random;

public abstract class AI {
	
	protected Random random = new Random();
	
	public abstract int calculateDirection();
}
