package main.java;

import java.awt.Font;
import java.awt.Graphics;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import main.java.Entities.Entity;
import main.java.Entities.Notification;
import main.java.Entities.Bomb.Bomb;
import main.java.Entities.Bomb.FlameSegment;
import main.java.Entities.dynamicEntities.Character;
import main.java.Entities.dynamicEntities.Bomber;
import main.java.Entities.staticEntities.Items.Items;
import main.java.Exception.LoadLevelException;
import main.java.Graphics.IRender;
import main.java.Graphics.Screen;
import main.java.Input.Keyboard;
import main.java.Level.FileLevelLoader;
import main.java.Level.LevelLoader;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

public class Board implements IRender {

	protected LevelLoader level;
	protected Game game;
	protected Keyboard input;
	protected Screen screen;
	
	public Entity[] entities;
	public List<Character> mobs = new ArrayList<>();
	protected List<Bomb> bombList = new ArrayList<>();
	private List<Notification> notificationList = new ArrayList<>();
	
	private int _screenToShow = -1;
	
	private int _time = Game.TIME;
	private int _points = Game.POINTS;
	private int _lives = Game.LIVES;
	
	public Board(Game game, Keyboard input, Screen screen) {
		this.game = game;
		this.input = input;
		this.screen = screen;
		
		changeLevel(1);
	}

	@Override
	public void update() throws UnsupportedAudioFileException, LineUnavailableException, IOException {
		if( game.isPaused() ) return;
		
		updateEntities();
		updateMobs();
		updateBombs();
		updateMessages();
		detectEndGame();
		
		for (int i = 0; i < mobs.size(); i++) {
			Character a = mobs.get(i);
			if(((Entity)a).isRemoved()) mobs.remove(i);
		}
	}


	@Override
	public void render(Screen screen) {
		if( game.isPaused() ) return;

		int x0 = Screen.xOffset >> 4;
		int x1 = (Screen.xOffset + screen.getWidth() + Game.TILES_SIZE) / Game.TILES_SIZE;
		int y0 = Screen.yOffset >> 4;
		int y1 = (Screen.yOffset + screen.getHeight()) / Game.TILES_SIZE;
		
		for (int y = y0; y < y1; y++) {
			for (int x = x0; x < x1; x++) {
				entities[x + y * level.getWidth()].render(screen);
			}
		}
		
		renderBombs(screen);
		renderMobs(screen);
		
	}

	public void newGame() {
		resetProperties();
		changeLevel(1);
	}
	
	@SuppressWarnings("static-access")
	private void resetProperties() {
		_points = Game.POINTS;
		_lives = Game.LIVES;
		Bomber.itemsList.clear();
		
		game.playerSpeed = 1.0;
		game.bombRadius = 1;
		game.bombRate = 1;
		
	}

	public void restartLevel() {
		changeLevel(level.getLevel());
	}
	
	public void nextLevel() {
		changeLevel(level.getLevel() + 1);
	}
	
	public void changeLevel(int level) {
		_time = Game.TIME;
		_screenToShow = 2;
		game.resetScreenDelay();
		game.pause();
		mobs.clear();
		bombList.clear();
		notificationList.clear();
		
		try {
			this.level = new FileLevelLoader("levels/Level" + level + ".txt", this);
			entities = new Entity[this.level.getHeight() * this.level.getWidth()];
			
			this.level.createEntities();
		} catch (LoadLevelException e) {
			endGame(); //failed to load.. so.. no more levels?
		}
	}

	
	public boolean isPowerupUsed(int x, int y, int level) {
		Items p;
		for (int i = 0; i < Bomber.itemsList.size(); i++) {
			p = Bomber.itemsList.get(i);
			if(p.getX() == x && p.getY() == y && level == p.getLevel())
				return true;
		}
		
		return false;
	}
	
	/*
	|--------------------------------------------------------------------------
	| Detections
	|--------------------------------------------------------------------------
	 */
	protected void detectEndGame() {
		if(_time <= 0)
			restartLevel();
	}
	
	public void endGame() {
		_screenToShow = 1;
		game.resetScreenDelay();
		game.pause();
	}
	
	public boolean detectNoEnemies() {
		int total = 0;
		for (int i = 0; i < mobs.size(); i++) {
			if(mobs.get(i) instanceof Bomber == false)
				++total;
		}
		
		return total == 0;
	}
	
	/*
	|--------------------------------------------------------------------------
	| Pause & Resume
	|--------------------------------------------------------------------------
	 */
	public void gamePause() {
		game.resetScreenDelay();
		if(_screenToShow <= 0)
			_screenToShow = 3;
		game.pause();
	}
	
	public void gameResume() {
		game.resetScreenDelay();
		_screenToShow = -1;
		game.run();
	}

	public void drawScreen(Graphics g) {
		switch (_screenToShow) {
			case 1:
				break;
			case 2:
				screen.drawChangeLevel(g, level.getLevel());
				break;
			case 3:
				screen.drawPaused(g);
				break;
		}
	}

	public Entity getEntity(double x, double y, Character m) {
		
		Entity res = null;
		
		res = getExplosionAt((int)x, (int)y);
		if( res != null) return res;
		
		res = getBombAt(x, y);
		if( res != null) return res;
		
		res = getMobAtExcluding((int)x, (int)y, m);
		if( res != null) return res;
		
		res = getEntityAt((int)x, (int)y);
		
		return res;
	}
	
