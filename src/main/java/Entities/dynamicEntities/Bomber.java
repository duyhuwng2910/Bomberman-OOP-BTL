package main.java.Entities.dynamicEntities;

import java.awt.Color;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import main.java.Board;
import main.java.Game;
import main.java.Entities.Entity;
import main.java.Entities.Notification;
import main.java.Entities.Bomb.Bomb;
import main.java.Entities.Bomb.Flame;
import main.java.Entities.dynamicEntities.Enemy.Enemy;
import main.java.Entities.staticEntities.Items.Items;
import main.java.Graphics.Screen;
import main.java.Graphics.Sprite;
import main.java.Input.Keyboard;
import main.java.Level.Coordinates;
import main.java.Sound.Sound;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

public class Bomber extends Character {
	
	private List<Bomb> bombList;
	protected Keyboard keyboard;
	
	protected int timeBetweenPutBombs = 0;
	
	public static List<Items> itemsList = new ArrayList<>();
	
	
	public Bomber(int x, int y, Board board) {
		super(x, y, board);
		bombList = this.board.getBombs();
		keyboard = this.board.getInput();
		sprite = Sprite.player_right;
	}

	@Override
	public void update() throws UnsupportedAudioFileException, LineUnavailableException, IOException {
		clearBombs();
		if(!isAlive) {
			afterKill();
			return;
		}
		
		if(timeBetweenPutBombs < -7500) {
			timeBetweenPutBombs = 0;
		} else {
			timeBetweenPutBombs--;
		}
		
		animate();
		calculateMove();
		detectPlaceBomb();
	}
	
	@Override
	public void render(Screen screen) {
		calculateXOffset();
		
		if(isAlive)
			chooseSprite();
		else
			sprite = Sprite.player_dead1;
		
		screen.renderEntity((int) x, (int) y - sprite.SIZE, this);
	}
	
	public void calculateXOffset() {
		int xScroll = Screen.calculateXOffset(board, this);
		Screen.setOffset(xScroll, 0);
	}


	/*
  	*Cac phuong thuc cua qua trinh dat bomb
   	*/
	private void detectPlaceBomb() throws UnsupportedAudioFileException, LineUnavailableException, IOException {
		if(keyboard.space && Game.getBombRate() > 0 && timeBetweenPutBombs < 0) {
			
			int xt = Coordinates.pixelToTile(x + sprite.getSize() / 2);
			int yt = Coordinates.pixelToTile( (y + sprite.getSize() / 2) - sprite.getSize() );
			
			placeBomb(xt,yt);
			Game.addBombRate(-1);
			
			timeBetweenPutBombs = 30;
		}
	}
	
	protected void placeBomb(int x, int y) throws UnsupportedAudioFileException, LineUnavailableException, IOException {
		Bomb b = new Bomb(x, y, board);
		board.addBomb(b);
		Sound.play("bomset");
	}
	
	private void clearBombs() {
		Iterator<Bomb> bs = bombList.iterator();
		
		Bomb b;
		while(bs.hasNext()) {
			b = bs.next();
			if(b.isRemoved())  {
				bs.remove();
				Game.addBombRate(1);
			}
		}
		
	}
	
	/*
	*Cac ham xu ly va cham va bi tieu diet cua Bomber.
	 */
	@Override
	public void kill() throws UnsupportedAudioFileException, LineUnavailableException, IOException {
		if(!isAlive) return;
		
		isAlive = false;
		
		board.addLives(-1);

		Notification msg = new Notification("-1 LIVE", getXMessage(), getYMessage(), 2, Color.white, 14);
		board.addMessage(msg);
		Sound.play("endgame");
	}
	
	@Override
	protected void afterKill() {
		if(timeAfter > 0) --timeAfter;
		else {
			if(bombList.size() == 0) {
				
				if(board.getLives() == 0)
					board.endGame();
				else
					board.restartLevel();
			}
		}
	}
	
	/*
	*Cac ham thuc hien qua trinh di chuyen cua Bomber
	 */
	@Override
	protected void calculateMove() throws UnsupportedAudioFileException, LineUnavailableException, IOException {
		int xa = 0, ya = 0;
		if(keyboard.up) {
			ya--;
		}
		if(keyboard.down) {
			ya++;
		}
		if(keyboard.left) {
			xa--;
		}
		if(keyboard.right) {
			xa++;
		}
		
		if(xa != 0 || ya != 0)  {
			move(xa * Game.getPlayerSpeed(), ya * Game.getPlayerSpeed());
			isMoving = true;
		} else {
			isMoving = false;
		}
		
	}
	
	@Override
	public boolean canMove(double x, double y) throws UnsupportedAudioFileException, LineUnavailableException, IOException {
		for (int c = 0; c < 4; c++) {
			double xt = ((this.x + x) + c % 2 * 9) / Game.TILES_SIZE;
			double yt = ((this.y + y) + c / 2 * 10 - 13) / Game.TILES_SIZE;
			
			Entity a = board.getEntity(xt, yt, this);
			
			if(!a.collide(this))
				return false;
		}
		
		return true;
	}

	@Override
	public void move(double xa, double ya) throws UnsupportedAudioFileException, LineUnavailableException, IOException {
		if(xa > 0) direction = 1;
		if(xa < 0) direction = 3;
		if(ya > 0) direction = 2;
		if(ya < 0) direction = 0;
		
		if(canMove(0, ya)) {
			y += ya;
		}
		
		if(canMove(xa, 0)) {
			x += xa;
		}
	}
	
	@Override
	public boolean collide(Entity entity) throws UnsupportedAudioFileException, LineUnavailableException, IOException {
		if(entity instanceof Flame) {
			kill();
			return false;
		}
		
		if(entity instanceof Enemy) {
			kill();
			return true;
		}
		
		return true;
	}
	
	/*
	|--------------------------------------------------------------------------
	| Cac ham thuc hien qua trinh thu nhan item cua Bomber
	|--------------------------------------------------------------------------
	 */
	public void addPowerup(Items p) {
		if(p.isRemoved()) return;
		
		itemsList.add(p);
		
		p.setValues();
	}

	private void chooseSprite() {
		switch(direction) {
		case 0:
			sprite = Sprite.player_up;
			if(isMoving) {
				sprite = Sprite.movingSprite(Sprite.player_up_1, Sprite.player_up_2, animate, 20);
			}
			break;
			case 2:
			sprite = Sprite.player_down;
			if(isMoving) {
				sprite = Sprite.movingSprite(Sprite.player_down_1, Sprite.player_down_2, animate, 20);
			}
			break;
		case 3:
			sprite = Sprite.player_left;
			if(isMoving) {
				sprite = Sprite.movingSprite(Sprite.player_left_1, Sprite.player_left_2, animate, 20);
			}
			break;
			case 1:
			default:
			sprite = Sprite.player_right;
			if(isMoving) {
				sprite = Sprite.movingSprite(Sprite.player_right_1, Sprite.player_right_2, animate, 20);
			}
			break;
		}
	}
}
