package main.java;

import java.awt.Font;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import main.java.Entities.Bomb;
import main.java.Entities.Entity;
import main.java.Entities.Notification;
import main.java.Entities.bomb.FlameSegment;
import main.java.Entities.dynamicEntities.Bomber;
import main.java.Graphics.Render;
import main.java.Graphics.Screen;
import main.java.Input.Keyboard;
import main.java.Entities.dynamicEntities.Character;
import main.java.Level.FileLevelLoader;
import main.java.Level.LevelLoader;

/**
 * Class Board dùng để quản lý các thao tác điều khiển,
 * bao gồm load level, render, update các màn hình
 * của game.
 */
public class Board implements Render {
  protected LevelLoader levelLoader;
  protected Game game;
  protected Keyboard input;
  protected main.java.Graphics.Screen screen;

  public Entity[] entities;
  public List<Character> characters = new ArrayList<>();
  protected List<Bomb> bombs = new ArrayList<>();
  private List<Notification> notifications = new ArrayList<>();

  //1:endgame, 2:changelevel, 3:paused
  private int screenToShow = -1;

  private int time = Game.TIME;
  private int points = Game.POINTS;

  public Board(Game game, Keyboard input, main.java.Graphics.Screen screen) {
    this.game = game;
    this.input = input;
    this.screen = screen;
    loadLevel(1);
  }

  @Override
  public void update() {
    if (game.isPaused()) {
      return;
    }

    updateEntities();
    updateCharacters();
    updateBombs();
    updateNotification();
    detectEndGame();

    for (int i = 0; i < characters.size(); i++) {
      if (characters.get(i).isRemoved()) {
        characters.remove(i);
      }
    }
  }

  @Override
  public void render(Screen screen) {
    if (game.isPaused()) {
      return;
    }

    //only render the visible part of screen
    int x0 = Screen.xOffset >> 4; //tile precision, -> left X
    int x1 = (Screen.xOffset + screen.getWidth() + Game.TILES_SIZE) / Game.TILES_SIZE; // -> right X
    int y0 = Screen.yOffset >> 4;
    int y1 = (Screen.yOffset + screen.getHeight()) / Game.TILES_SIZE; //render one tile plus to fix black margins

    for (int y = y0; y < y1; y++) {
      for (int x = x0; x < x1; x++) {
        entities[x + y * levelLoader.getWidth()].render(screen);
      }
    }

    renderBombs(screen);
    renderCharacter(screen);
  }

  public void nextLevel() {
    Game.setBombRadius(1);
    Game.setBombRate(1);
    Game.setBomberSpeed(1.0);
    loadLevel(levelLoader.getLevel() + 1);
  }

  public void loadLevel(int level) {
    time = Game.TIME;
    screenToShow = 2;
    game.resetScreenDelay();
    game.pause();
    characters.clear();
    bombs.clear();
    notifications.clear();

    try {
      levelLoader = new FileLevelLoader(this, level);
      entities = new Entity[levelLoader.getHeight() * levelLoader.getWidth()];
      levelLoader.createEntities();
    } catch (LoadLevelException e) {
      endGame();
    }
  }

  protected void detectEndGame() {
    if (time <= 0) {
      endGame();
    }
  }

  public void endGame() {
    screenToShow = 1;
    game.resetScreenDelay();
    game.pause();
  }

  public boolean detectNoEnemies() {// phat hien enemies
    int total = 0;
    for (int i = 0; i < characters.size(); i++) {
      if (characters.get(i) instanceof Bomber == false) {
        ++total;
        }
    }
    return total == 0;
  }

  public void drawScreen(Graphics g) {
    switch (screenToShow) {
      case 1:
        screen.drawEndGame(g, points);
        break;
      case 2:
        screen.drawChangeLevel(g, levelLoader.getLevel());
        break;
      case 3:
        screen.drawPaused(g);
        break;
    }
  }

