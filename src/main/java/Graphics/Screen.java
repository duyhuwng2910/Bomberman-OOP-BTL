package main.java.Graphics;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;

import main.java.Board;
import main.java.Game;
import main.java.Entities.Entity;
import main.java.Entities.dynamicEntities.Bomber;

public class Screen {
    protected int width, height;
    public int[] pixels;
    protected int transparentColor = 0xffff00ff;

    public static int xOffset = 0, yOffset = 0;

    public Screen(int width, int height) {
        this.width = width;
        this.height = height;
        pixels = new int[width * height];
    }

    public void clear() {
        for (int i = 0; i < pixels.length; i++) {
            pixels[i] = 0;
        }
    }

    public void renderEntity(int xp, int yp, Entity entity) {
        xp -= xOffset;
        yp -= yOffset;
        for (int y = 0; y < entity.getSprite().getSize(); y++) {
            int ya = y + yp;
            for (int x = 0; x < entity.getSprite().getSize(); x++) {
                int xa = x + xp;

                if (xa < -entity.getSprite().getSize() || xa >= width || ya < 0 || ya >= height) {
                    break;
                }
                if (xa < 0) {
                    xa = 0;
                }
                int color = entity.getSprite().getPixel(x + y * entity.getSprite().getSize());

                if (color != transparentColor) {
                    pixels[xa + ya * width] = color;
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

                if (xa < -entity.getSprite().getSize() || xa >= width || ya < 0 || ya >= height) {
                    break;
                }
                if (xa < 0) {
                    xa = 0;
                }
                int color = entity.getSprite().getPixel(x + y * entity.getSprite().getSize());

                if (color != transparentColor) {
                    pixels[xa + ya * width] = color;
                }
                else {
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
        if (bomber == null) {
            return 0;
        }
        int temp = xOffset;
        double BomberX = bomber.getX() / 16;
        double complement = 0.5;
        int firstBreakpoint = board.getWidth() / 4;
        int lastBreakpoint = board.getWidth() - firstBreakpoint;

        if (BomberX > firstBreakpoint + complement && BomberX < lastBreakpoint - complement) {
            temp = (int) bomber.getX() - (Game.WIDTH / 2);
        }

        return temp;
    }
    /**
     * Màn hình trò chơi.
     */
    public void drawEndGame(Graphics g, int points, String record) {
        g.setColor(Color.black);
        g.fillRect(0, 0, getRealWidth(), getRealHeight());

        Font font = new Font("Arial", Font.PLAIN, 20 * Game.SCALE);
        g.setFont(font);
        g.setColor(Color.white);
        drawCenteredString("TRÒ CHƠI KẾT THÚC", getRealWidth(), getRealHeight() - 10, g);

        font = new Font("Arial", Font.PLAIN, 10 * Game.SCALE);
        g.setFont(font);
        g.setColor(Color.orange);
        drawCenteredString("ĐIỂM SỐ CỦA BẠN: " + points, getRealWidth(), getRealHeight() + (Game.TILES_SIZE * 2) * Game.SCALE - 10, g);

        font = new Font("Arial", Font.PLAIN, 5 * Game.SCALE);
        g.setFont(font);
        g.setColor(Color.pink);
        drawCenteredString(record, getRealWidth(), getRealHeight() + 2 * (Game.TILES_SIZE * 2) * Game.SCALE - 10, g);
    }

    public void drawChangeLevel(Graphics g, int level) {
        g.setColor(Color.black);
        g.fillRect(0, 0, getRealWidth(), getRealHeight());
        Font font = new Font("Arial", Font.PLAIN, 20 * Game.SCALE);
        g.setFont(font);
        g.setColor(Color.white);
        drawCenteredString("MÀN " + level, getRealWidth(), getRealHeight(), g);
    }

    public void drawPaused(Graphics g) {
        Font font = new Font("Arial", Font.PLAIN, 20 * Game.SCALE);
        g.setFont(font);
        g.setColor(Color.white);
        drawCenteredString("DỪNG GAME", getRealWidth(), getRealHeight(), g);
    }

    public void drawCenteredString(String s, int w, int h, Graphics g) {
        FontMetrics fm = g.getFontMetrics();
        int x = (w - fm.stringWidth(s)) / 2;
        int y = (fm.getAscent() + (h - (fm.getAscent() + fm.getDescent())) / 2);
        g.drawString(s, x, y);
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public int getRealWidth() {
        return width * Game.SCALE;
    }

    public int getRealHeight() {
        return height * Game.SCALE;
    }
}
