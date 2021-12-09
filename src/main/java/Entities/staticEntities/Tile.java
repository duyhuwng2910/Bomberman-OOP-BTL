package main.java.Entities.staticEntities;

import main.java.Entities.Entity;
import main.java.Graphics.Screen;
import main.java.Graphics.Sprite;
import main.java.Level.Coordinates;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.IOException;

/**
 * Những entity cố định.
 */
public abstract class Tile extends Entity {
	public Tile(int x, int y, Sprite sprite) {
		this.x = x;
		this.y = y;
		this.sprite = sprite;
	}
	
	@Override
	public boolean collide(Entity entity) throws UnsupportedAudioFileException, LineUnavailableException, IOException {
		return false;
	}
	
	@Override
	public void render(Screen screen) {
		screen.renderEntity( Coordinates.tileToPixel(x), Coordinates.tileToPixel(y), this);
	}
	
	@Override
	public void update() {}
}
