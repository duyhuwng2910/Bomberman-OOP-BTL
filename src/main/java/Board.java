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
import main.java.Graphics.Render;
import main.java.Graphics.Screen;
import main.java.Input.Keyboard;
import main.java.Level.FileLevelLoader;
import main.java.Level.LevelLoader;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

/**
 * Class Board dùng để quản lý các thao tác điều khiển,
 * bao gồm load level, render, update các màn hình
 * của game.
 */
public class Board implements Render {
	protected LevelLoader level;
	protected Game game;
	protected Keyboard input;
	protected Screen screen;
	
	public Entity[] entities;
	public List<Character> characterList = new ArrayList<>();
	protected List<Bomb> bombList = new ArrayList<>();
	protected List<Notification> notificationList = new ArrayList<>();

	//1:endGame, 2:nextLevel, 3:paused
	protected int screenToShow = -1;
	
	protected int time = Game.TIME;
	protected int points = Game.POINTS;
	protected int lives = Game.LIVES;
	
	public Board(Game game, Keyboard input, Screen screen) {
		this.game = game;
		this.input = input;
		this.screen = screen;
		changeLevel(1); // Bắt đầu game ở level 1
	}

	/*
	|--------------------------------------------------------------------------
	| Hàm Render và Update
	|--------------------------------------------------------------------------
	*/
	@Override
	public void update() throws UnsupportedAudioFileException, LineUnavailableException, IOException {
		if (game.isPaused()) {
			return;
		}
		updateEntities();
		updateMobs();
		updateBombs();
		updateNotification();
		detectEndGame();
		
		for (int i = 0; i < characterList.size(); i++) {
			Character a = characterList.get(i);
			if ((a).isRemoved()) {
				characterList.remove(i);
			}
		}
	}


	@Override
	public void render(Screen screen) {
		if (game.isPaused()) {
			return;
		}

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
		renderCharacters(screen);
	}
	/*
 	|--------------------------------------------------------------------------
 	| Các trình bắt đầu vào game
 	|--------------------------------------------------------------------------
  */
	public void restartLevel() {
		changeLevel(level.getLevel());
	}
	
	public void nextLevel() {
		changeLevel(level.getLevel() + 1);
	}
	
	public void changeLevel(int level) {
		time = Game.TIME;
		screenToShow = 2;
		game.resetScreenDelay();
		game.pause();
		characterList.clear();
		bombList.clear();
		notificationList.clear();
		
		try {
			this.level = new FileLevelLoader("levels/Level" + level + ".txt", this);
			entities = new Entity[this.level.getHeight() * this.level.getWidth()];
			this.level.createEntities();
		} catch (LoadLevelException e) {
			endGame();
		}
	}

	public boolean isItemUsed(int x, int y, int level) {
		Items p;
		for (int i = 0; i < Bomber.itemsList.size(); i++) {
			p = Bomber.itemsList.get(i);
      if (p.getX() == x && p.getY() == y && level == p.getLevel()) {
        return true;
			}
		}
		return false;
	}

	/*
  |--------------------------------------------------------------------------
  | Qúa trình can thiệp vào game
  |--------------------------------------------------------------------------
   */
	protected void detectEndGame() {
    if (time <= 0) {
      restartLevel();
		}
	}
	
	public void endGame() {
		screenToShow = 1;
		game.resetScreenDelay();
		game.pause();
	}
	
	public boolean detectNoEnemies() {
		int total = 0;
		for (int i = 0; i < characterList.size(); i++) {
      if (!(characterList.get(i) instanceof Bomber)) {
        ++total;
			}
		}
		return total == 0;
	}

	public void gamePause() {
		game.resetScreenDelay();
    if (screenToShow <= 0) {
      screenToShow = 3;
		}
		game.pause();
	}
	
	public void gameResume() {
		game.resetScreenDelay();
		screenToShow = -1;
		game.run();
	}

	public void drawScreen(Graphics g) {
		switch (screenToShow) {
			case 1:
				screen.drawEndGame(g, points);
				break;
			case 2:
				screen.drawChangeLevel(g, level.getLevel());
				break;
			case 3:
				screen.drawPaused(g);
				break;
		}
	}

	/*
	|--------------------------------------------------------------------------
	| Các hàm getter và setter
	|--------------------------------------------------------------------------
 	 */
	public Entity getEntity(double x, double y, Character m) {
		Entity res = null;
		res = getFlameSegmentAt((int)x, (int)y);

		if ( res != null) {
			return res;
		}
		res = getBombAt(x, y);
		if ( res != null) {
			return res;
		}
		res = getCharacterAtExcluding((int)x, (int)y, m);
		if ( res != null) {
			return res;
		}
		res = getEntityAt((int)x, (int)y);
		return res;
	}
	
	public List<Bomb> getBombs() {
		return bombList;
	}
	
	public Bomb getBombAt(double x, double y) {
		Iterator<Bomb> bs = bombList.iterator();
		Bomb b;

		while (bs.hasNext()) {
			b = bs.next();
      if (b.getX() == (int) x && b.getY() == (int) y) {
        return b;
			}
		}
		return null;
	}
	
