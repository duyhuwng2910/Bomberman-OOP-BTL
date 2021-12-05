package main.java.Entities;

import main.java.Graphics.Render;
import main.java.Graphics.Screen;
import main.java.Graphics.Sprite;
import main.java.Level.Coordinates;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.IOException;

public abstract class Entity implements Render {
    //Tọa độ X tính từ góc trái trên trong Canvas
    protected double x;

    //Tọa độ Y tính từ góc trái trên trong Canvas
    protected double y;

    protected boolean removed = false;

    protected Sprite sprite;

    public Entity(double x, double y, Sprite sprite) {
        this.x = x;
        this.y = y;
        this.sprite = sprite;
    }

  protected Entity() {
  }

  /**
     * Phương thức render được gọi liên tục trong vòng lặp game
     * để cập nhật hình ảnh của các entity theo trạng thái.
     */
    public abstract void render(Screen screen);

    /**
     * Phương thức update được gọi liên tục trong vòng lặp game
     * để xử lý sự kiện và cập nhật trạng thái của toàn bộ Entity.
     */
    public abstract void update() throws UnsupportedAudioFileException, LineUnavailableException, IOException;

    /**
     * Phương thức collied dùng để xử lý va chạm của 2 entity.
     */
    public abstract boolean collided(Entity entity) throws UnsupportedAudioFileException, LineUnavailableException, IOException;

    public void remove() {
        removed = true;
    }

    public boolean isRemoved() {
        return removed;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public Sprite getSprite() {
        return sprite;
    }

    public int getXTile() {
        return Coordinates.pixelToTile(x + sprite.SIZE / 2);
    }

    public int getYTile() {
        return Coordinates.pixelToTile(y - sprite.SIZE / 2);
    }
}
