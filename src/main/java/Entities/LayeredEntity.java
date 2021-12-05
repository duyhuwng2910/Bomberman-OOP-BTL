package main.java.Entities;

import java.io.IOException;
import java.util.LinkedList;
import main.java.Entities.staticEntities.Destroyable.DestroyableTile;
import main.java.Graphics.Screen;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

/**
 * Class dùng để chứa và quản lý nhiều entity tại cùng một vị trí
 * Ví dụ: tại vị trí có chứa item flash, có 3 entity [Grass, flashItem, Brick]
 */
public class LayeredEntity extends Entity {
  protected LinkedList<Entity> entities = new LinkedList<>();

  public LayeredEntity(double x, double y, Entity ... entity) {
    this.x = x;
    this.y = y;
    for (int i = 0; i < entities.size(); i++) {
      entities.add(entity[i]);
      if (i > 1) {
        if (entity[i] instanceof DestroyableTile) {
          ((DestroyableTile)entity[i]).addBelowImage(entity[i - 1].getSprite());
        }
      }
    }
  }

  @Override
  public void render(Screen screen) {
    getTopEntity().render(screen);
  }

  @Override
  public void update() throws UnsupportedAudioFileException, LineUnavailableException, IOException {
    clearRemoved();
    getTopEntity().update();
  }

  public Entity getTopEntity() {
    return entities.getLast();
  }

  private void clearRemoved() {
    Entity topEntity = getTopEntity();
    if(topEntity.isRemoved())  {
      entities.removeLast();
    }
  }

  public void addBeforeTop(Entity entity) {
    entities.add(entities.size() - 1, entity);
  }

  @Override
  public boolean collided(Entity entity) throws UnsupportedAudioFileException, LineUnavailableException, IOException {
    return getTopEntity().collided(entity);
  }
}