	public List<Bomb> getBombs() {
		return bombList;
	}
	
	public Bomb getBombAt(double x, double y) {
		Iterator<Bomb> bs = bombList.iterator();
		Bomb b;
		while(bs.hasNext()) {
			b = bs.next();
			if(b.getX() == (int)x && b.getY() == (int)y)
				return b;
		}
		
		return null;
	}
	
	public Character getMobAt(double x, double y) {
		Iterator<Character> itr = mobs.iterator();
		
		Character cur;
		while(itr.hasNext()) {
			cur = itr.next();
			
			if(cur.getXTile() == x && cur.getYTile() == y)
				return cur;
		}
		
		return null;
	}
	
	public Bomber getPlayer() {
		Iterator<Character> itr = mobs.iterator();
		
		Character cur;
		while(itr.hasNext()) {
			cur = itr.next();
			
			if(cur instanceof Bomber)
				return (Bomber) cur;
		}
		
		return null;
	}
	
	public Character getMobAtExcluding(int x, int y, Character a) {
		Iterator<Character> itr = mobs.iterator();
		
		Character cur;
		while(itr.hasNext()) {
			cur = itr.next();
			if(cur == a) {
				continue;
			}
			
			if(cur.getXTile() == x && cur.getYTile() == y) {
				return cur;
			}
				
		}
		
		return null;
	}
	
	public FlameSegment getExplosionAt(int x, int y) {
		Iterator<Bomb> bs = bombList.iterator();
		Bomb b;
		while(bs.hasNext()) {
			b = bs.next();
			
			FlameSegment e = b.explosionAt(x, y);
			if(e != null) {
				return e;
			}
				
		}
		
		return null;
	}
	
	public Entity getEntityAt(double x, double y) {
		return entities[(int)x + (int)y * level.getWidth()];
	}
	
	/*
	|--------------------------------------------------------------------------
	| Adds and Removes
	|--------------------------------------------------------------------------
	 */
	public void addEntitie(int pos, Entity e) {
		entities[pos] = e;
	}
	
	public void addMob(Character e) {
		mobs.add(e);
	}
	
	public void addBomb(Bomb e) {
		bombList.add(e);
	}
	
	public void addMessage(Notification e) {
		notificationList.add(e);
	}
	
	/*
	|--------------------------------------------------------------------------
	| Renders
	|--------------------------------------------------------------------------
	 */
	protected void renderEntities(Screen screen) {
		for (int i = 0; i < entities.length; i++) {
			entities[i].render(screen);
		}
	}
	
	protected void renderMobs(Screen screen) {
		Iterator<Character> itr = mobs.iterator();
		
		while(itr.hasNext())
			itr.next().render(screen);
	}
	
	protected void renderBombs(Screen screen) {
		Iterator<Bomb> itr = bombList.iterator();
		
		while(itr.hasNext())
			itr.next().render(screen);
	}
	
	public void renderMessages(Graphics g) {
		Notification m;
		for (int i = 0; i < notificationList.size(); i++) {
			m = notificationList.get(i);
			
			g.setFont(new Font("Arial", Font.PLAIN, m.getSize()));
			g.setColor(m.getColor());
			g.drawString(m.getMessage(), (int)m.getX() - Screen.xOffset  * Game.SCALE, (int)m.getY());
		}
	}
	
	/*
	|--------------------------------------------------------------------------
	| Updates
	|--------------------------------------------------------------------------
	 */
	protected void updateEntities() throws UnsupportedAudioFileException, LineUnavailableException, IOException {
		if( game.isPaused() ) return;
		for (int i = 0; i < entities.length; i++) {
			entities[i].update();
		}
	}
	
	protected void updateMobs() throws UnsupportedAudioFileException, LineUnavailableException, IOException {
		if( game.isPaused() ) return;
		Iterator<Character> itr = mobs.iterator();
		
		while(itr.hasNext() && !game.isPaused())
			itr.next().update();
	}
	
	protected void updateBombs() throws UnsupportedAudioFileException, LineUnavailableException, IOException {
		if( game.isPaused() ) return;
		Iterator<Bomb> itr = bombList.iterator();
		
		while(itr.hasNext())
			itr.next().update();
	}
	
	protected void updateMessages() {
		if( game.isPaused() ) return;
		Notification m;
		int left = 0;
		for (int i = 0; i < notificationList.size(); i++) {
			m = notificationList.get(i);
			left = m.getDuration();
			
			if(left > 0) 
				m.setDuration(--left);
			else
				notificationList.remove(i);
		}
	}
	
	/*
	|--------------------------------------------------------------------------
	| Getters & Setters
	|--------------------------------------------------------------------------
	 */
	public Keyboard getInput() {
		return input;
	}
	
	public LevelLoader getLevel() {
		return level;
	}
	
	public Game getGame() {
		return game;
	}
	
	public int getShow() {
		return _screenToShow;
	}
	
	public void setShow(int i) {
		_screenToShow = i;
	}
	
	public int getTime() {
		return _time;
	}
	
	public int getLives() {
		return _lives;
	}

	public int subtractTime() {
		if(game.isPaused())
			return this._time;
		else
			return this._time--;
	}

	public int getPoints() {
		return _points;
	}

	public void addPoints(int points) {
		this._points += points;
	}

	public void addLives(int lives) {
		this._lives += lives;
	}
	
	public int getWidth() {
		return level.getWidth();
	}

	public int getHeight() {
		return level.getHeight();
	}
	
}
