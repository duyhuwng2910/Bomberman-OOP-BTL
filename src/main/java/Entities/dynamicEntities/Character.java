package main.java.Entities.dynamicEntities;

import main.java.Board;
import main.java.Game;
import main.java.Entities.AnimatedEntitiy;
import main.java.Graphics.Screen;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.IOException;

/**
 * Class trừu tượng Character dùng để quản lý
 * các sự kiện của các nhân vật có chuyển động
 * hoạt hình bao gồm Bomber và Enemy.
 */
public abstract class Character extends AnimatedEntitiy {
	protected Board board;
	protected int direction = -1;
	protected boolean isAlive = true;
	protected boolean isMoving = false;
	public int timeAfter = 80;
	
	public Character(int x, int y, Board board) {
		this.x = x;
		this.y = y;
		this.board = board;
	}
	
	@Override
	public abstract void update() throws UnsupportedAudioFileException, LineUnavailableException, IOException;
	
	@Override
	public abstract void render(Screen screen);

	/**
	 * Tính toán hướng đi của nhân vật.
	 */
	protected abstract void calculateMove() throws UnsupportedAudioFileException, LineUnavailableException, IOException;
	
	protected abstract void move(double xa, double ya) throws UnsupportedAudioFileException, LineUnavailableException, IOException;

	/**
	 * Phương thức được gọi khi đối tượng bị tiêu diệt.
	 */
	public abstract void kill() throws UnsupportedAudioFileException, LineUnavailableException, IOException;

	/**
	 * Xử lý hiệu ứng khi đối tượng bị tiêu diệt.
	 */
	protected abstract void afterKill();

	/**
	 * Kiểm tra xem đối tượng có thể
	 * di chuyển tới vị trí đã tính toán hay không.
	 */
	protected abstract boolean canMove(double x, double y) throws UnsupportedAudioFileException, LineUnavailableException, IOException;
	
	protected double getXNotification() {
		return (x * Game.SCALE) + (sprite.SIZE / 2 * Game.SCALE);
	}
	
	protected double getYNotification() {
		return (y * Game.SCALE) - (sprite.SIZE / 2 * Game.SCALE);
	}
	
}
