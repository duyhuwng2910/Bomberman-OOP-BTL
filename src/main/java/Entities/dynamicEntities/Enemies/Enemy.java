package main.java.Entities.dynamicEntities.Enemies;

import java.awt.Color;
import java.io.IOException;

import main.java.Board;
import main.java.Entities.Entity;
import main.java.Entities.Notification;
import main.java.Entities.dynamicEntities.Character;
import main.java.Entities.dynamicEntities.Enemies.AI_enemies.AI;
import main.java.Game;
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

    MAX_STEPS = Game.TILES_SIZE / speed;
    rest = (MAX_STEPS - (int) MAX_STEPS) / MAX_STEPS;
    steps = MAX_STEPS;

    this.time_left = 20;
    deadSprite = dead;
  }

  @Override
  public void update() throws UnsupportedAudioFileException, LineUnavailableException, IOException {
    animate();

    if(!isAlive) {
      afterKilled();
      return;
    }

    if (isAlive) {
      calculateMove();
      }
  }

  @Override
  public void render(Screen screen) {
    if (isAlive) {
      chooseSprite();
    } else {
      if (time_left > 0) {
        sprite = deadSprite;
        this.animated = 0;
      } else {
        sprite =
            Sprite.movingSprite(Sprite.mob_dead1, Sprite.mob_dead2, Sprite.mob_dead3, animated, 60);
      }
    }
    if (this.sprite != null) {
      screen.renderEntity((int) x, (int) y - this.sprite.SIZE, this);
    }
  }

  @Override
  public void calculateMove() throws UnsupportedAudioFileException, LineUnavailableException, IOException {
    // TODO: Tính toán hướng đi và di chuyển Enemy theo _ai và cập nhật giá trị cho _direction
    // TODO: sử dụng canMove() để kiểm tra xem có thể di chuyển tới điểm đã tính toán hay không
    // TODO: sử dụng move() để di chuyển
    // TODO: nhớ cập nhật lại giá trị cờ _moving khi thay đổi trạng thái di chuyển
    int xa = 0, ya = 0;

    if (steps <= 0) {
      direction = ai.calculateDirection();
      steps = MAX_STEPS;
    }

    if (direction == 0) {
      ya--;
    }
    if (direction == 2) {
      ya++;
    }
    if (direction == 3) {
      xa--;
    }
    if (direction == 1) {
      xa++;
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
    if(!isAlive) {
      return;
    }
    y += ya;
    x += xa;
  }

  @Override
  public boolean canMove(double x, double y) throws UnsupportedAudioFileException, LineUnavailableException, IOException {
    double xr = x, yr = y - 16;

    if (direction == 0) {
      yr += sprite.getSize() - 1;
      xr += sprite.getSize() / 2;
    }
    if (direction == 1) {
      yr += sprite.getSize() / 2;
      xr += 1;
    }
    if (direction == 2) {
      xr += sprite.getSize()/2;
      yr += 1;
    }
    if (direction == 3) {
      xr += sprite.getSize() -1;
      yr += sprite.getSize()/2;
    }

    int xx = Coordinates.pixelToTile(xr) + (int)x;
    int yy = Coordinates.pixelToTile(yr) + (int)y;

    Entity a = board.getEntity(xx, yy, this);
    return a.collided(this);
  }

  @Override
  public boolean collided(Entity entity) {
    return false;
  }

  @Override
  public void kill() throws UnsupportedAudioFileException, LineUnavailableException, IOException {
    if(!isAlive) {
      return;
    }
    isAlive = false;

    this.board.addPoints(points);

    Notification notification = new Notification("+" + points,
        getXNotification(), getYNotification(), 2, Color.white, 14);
    board.addNotification(notification);
    Sound.play("aaa");
  }


  @Override
  protected void afterKilled() {
    if (time_left > 0) {
      --time_left;
    } else {
      if (finalAnimation > 0) {
        --finalAnimation;
      } else {
        remove();
      }
    }
  }

  protected abstract void chooseSprite();
}
