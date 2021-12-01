package main.java.Entities.staticEntities;

import javafx.scene.image.Image;
import main.java.Entities.Entity;
import main.java.Graphics.Screen;
import main.java.Graphics.Sprite;

public class Wall extends Tile {

    public Wall(int x, int y, Sprite sprite) {
        super(x, y, sprite);
    }

    @Override
    public void render(Screen screen) {

    }

    @Override
    public void update() {

    }

    @Override
    public boolean collided(Entity entity) {
        return false;
    }
}
