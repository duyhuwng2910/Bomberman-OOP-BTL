package main.java.Entities.Bomb;

import main.java.Board;
import main.java.Game;
import main.java.Entities.AnimatedEntitiy;
import main.java.Entities.Entity;
import main.java.Entities.dynamicEntities.Character;
import main.java.Entities.dynamicEntities.Bomber;
import main.java.Graphics.Screen;
import main.java.Graphics.Sprite;
import main.java.Level.Coordinates;
import main.java.Sound.Sound;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.IOException;

/**
 * Class của thực thể Bomb.
 */
public class Bomb extends AnimatedEntitiy {
	protected double timeToExplode = 150; //2.5 giây - thời gian phát nổ
	public int timeAfter = 30; //thời gian sau khi bom nổ
	protected Board board;
	protected boolean allowedToPass = true;
	protected Flame[] flames = null;
	protected boolean exploded = false;
	
	public Bomb(int x, int y,Board board) {
		this.x = x;
		this.y = y;
		this.board = board;
		this.sprite = Sprite.bomb;
	}
	
	@Override
	public void update() throws UnsupportedAudioFileException, LineUnavailableException, IOException {
		if(timeToExplode > 0)
			timeToExplode--;
		else {
      if (!exploded) {
        explosion();
      } else {
        updateFlames();
			}

      if (timeAfter > 0) {
        timeAfter--;
      } else {
        remove();
			}
		}
		animate();
	}
	
	@Override
	public void render(Screen screen) {
		if (exploded) {
			sprite =  Sprite.bomb_exploded2;
			renderFlames(screen);
    } else {
      sprite = Sprite.movingSprite(Sprite.bomb, Sprite.bomb_1, Sprite.bomb_2, animate, 60);
		}
		int xt = (int) x << 4;
		int yt = (int) y << 4;
		screen.renderEntity(xt, yt , this);
	}
	
	public void renderFlames(Screen screen) {
		for (Flame flame : flames) {
			flame.render(screen);
		}
	}
	
	public void updateFlames() {
		for (int i = 0; i < flames.length; i++) {
			flames[i].update();
		}
	}
	
	public void explodedTime() {
		timeToExplode = 0;
	}

	/**
	 * Phương thức xử lý sự kiện Bomb nổ.
	 */
	protected void explosion() throws UnsupportedAudioFileException, LineUnavailableException, IOException {
		allowedToPass = true;
		exploded = true;
		// Tiến hành xử lý sự kiện khi Character
		// đứng trong khu vực chịu ảnh hưởng của Bomb
		Character a = board.getCharacterAt(x, y);

		if (a != null)  {
			a.kill();
		}
		flames = new Flame[4];
		
		for (int i = 0; i < flames.length; i++) {
			flames[i] = new Flame((int) x, (int) y, i, Game.getBombRadius(), board);
		}
		// Hiệu ứng âm thanh Bomb nổ
		Sound.play("bom");
	}

	@Override
	public boolean collided(Entity e) {
		// Xử lý khi Bomber đi ra khỏi khu vực đặt bomb
		if (e instanceof Bomber) {
			double diffX = e.getX() - Coordinates.tileToPixel(getX());
			double diffY = e.getY() - Coordinates.tileToPixel(getY());
			
			if (!(diffX >= -10 && diffX < 16 && diffY >= 1 && diffY <= 28)) { // differences to see if the player has moved out of the bomb, tested values
				allowedToPass = false;
			}
			return allowedToPass;
		}
		// Xử lý va chạm với hiệu ứng flame của Bomb khác
		if (e instanceof Flame) {
			explodedTime();
			return true;
		}
		return false;
	}

	public FlameSegment flameAt(int x, int y) {
		if (!exploded) {
			return null;
		}

		for (int i = 0; i < flames.length; i++) {
			if (flames[i] == null) {
				return null;
			}
			FlameSegment fs = flames[i].flameSegmentAt(x, y);

			if (fs != null) {
				return fs;
			}
		}
		return null;
	}
}
