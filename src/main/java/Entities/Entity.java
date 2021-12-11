package main.java.Entities;

import main.java.Graphics.Render;
import main.java.Graphics.Screen;
import main.java.Graphics.Sprite;
import main.java.Level.Coordinates;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.IOException;

public abstract class Entity implements Render {
	//Tọa độ X tính từ góc trái trên trong Canvas
	protected double x;

	//Tọa độ Y tính từ góc trái trên trong Canvas
	protected double y;

	protected boolean removed = false;

	protected Sprite sprite;

	/**
	 * Phương thức update được gọi liên tục trong vòng lặp game
	 * để xử lý sự kiện và cập nhật trạng thái của toàn bộ Entity.
	 */
	@Override
	public abstract void update() throws UnsupportedAudioFileException, LineUnavailableException, IOException;

	/**
	 * Phương thức render được gọi liên tục trong vòng lặp game
	 * để cập nhật hình ảnh của các entity theo trạng thái.
	 */
	@Override
	public abstract void render(Screen screen);
	
	public void remove() {
		removed = true;
	}
	
	public boolean isRemoved() {
		return removed;
	}
	
	public Sprite getSprite() {
		return sprite;
	}
	
	public abstract boolean collided(Entity e) throws UnsupportedAudioFileException, LineUnavailableException, IOException;
	
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