	public Character getCharacterAt(double x, double y) {
		Iterator<Character> itr = characterList.iterator();
		Character cur;

		while (itr.hasNext()) {
			cur = itr.next();

      if (cur.getXTile() == x && cur.getYTile() == y) {
        return cur;
			}
		}
		return null;
	}
	
	public Bomber getBomber() {
		Iterator<Character> itr = characterList.iterator();
		Character cur;

		while (itr.hasNext()) {
			cur = itr.next();
      if (cur instanceof Bomber) {
        return (Bomber) cur;
			}
		}
		return null;
	}
	
	public Character getCharacterAtExcluding(int x, int y, Character a) {
		Iterator<Character> itr = characterList.iterator();
		Character cur;

		while (itr.hasNext()) {
			cur = itr.next();

			if (cur == a) {
				continue;
			}
			if (cur.getXTile() == x && cur.getYTile() == y) {
				return cur;
			}
		}
		return null;
	}
	
	public FlameSegment getFlameSegmentAt(int x, int y) {
		Iterator<Bomb> bs = bombList.iterator();
		Bomb b;

		while (bs.hasNext()) {
			b = bs.next();
			FlameSegment fs = b.flameAt(x, y);
			if (fs != null) {
				return fs;
			}
		}
		return null;
	}
	
	public Entity getEntityAt(double x, double y) {
		return entities[(int)x + (int)y * level.getWidth()];
	}

	/*
  |--------------------------------------------------------------------------
  | Các hàm thêm và xóa thực thể, thông báo...
  |--------------------------------------------------------------------------
   */
	public void addEntity(int pos, Entity e) {
		entities[pos] = e;
	}
	
	public void addCharacter(Character character) {
		characterList.add(character);
	}
	
	public void addBomb(Bomb bomb) {
		bombList.add(bomb);
	}
	
	public void addNotification(Notification notification) {
		notificationList.add(notification);
	}
	
	/*
	|--------------------------------------------------------------------------
	| Renders
	|--------------------------------------------------------------------------
	 */
	
	protected void renderCharacters(Screen screen) {
		Iterator<Character> itr = characterList.iterator();
    while (itr.hasNext()) {
      itr.next().render(screen);
		}
	}
	
	protected void renderBombs(Screen screen) {
		Iterator<Bomb> itr = bombList.iterator();
    while (itr.hasNext()) {
      itr.next().render(screen);
		}
	}
	
	public void renderNotification(Graphics g) {
		Notification notification;
		for (int i = 0; i < notificationList.size(); i++) {
			notification = notificationList.get(i);
			g.setFont(new Font("Arial", Font.PLAIN, notification.getSize()));
			g.setColor(notification.getColor());
			g.drawString(notification.getNotification(), (int)notification.getX() - Screen.xOffset  * Game.SCALE, (int)notification.getY());
		}
	}
	/*
  |--------------------------------------------------------------------------
  | Các hàm update thực thể, thông báo...
  |--------------------------------------------------------------------------
   */
	protected void updateEntities() throws UnsupportedAudioFileException, LineUnavailableException, IOException {
		if (game.isPaused()) {
			return;
		}
		for (int i = 0; i < entities.length; i++) {
			entities[i].update();
		}
	}
	
	protected void updateMobs() throws UnsupportedAudioFileException, LineUnavailableException, IOException {
		if (game.isPaused()) {
			return;
		}
		Iterator<Character> itr = characterList.iterator();
    while (itr.hasNext() && !game.isPaused()) {
      itr.next().update();
		}
	}
	
	protected void updateBombs() throws UnsupportedAudioFileException, LineUnavailableException, IOException {
		if (game.isPaused()) {
			return;
		}
		Iterator<Bomb> itr = bombList.iterator();

    while (itr.hasNext()) {
      itr.next().update();
		}
	}
	
	protected void updateNotification() {
		if(game.isPaused()) {
			return;
		}
		Notification notification;
		int left;

		for (int i = 0; i < notificationList.size(); i++) {
			notification = notificationList.get(i);
			left = notification.getDuration();

      if (left > 0) {
        notification.setDuration(--left);
      } else {
        notificationList.remove(i);
			}
		}
	}
	
	/*
	|--------------------------------------------------------------------------
	| Các hàm getter và setter cần cho quá trình game chạy.
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
		return screenToShow;
	}
	
	public void setShow(int i) {
		screenToShow = i;
	}
	
	public int getTime() {
		return time;
	}
	
	public int getLives() {
		return lives;
	}

	public int subtractTime() {
    if (game.isPaused()) {
      return this.time;
    } else {
      return this.time--;
		}
	}

	public int getPoints() {
		return points;
	}

	public void addPoints(int points) {
		this.points += points;
	}

	public void addLives(int lives) {
		this.lives += lives;
	}
	
	public int getWidth() {
		return level.getWidth();
	}
}
