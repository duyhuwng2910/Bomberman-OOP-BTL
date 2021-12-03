package main.java.Entities.dynamicEntities.Enemies.AI_enemies;

import java.util.Random;

public abstract class AI {
  protected Random random = new Random();

  public abstract int calculateDirection();
}
