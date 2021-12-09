package main.java.Entities;

import main.java.Graphics.IRender;
import main.java.Graphics.Screen;
import main.java.Graphics.Sprite;
import main.java.Level.Coordinates;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.IOException;

public abstract class Entity implements IRender {

	protected double x, y;
	protected boolean _removed = false;
	protected Sprite sprite;
	
	@Override
	public abstract void update() throws UnsupportedAudioFileException, LineUnavailableException, IOException;
	
	@Override
	public abstract void render(Screen screen);
	
	public void remove() {
		_removed = true;
	}
	
	public boolean isRemoved() {
		return _removed;
	}
	
	public Sprite getSprite() {
		return sprite;
	}
	
	public abstract boolean collide(Entity e) throws UnsupportedAudioFileException, LineUnavailableException, IOException;
	
	public double getX() {
		return x;
	}
	
	public double getY() {
		return y;
	}
	
	public int getXTile() {
		return Coordinates.pixelToTile(x + sprite.SIZE / 2); //plus half block for precision
	}
	
	public int getYTile() {
		return Coordinates.pixelToTile(y - sprite.SIZE / 2); //plus half block
	}
}
