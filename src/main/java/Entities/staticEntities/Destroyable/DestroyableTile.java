package main.java.Entities.staticEntities.Destroyable;

import main.java.Entities.Entity;
import main.java.Entities.Bomb.Flame;
import main.java.Entities.staticEntities.Tile;
import main.java.Graphics.Sprite;

public class DestroyableTile extends Tile {

    private final int MAX_ANIMATE = 7500;
    private int animate = 0;
    protected boolean destroyed = false;
    protected int timeToDisapear = 20;
    protected Sprite belowSprite = Sprite.grass;

    public DestroyableTile(int x, int y, Sprite sprite) {
        super(x, y, sprite);
    }

    @Override
    public void update() {
        if (destroyed) {
            if (animate < MAX_ANIMATE) {
                animate++;
            } else {
                animate = 0;
            }
            if (timeToDisapear > 0) {
                timeToDisapear--;
            } else {
                remove();
            }
        }
    }

    public void destroy() {
        destroyed = true;
    }

    @Override
    public boolean collide(Entity entity) {
        if (entity instanceof Flame) {
            destroy();
        }
        return false;
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
}
