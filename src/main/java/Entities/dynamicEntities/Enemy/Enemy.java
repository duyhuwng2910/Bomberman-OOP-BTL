package main.java.Entities.dynamicEntities.Enemy;

import java.awt.Color;
import java.io.IOException;

import main.java.Board;
import main.java.Game;
import main.java.Entities.Entity;
import main.java.Entities.Notification;
import main.java.Entities.Bomb.Flame;
import main.java.Entities.dynamicEntities.Character;
import main.java.Entities.dynamicEntities.Bomber;
import main.java.Entities.dynamicEntities.Enemy.AI_enemies.AI;
import main.java.Graphics.Screen;
import main.java.Graphics.Sprite;
import main.java.Level.Coordinates;
import main.java.Sound.Sound;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

/**
 * Class trừu tượng cho thực thể Enemy.
 */
public abstract class Enemy extends Character {

	protected int points;
	
	protected double speed;
	protected AI ai;

	protected final double MAX_STEPS;
	protected final double rest;
	protected double steps;
	
	protected int finalAnimation = 30;
	protected Sprite deadSprite;
	
	public Enemy(int x, int y, Board board, Sprite dead, double speed, int points) {
		super(x, y, board);
		
		this.points = points;
		this.speed = speed;
		
		MAX_STEPS = Game.TILES_SIZE / this.speed;
		rest = (MAX_STEPS - (int) MAX_STEPS) / MAX_STEPS;
		steps = MAX_STEPS;
		
		timeAfter = 20;
		deadSprite = dead;
	}

	@Override
	public void update() throws UnsupportedAudioFileException, LineUnavailableException, IOException {
		animate();
		
		if(!isAlive) {
			afterKill();
			return;
		}
		
		if(isAlive)
			calculateMove();
	}
	
	@Override
	public void render(Screen screen) {
		
		if(isAlive)
			chooseSprite();
		else {
			if(timeAfter > 0) {
				sprite = deadSprite;
				animate = 0;
			} else {
				sprite = Sprite.movingSprite(Sprite.mob_dead1, Sprite.mob_dead2, Sprite.mob_dead3, animate, 60);
			}
				
		}
			
		screen.renderEntity((int) x, (int) y - sprite.SIZE, this);
	}

	@Override
	public void calculateMove() throws UnsupportedAudioFileException, LineUnavailableException, IOException {
		int xa = 0, ya = 0;
		if(steps <= 0){
			direction = ai.calculateDirection();
			steps = MAX_STEPS;
		}
			
		if(direction == 0) {
			ya--;
		}
		if(direction == 1) {
			xa++;
		}
		if(direction == 2) {
			ya++;
		}
		if(direction == 3) {
			xa--;
		}
		
		if(canMove(xa, ya)) {
			steps -= 1 + rest;
			move(xa * speed, ya * speed);
			isMoving = true;
		} else {
			steps = 0;
			isMoving = false;
		}
	}
	
	@Override
	public void move(double xa, double ya) {
		if(!isAlive) return;

			y += ya;
			x += xa;
	}
	
	@Override
	public boolean canMove(double x, double y) throws UnsupportedAudioFileException, LineUnavailableException, IOException {
		
		double xr = this.x, yr = this.y - 16; //subtract y to get more accurate results
		
		//the thing is, subract 15 to 16 (sprite size), so if we add 1 tile we get the next pixel tile with this
		//we avoid the shaking inside tiles with the help of steps
		if(direction == 0) {
			yr += sprite.getSize() -1 ;
			xr += sprite.getSize()/2;
		}
		if(direction == 1) {
			yr += sprite.getSize()/2;
			xr += 1;
		}
		if(direction == 2) {
			xr += sprite.getSize()/2;
			yr += 1;
		}
		if(direction == 3) {
			xr += sprite.getSize() -1;
			yr += sprite.getSize()/2;
		}
		
		int xx = Coordinates.pixelToTile(xr) +(int)x;
		int yy = Coordinates.pixelToTile(yr) +(int)y;
		
		Entity entity = board.getEntity(xx, yy, this);
		return entity.collide(this);
	}

	@Override
	public boolean collide(Entity e) throws UnsupportedAudioFileException, LineUnavailableException, IOException {
		if(e instanceof Flame) {
			kill();
			return false;
		}
		
		if(e instanceof Bomber) {
			((Bomber) e).kill();
			return false;
		}
		
		return true;
	}
	
	@Override
	public void kill() throws UnsupportedAudioFileException, LineUnavailableException, IOException {
		if(!isAlive) {
			return;
		}
		Sound.play("aaa");
		isAlive = false;
		board.addPoints(points);
		Notification msg = new Notification("+" + points, getXMessage(), getYMessage(), 2, Color.white, 14);
		board.addMessage(msg);
	}
	
	
	@Override
	protected void afterKill() {
		if(timeAfter > 0) --timeAfter;
		else {
			
			if(finalAnimation > 0) --finalAnimation;
			else
				remove();
		}
	}
	
	protected abstract void chooseSprite();
}
