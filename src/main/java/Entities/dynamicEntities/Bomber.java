package main.java.Entities.dynamicEntities;

import java.awt.Color;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import main.java.Board;
import main.java.Entities.Entity;
import main.java.Entities.Bomb.Bomb;
import main.java.Entities.Bomb.Flame;
import main.java.Entities.Notification;
import main.java.Entities.dynamicEntities.Enemies.Enemy;
import main.java.Entities.staticEntities.Items.Items;
import main.java.Game;
import main.java.Graphics.Sprite;
import main.java.Input.Keyboard;
import main.java.Graphics.Screen;
import main.java.Level.Coordinates;
import main.java.Sound.Sound;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

/**
 * Class của nhân vật chính Bomber.
 */
public class Bomber extends Character {
  protected Keyboard keyboard;
  private List<Bomb> bombList;
  public static List<Items> itemsList = new ArrayList<Items>();
  protected int timeBetweenPutBombs = 7500;

  public Bomber(double x, double y, Board board) {
    super(x, y, board);
    this.bombList = board.getBombs();
    keyboard = board.getInput();
    this.sprite = Sprite.player_right;
  }
  /*
  |--------------------------------------------------------------------------
  | Update & Render
  |--------------------------------------------------------------------------
   */
  @Override
  public void update() throws UnsupportedAudioFileException, LineUnavailableException, IOException {
    clearBombs();

    if (this.isAlive) {
      this.afterKilled();
      return;
    }

    if (timeBetweenPutBombs < 0) {
      timeBetweenPutBombs = 7500;
    } else {
      timeBetweenPutBombs--;
    }
    animate();
    calculateMove();
    detectPlaceOfBombs();
  }

  @Override
  public void render(Screen screen) {
    calculateXOffset();

    if (isAlive) {
      chooseSprite();
    } else {
      this.sprite = Sprite.player_dead1;
    }

    screen.renderEntity((int) this.x,
        (int) this.y - this.sprite.SIZE, this);
  }

  private void calculateXOffset() {
    int Scroll = Screen.calculateXOffset(this.board, this);
    Screen.setOffset(Scroll, 0);
  }
  /*
  |--------------------------------------------------------------------------
  | Cac phuong thuc cua qua trinh dat bomb
  |--------------------------------------------------------------------------
   */
  private void detectPlaceOfBombs() throws UnsupportedAudioFileException, LineUnavailableException, IOException {
    if(keyboard.space && Game.getBombRate() > 0 && timeBetweenPutBombs < 0) {
      int xt = Coordinates.pixelToTile(this.x + sprite.getSize() / 2);
      int yt = Coordinates.pixelToTile( (this.y + sprite.getSize() / 2) - sprite.getSize() ); //subtract half player height and minus 1 y position

      placeBomb(xt,yt);
      Game.addBombRate(-1);

      timeBetweenPutBombs = 30;
    }
  }

  private void placeBomb(int xt, int yt) throws UnsupportedAudioFileException, LineUnavailableException, IOException {
    Bomb bomb = new Bomb(xt, yt, this.board);
    board.addBomb(bomb);
    // Hiệu ứng âm thanh khi đặt bomb thành công
    Sound.play("bomset");
  }

  private void clearBombs() {
    Iterator<Bomb> bl = bombList.iterator();
    Bomb bomb;

    while (bl.hasNext()) {
      bomb = bl.next();
      if (bomb.isRemoved()) {
        bl.remove();
        Game.addBombRate(1);
      }
    }
  }

  private void chooseSprite() {
    switch (direction) {
      case 0:
        this.sprite = Sprite.player_up;
        if (isMoving) {
          this.sprite = Sprite.movingSprite(Sprite.player_up_1, Sprite.player_up_2, animated, 20);
        }
        break;
      case 1:
        this.sprite = Sprite.player_right;
        if (isMoving) {
          this.sprite = Sprite.movingSprite(Sprite.player_right_1, Sprite.player_right_2, animated, 20);
        }
        break;
      case 2:
        this.sprite = Sprite.player_down;
        if (isMoving) {
          sprite = Sprite.movingSprite(Sprite.player_down_1, Sprite.player_down_2, animated, 20);
        }
        break;
      case 3:
        this.sprite = Sprite.player_left;
        if (isMoving) {
          this.sprite = Sprite.movingSprite(Sprite.player_left_1, Sprite.player_left_2, animated, 20);
        }
        break;
    }
  }
  /*
  |--------------------------------------------------------------------------
  | Cac ham xu ly va cham va bi tieu diet cua Bomber
  |--------------------------------------------------------------------------
   */
  @Override
  public boolean collided(Entity entity) throws UnsupportedAudioFileException, LineUnavailableException, IOException {
    if (entity instanceof Flame){
      this.kill();
      return false;
    }
    if (entity instanceof Enemy){
      this.kill();
      return true;
    }
    return true;
  }

  @Override
  public void kill() throws UnsupportedAudioFileException, LineUnavailableException, IOException {
    if (!isAlive) {
      return;
    } else {
      isAlive = false;
      this.board.addLives(-1);
      Notification notification =
          new Notification("-1 LIVE", getXNotification(), getYNotification(),
              2, Color.white, 16);
      // Hiệu ứng âm thanh khi Bomber bị tiêu diệt
      Sound.play("endgame");
    }
  }

  @Override
  protected void afterKilled() {
    if (time_left > 0) {
      --time_left;
    } else {
      if (this.bombList.size() == 0) {
        if (this.board.getLives() == 0) {
          this.board.endGame();
        } else {
          this.board.restartLevel();
        }
      }
    }
  }

  /*
  |--------------------------------------------------------------------------
  | Cac ham thuc hien qua trinh di chuyen cua Bomber
  |--------------------------------------------------------------------------
    */
  @Override
  protected boolean canMove(double xa, double ya) throws UnsupportedAudioFileException, LineUnavailableException, IOException {
    for (int i = 0; i < 4; i++) {
      double xt = ((this.x + xa) + i % 2 * 9) / Game.TILES_SIZE;
      double yt = ((this.y + ya) + i / 2 * 10 - 13) / Game.TILES_SIZE;
      Entity entity = board.getEntity(xt, yt, this);

      if (!entity.collided(this)) {
        return false;
      }
    }
    return true;
  }

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

    if (xa != 0 || ya != 0)  {
      move(xa * Game.getBomberSpeed(), ya * Game.getBomberSpeed());
      isMoving = true;
    } else {
      isMoving = false;
    }
  }

  @Override
  protected void move(double xa, double ya) throws UnsupportedAudioFileException, LineUnavailableException, IOException {
    if (xa > 0) {
      direction = 1;
    }
    if (xa < 0) {
      direction = 3;
    }
    if (ya > 0) {
      direction = 2;
    }
    if (ya < 0) {
      direction = 0;
    }
    if (canMove(0, ya)) {
      this.y += ya;
    }
    if(canMove(xa, 0)) {
      this.x += xa;
    }
  }
  /*
|--------------------------------------------------------------------------
| Cac ham thuc hien qua trinh thu nhan item cua Bomber
|--------------------------------------------------------------------------
 */
  public void addItem(Items item) {
    if(item.isRemoved()) {
      return;
    }
    itemsList.add(item);

    item.setValues();
  }

  public void clearUsedItem() {
    Items item;
    for (int i = 0; i < itemsList.size(); i++) {
      item = itemsList.get(i);
      if (!item.isActive()) {
        itemsList.remove(i);
      }
    }
  }

  public void removePowerups() {
    for (int i = 0; i < itemsList.size(); i++) {
      itemsList.remove(i);
    }
  }
}
