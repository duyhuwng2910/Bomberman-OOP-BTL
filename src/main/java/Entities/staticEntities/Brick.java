package main.java.Entities.staticEntities;

import main.java.Entities.Entity;
import main.java.Entities.Bomb.Flame;
import main.java.Entities.dynamicEntities.Enemy.Kondoria;
import main.java.Graphics.Screen;
import main.java.Graphics.Sprite;
import main.java.Level.Coordinates;

/**
 * Class đại diện cho thực thể tĩnh Brick.
 */
public class Brick extends Tile {
    private final int MAX_ANIMATE = 7500;
    private int animate = 0;
    protected boolean destroyed = false;
    protected int timeToDisappear = 20;
    protected Sprite belowSprite = Sprite.grass;
    public Brick(int x, int y, Sprite sprite) {
        super(x, y, sprite);
    }

    @Override
    public void render(Screen screen) {
        int x = Coordinates.tileToPixel(this.x);
        int y = Coordinates.tileToPixel(this.y);
        if (destroyed) {
            sprite = movingSprite(Sprite.brick_exploded, Sprite.brick_exploded1, Sprite.brick_exploded2);
            screen.renderEntityWithBelowSprite(x, y, this, belowSprite);
        } else {
            screen.renderEntity(x, y, this);
        }
    }

    @Override
    public void update() {
        if (destroyed) {
            if (animate < MAX_ANIMATE) {
                animate++;
            } else {
                animate = 0;
            }
            if (timeToDisappear > 0) {
                timeToDisappear--;
            } else {
                remove();
            }
        }
    }

    public void destroy() {
        destroyed = true;
    }

    public void addBelowSprite(Sprite sprite) {
        belowSprite = sprite;
    }

    protected Sprite movingSprite(Sprite normal, Sprite x1, Sprite x2) {
        int calc = animate % 30;

        if (calc < 10) {
            return normal;
        }
        if (calc < 20) {
            return x1;
        }
        return x2;
    }

    @Override
    public boolean collided(Entity entity) {
        if (entity instanceof Flame) {
            destroy();
        }

        if (entity instanceof Kondoria) {
            return true;
        }
        return false;
    }
}
