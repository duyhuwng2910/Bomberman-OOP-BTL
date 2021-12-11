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
    protected LinkedList<Entity> entityLinkedList = new LinkedList<>();

    public LayeredEntity(int x, int y, Entity... entities) {
        this.x = x;
        this.y = y;

        for (int i = 0; i < entities.length; i++) {
            entityLinkedList.add(entities[i]);
            if (i > 1) {
                if (entities[i] instanceof DestroyableTile) {
                    ((DestroyableTile) entities[i]).addBelowSprite(entities[i - 1].getSprite());
                }
            }
        }
    }

    @Override
    public void update() throws UnsupportedAudioFileException, LineUnavailableException, IOException {
        clearRemoved();
        getTopEntity().update();
    }

    @Override
    public void render(Screen screen) {
        getTopEntity().render(screen);
    }

    public Entity getTopEntity() {
        return entityLinkedList.getLast();
    }

    private void clearRemoved() {
        Entity top = getTopEntity();
        if (top.isRemoved()) {
            entityLinkedList.removeLast();
        }
    }

    public void addBeforeTop(Entity entity) {
        entityLinkedList.add(entityLinkedList.size() - 1, entity);
    }

    @Override
    public boolean collided(Entity entity) throws UnsupportedAudioFileException, LineUnavailableException, IOException {
        return getTopEntity().collided(entity);
    }

}
