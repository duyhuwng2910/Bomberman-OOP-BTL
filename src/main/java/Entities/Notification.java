package main.java.Entities;

import javafx.scene.paint.Color;

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

  public void setNotification(String notification) {
    this.notification = notification;
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

  public void setColor(Color color) {
    this.color = color;
  }

  public int getSize() {
    return size;
  }

  public void setSize(int size) {
    this.size = size;
  }

  @Override
  public void update() {

  }

  @Override
  public boolean collided(Entity entity) {
    return true;
  }
}