  public Entity getEntity(double x, double y, Character m) {
    Entity res = null;
    res = getFlameSegmentAt((int)x, (int)y);
    if( res != null) {
      return res;
    }

    res = getBombAt(x, y);
    if( res != null) {
      return res;
    }

    res = getCharacterAtExcluding((int)x, (int)y, m);
    if( res != null) {
      return res;
    }

    res = getEntityAt((int)x, (int)y);
    return res;
  }

  public List<Bomb> getBombs() {
    return bombs;
  }

  public Bomb getBombAt(double x, double y) {
    Iterator<Bomb> bomb_list = bombs.iterator();
    Bomb b;

    while (bomb_list.hasNext()) {
      b = bomb_list.next();
      if(b.getX() == (int)x && b.getY() == (int)y)
        return b;
    }

    return null;
  }

  public Bomber getBomber() {
    Iterator<Character> itr = characters.iterator();
    Character cur;

    while (itr.hasNext()) {
      cur = itr.next();
      if(cur instanceof Bomber)
        return (Bomber) cur;
    }

    return null;
  }

  public Character getCharacterAtExcluding(int x, int y, Character a) {
    Iterator<Character> itr = characters.iterator();
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
    Iterator<Bomb> bomb_list = bombs.iterator();
    Bomb b;

    while (bomb_list.hasNext()) {
      b = bomb_list.next();
      FlameSegment e = b.flameAt(x, y);
      if(e != null) {
        return e;
      }
    }

    return null;
  }

  public Entity getEntityAt(double x, double y) {
    return entities[(int)x + (int)y * levelLoader.getWidth()];
  }

  public void addEntity(int pos, Entity entity) {
    entities[pos] = entity;
  }

  public void addCharacter(Character character) {
    characters.add(character);
  }

  public void addBomb(Bomb bomb) {
    bombs.add(bomb);
  }

  public void addNotification(Notification notification) {
    notifications.add(notification);
  }

  protected void renderCharacter(Screen screen) {
    Iterator<Character> itr = characters.iterator();
    while (itr.hasNext()) {
      itr.next().render(screen);
    }
  }

  protected void renderBombs(Screen screen) {
    Iterator<Bomb> itr = bombs.iterator();
    while (itr.hasNext()) {
      itr.next().render(screen);
    }
  }

  public void renderNotification(Graphics g) {
    Notification notification;
    for (int i = 0; i < notifications.size(); i++) {
      notification = notifications.get(i);
      g.setFont(new Font("Arial", Font.PLAIN, notification.getSize()));
      g.setColor(notification.getColor());
      g.drawString(notification.getNotification(),
          (int)notification.getX() - Screen.xOffset  * Game.SCALE,
          (int)notification.getY());
    }
  }

  protected void updateEntities() {
    if (game.isPaused()) {
      return;
    }
    for (int i = 0; i < entities.length; i++) {
      entities[i].update();
    }
  }

  protected void updateCharacters() {
    if(game.isPaused()) {
      return;
    }

    Iterator<Character> itr = characters.iterator();

    while (itr.hasNext() && !game.isPaused()) {
      itr.next().update();
    }
  }

  protected void updateBombs() {
    if(game.isPaused()) {
      return;
    }

    Iterator<Bomb> itr = bombs.iterator();

    while (itr.hasNext()) {
      itr.next().update();
    }
  }

  protected void updateNotification() {
    if (game.isPaused()) {
      return;
    }
    Notification notification;
    int left;

    for (int i = 0; i < notifications.size(); i++) {
      notification = notifications.get(i);
      left = notification.getDuration();

      if (left > 0) {
        notification.setDuration(--left);
      } else {
        notifications.remove(i);
      }
    }
  }

  public int timeSubtraction() {
    if (game.isPaused()) {
      return this.time;
    } else {
      return this.time--;
    }
  }

  public Keyboard getInput() {
    return input;
  }

  public LevelLoader getLevel() {
    return levelLoader;
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

  public int getPoints() {
    return points;
  }

  public void addPoints(int points) {
    this.points += points;
  }

  public int getWidth() {
    return levelLoader.getWidth();
  }

  public int getHeight() {
    return levelLoader.getHeight();
  }
}
