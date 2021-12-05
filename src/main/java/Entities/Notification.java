package main.java.Entities;

import java.awt.Color;
import main.java.Graphics.Screen;

/**
 * Class dùng để hiện thông báo về các sự kiện
 * trong game như ăn điểm, tiêu diệt enemy...
 */
public class Notification extends Entity {
  private String notification;
  private int duration;
  private Color color;
  private int size;

  public Notification(String notification, double x, double y,
      int duration, Color color, int size) {
    this.x = x;
    this.y = y;
    this.notification = notification;
    this.duration = duration * 60;
    this.color = color;
    this.size = size;
  }

  public String getNotification() {
    return notification;
  }

  public int getDuration() {
    return duration;
  }

  public void setDuration(int duration) {
    this.duration = duration;
  }

  public Color getColor() {
    return color;
  }

  public int getSize() {
    return size;
  }

  @Override
  public void render(Screen screen) {}

  @Override
  public void update() {}

  @Override
  public boolean collided(Entity entity) {
    return true;
  }
}
