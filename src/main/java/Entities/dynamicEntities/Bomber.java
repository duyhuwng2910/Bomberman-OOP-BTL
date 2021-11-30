package main.java.Entities.dynamicEntities;

import java.util.ArrayList;
import java.util.List;
import main.java.Board;
import main.java.Entities.Entity;
import main.java.Entities.bomb.Bomb;
import main.java.Entities.staticEntities.Items.Items;
import main.java.Graphics.Sprite;
import main.java.Input.Keyboard;
import main.java.Graphics.Screen;

public class Bomber extends Character {
  protected Keyboard keyboard;
  private List<Bomb> bombList;
  public static List<Items> itemsList = new ArrayList<Items>();
  protected int timeBetweenPutBombs = 7500;

  public Bomber(double x, double y, Board board) {
    this.x = x;
    this.y = y;
    this.board = board;
    this.sprite = Sprite.player_right;
  }

  @Override
  public void update() {
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
    detectPlaceofBombs();
  }


  @Override
  public void render(Screen screen) {
    calculateXOffset();
    if (isAlive) {
    }
  }

  private void calculateXOffset() {
  }

  private void detectPlaceofBombs() {
  }

  private void clearBombs() {
  }

  @Override
  public boolean collided(Entity entity) {
    return false;
  }
}
