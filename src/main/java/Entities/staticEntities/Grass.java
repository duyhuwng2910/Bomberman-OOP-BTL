package main.java.Entities.staticEntities;

import javafx.scene.image.Image;
import main.java.Entities.Entity;
import main.java.Graphics.Sprite;

public class Grass extends Entity {

    public Grass(int x, int y, Image img, Sprite sprite) {
        super(x, y, img, sprite);
    }

    @Override
    public void update() {

    }

    @Override
    public boolean collided(Entity entity) {
        return false;
    }
}
