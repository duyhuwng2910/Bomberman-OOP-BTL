package main.java.Entities.staticEntities;

import main.java.Entities.Entity;
import main.java.Graphics.Sprite;

/**
 * thực thể Grass cho phép mọi nhân vật có thể đi qua.
 */
public class Grass extends Tile {
    public Grass(int x, int y, Sprite sprite) {
        super(x, y, sprite);
    }

    @Override
    public boolean collided(Entity entity) {
        return true;
    }
}
