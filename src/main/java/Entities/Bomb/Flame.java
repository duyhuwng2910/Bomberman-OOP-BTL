package main.java.Entities.Bomb;

import main.java.Board;
import main.java.Entities.Entity;
import main.java.Entities.dynamicEntities.Bomber;
import main.java.Entities.dynamicEntities.Character;
import main.java.Entities.dynamicEntities.Enemy.Enemy;
import main.java.Graphics.Screen;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.IOException;

/**
 * Class hiệu ứng Flame khi bom nổ.
 */
public class Flame extends Entity {
	protected Board board;
	protected int direction;
	private int radius;
	protected int xOrigin, yOrigin;
	protected FlameSegment[] flameSegments;
	
	public Flame(int x, int y, int direction, int radius, Board board) throws UnsupportedAudioFileException, LineUnavailableException, IOException {
		xOrigin = x;
		yOrigin = y;
		this.x = x;
		this.y = y;
		this.direction = direction;
		this.radius = radius;
		this.board = board;
		flameSegments = new FlameSegment[calculatePermittedDistance()]; //tính toán độ dài Flame, tương ứng với số lượng segment.
		createFlameSegments();
	}

	/**
	 * Phương thức tạo các FlameSegment,
	 * mỗi segment ứng một đơn vị độ dài.
	 */
	private void createFlameSegments() {
		boolean last = false; //biến last dùng để đánh dấu cho segment cuối cùng
		int x = (int) this.x;
		int y = (int) this.y;
		for (int i = 0; i < flameSegments.length; i++) {
			last = i == flameSegments.length - 1;
			
			switch (direction) {
				case 0:
					y--;
					break;
				case 1:
					x++;
					break;
				case 2:
					y++;
					break;
				case 3:
					x--;
					break;
				default:
					break;
			}
			flameSegments[i] = new FlameSegment(x, y, direction, last, board);
		}
	}

	/**
	 * Phương thức dùng để tính toán độ dài của Flame,
	 * nếu gặp vật cản là Brick/Wall thì độ dài sẽ bị cắt ngắn.
	 */
	private int calculatePermittedDistance() throws UnsupportedAudioFileException, LineUnavailableException, IOException {
		int radius = 0;
		int x = (int) this.x;
		int y = (int) this.y;

		while (radius < this.radius) {
			if (direction == 0) {
				y--;
			}
			if (direction == 1) {
				x++;
			}
			if (direction == 2) {
				y++;
			}
			if (direction == 3) {
				x--;
			}
			Entity entity = board.getEntity(x, y, null);
			
			if (entity instanceof Character) {
				++radius;
			}
			
			if (!entity.collided(this)) {
				break;
			}
			++radius;
		}
		return radius;
	}
	
	public FlameSegment flameSegmentAt(int x, int y) {
		for (int i = 0; i < flameSegments.length; i++) {
			if (flameSegments[i].getX() == x && flameSegments[i].getY() == y) {
				return flameSegments[i];
			}
		}
		return null;
	}

	@Override
	public void update() {}
	
	@Override
	public void render(Screen screen) {
		for (int i = 0; i < flameSegments.length; i++) {
			flameSegments[i].render(screen);
		}
	}

	@Override
	public boolean collided(Entity entity) throws UnsupportedAudioFileException, LineUnavailableException, IOException {
		// xử lý va chạm với Bomber, Enemy. Chú ý đối tượng này có vị trí chính là vị trí của Bomb đã nổ
		if (entity instanceof Bomber) {
			((Bomber) entity).kill();
		}
		if (entity instanceof Enemy) {
			((Enemy) entity).kill();
		}
		return true;
	}
}
