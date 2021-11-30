package main.java.Graphics;

import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import javafx.scene.paint.Color;
import main.java.Board;
import main.java.Entities.Entity;
import main.java.Entities.dynamicEntities.Bomber;
import main.java.Game;

public class Screen {
  protected int width, height;
  public int[] pixels;
  private int transparentColor = Color.BLACK.hashCode();

  public static int xOffset = 0, yOffset = 0;

  public Screen(int width, int height) {
    this.width = width;
    this.height = height;
    this.pixels = new int[width * height];
  }

  public void clear() {
    for (int i = 0; i < pixels.length; i++) {
      pixels[i] = 0;
    }
  }

  public void renderEntity(int xp, int yp, Entity entity) { //save entity pixels
    xp -= xOffset;
    yp -= yOffset;
    for (int y = 0; y < entity.getSprite().getSize(); y++) {
      int ya = y + yp; //add offset

      for (int x = 0; x < entity.getSprite().getSize(); x++) {
        int xa = x + xp; //add offset
        if (xa < -entity.getSprite().getSize()
            || xa >= this.width || ya < 0 || ya >= this.height) {
            break; //fix black margins
        }
        if (xa < 0) {
          xa = 0; //start at 0 from left
        }
        int color_code = entity.getSprite().getPixel(x + y * entity.getSprite().getSize());
        if (color_code != transparentColor) {
          pixels[xa + ya * width] = color_code;
        }
      }
    }
  }

  public void renderEntityWithBelowSprite(int xp, int yp, Entity entity, Sprite below) {
    xp -= xOffset;
    yp -= yOffset;

    for (int y = 0; y < entity.getSprite().getSize(); y++) {
      int ya = y + yp;

      for (int x = 0; x < entity.getSprite().getSize(); x++) {
        int xa = x + xp;
        if (xa < -entity.getSprite().getSize()
            || xa >= width || ya < 0 || ya >= height) {
          break; //fix black margins
        }
        if (xa < 0) {
          xa = 0;
        }
        int color_code = entity.getSprite().getPixel(x + y * entity.getSprite().getSize());
        if (color_code != transparentColor) {
          pixels[xa + ya * width] = color_code;
        } else {
          pixels[xa + ya * width] = below.getPixel(x + y * below.getSize());
          }
      }
    }
  }

  public static void setOffset(int xO, int yO) {
    xOffset = xO;
    yOffset = yO;
  }

  public static int calculateXOffset(Board board, Bomber bomber) {
    if(bomber == null) {
      return 0;
    }
    int temp = xOffset;
    double BomberX = bomber.getX() / 16;
    double complement = 0.5;
    int firstBreakpoint = board.getWidth() / 4;
    int lastBreakpoint = board.getWidth() - firstBreakpoint;

    if( BomberX > firstBreakpoint + complement && BomberX < lastBreakpoint - complement) {
      temp = (int)bomber.getX()  - (Game.WIDTH / 2);
    }
    return temp;
  }

  public void drawEndGame(Graphics g, int points) {
    g.setColor(java.awt.Color.black);
    g.fillRect(0, 0, getRealWidth(), getRealHeight());

    Font font = new Font("Arial", Font.PLAIN, 24 * Game.SCALE);
    g.setFont(font);
    g.setColor(java.awt.Color.white);
    drawCenteredString("GAME OVER", getRealWidth(), getRealHeight(), g);

    font = new Font("Arial", Font.PLAIN, 12 * Game.SCALE);
    g.setFont(font);
    g.setColor(java.awt.Color.orange);
    drawCenteredString("POINTS: " + points, getRealWidth(), getRealHeight() + (Game.TILES_SIZE * 2) * Game.SCALE, g);
  }

  public void drawChangeLevel(Graphics g, int level) {
    g.setColor(java.awt.Color.black);
    g.fillRect(0, 0, getRealWidth(), getRealHeight());

    Font font = new Font("Arial", Font.PLAIN, 24 * Game.SCALE);
    g.setFont(font);
    g.setColor(java.awt.Color.white);
    drawCenteredString("LEVEL " + level, getRealWidth(), getRealHeight(), g);
  }

  public void drawPaused(Graphics g) {
    Font font = new Font("Arial", Font.PLAIN, 24 * Game.SCALE);
    g.setFont(font);
    g.setColor(java.awt.Color.white);
    drawCenteredString("PAUSED", getRealWidth(), getRealHeight(), g);

  }

  public void drawCenteredString(String s, int w, int h, Graphics g) {
    FontMetrics fm = g.getFontMetrics();
    int x = (w - fm.stringWidth(s)) / 2;
    int y = (fm.getAscent() + (h - (fm.getAscent() + fm.getDescent())) / 2);
    g.drawString(s, x, y);
  }

  public int getWidth() {
    return this.width;
  }

  public int getHeight() {
    return this.height;
  }

  public int getRealWidth() {
    return this.width * Game.SCALE;
  }

  public int getRealHeight() {
    return this.height * Game.SCALE;
  }
}
