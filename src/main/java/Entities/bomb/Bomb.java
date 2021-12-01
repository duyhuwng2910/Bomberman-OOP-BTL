package main.java.Entities.bomb;

import main.java.Board;
import main.java.Entities.AnimatedEntity;
import main.java.Entities.Entity;
import main.java.Entities.dynamicEntities.Bomber;
import main.java.Entities.dynamicEntities.Character;
import main.java.Game;
import main.java.Graphics.Screen;
import main.java.Graphics.Sprite;
import main.java.Level.Coordinates;
import main.java.Sound.Sound;

public class Bomb extends AnimatedEntity {
    protected double timeToExplode = 150; //2.5 giây - thời gian phát nổ
    public int timeAfter = 40;// thời gian sau khi bom nổ

    protected Board board;
    protected Flame[] flames;
    protected boolean exploded = false;
    protected boolean allowedToPass = true;

    public Bomb(int x, int y, Board board) {
        this.x = x;
        this.y = y;
        this.board = board;
        this.sprite = Sprite.bomb;
    }

    @Override
    public void render(Screen screen) {
        if (exploded) {
            sprite =  Sprite.bomb_exploded2;
            renderFlames(screen);
        } else {
            sprite = Sprite.movingSprite(Sprite.bomb, Sprite.bomb_1, Sprite.bomb_2, animated, 60);
        }

        int xt = (int) this.x << 4;
        int yt = (int) this.y << 4;
        screen.renderEntity(xt, yt, this);
    }

    private void renderFlames(Screen screen) {
        for (int i = 0; i < flames.length; i++) {
            flames[i].render(screen);
        }
    }

    private void updateFlames() {
        for (int i = 0; i < flames.length; i++) {
            flames[i].update();
        }
    }

    /**
     * Phương thức xử lý sự kiện Bomb nổ.
     */
    private void explode() {
        exploded = true;
        allowedToPass = true;

        // Tiến hành xử lý sự kiện khi Character
        // đứng trong khu vực chịu ảnh hưởng của Bomb
        Character character = board.getCharacterAtExcluding((int) this.x, (int) this.y, null);

        if (character != null) {
            character.kill();
        }
        flames = new Flame[4];
        for (int i = 0; i < 4; i++) {
            flames[i]  = new Flame((int) this.x, (int) this.y,
                Game.getBombRadius(), this.board);
        }
        // Hiệu ứng âm thanh Bomb nổ
        Sound.play("");
    }

    public void explodedTime() {
        timeToExplode = 0;
    }

    @Override
    public void update() {
        if (timeToExplode > 0) {
            timeToExplode--;
        } else {
            if (!exploded) {
                explode();
            } else {
                updateFlames();
            }

            if (timeAfter > 0) {
                timeAfter--;
            } else {
                remove();
            }
        }

        animate();
    }

    @Override
    public boolean collided(Entity entity) {
      // Xử lý khi Bomber đi ra khỏi khu vực đặt bomb
      if (entity instanceof Bomber) {
          double X = entity.getX() - Coordinates.tileToPixel(getX());
          double Y = entity.getY() - Coordinates.tileToPixel(getY());

          if (!(X >= -10 && X < 16 && Y >= 1 && Y <= 28)) {
              allowedToPass = false;
          }
          return allowedToPass;
      }
      // Xử lý va chạm với hiệu ứng flame của Bomb khác
      if (entity instanceof Flame) {
          explodedTime();
          return true;
      }
      return false;
    }

    public FlameSegment flameAt(int x, int y) {
        if (!exploded) {
            return null;
        }

        for (int i = 0; i < flames.length; i++) {
            if (flames[i] == null) {
                return null;
            }
            FlameSegment fs = flames[i].flameSegmentAt(x, y);

            if (fs != null) {
                return fs;
            }
        }
        return null;
    }
}
