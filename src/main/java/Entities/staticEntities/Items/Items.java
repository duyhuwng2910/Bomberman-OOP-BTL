package main.java.Entities.staticEntities.Items;

import main.java.Entities.staticEntities.Tile;
import main.java.Graphics.Sprite;

/**
 * Class trừu tượng đại diện cho các item.
 */
public abstract class Items extends Tile {
	protected boolean active = false;
	protected int level;
	
	public Items(int x, int y, int level, Sprite sprite) {
		super(x, y, sprite);
		this.level = level;
	}
	
	public abstract void setValues();
	public int getLevel() {
		return level;
	}
}
