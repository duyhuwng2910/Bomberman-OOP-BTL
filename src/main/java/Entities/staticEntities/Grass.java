package main.java.Entities.staticEntities;

import main.java.Entities.Entity;
import main.java.Graphics.Sprite;

public class Grass extends Tile {
    public Grass(int x, int y, Sprite sprite) {
        super(x, y, sprite);
    }

    /**
     * thực thể Grass cho phép mọi nhân vật có thể đi qua.
     */
    @Override
    public boolean collided(Entity entity) {
        return true;
    }
}
