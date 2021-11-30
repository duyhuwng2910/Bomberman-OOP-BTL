package main.java.Entities;

import java.awt.Color;
import javafx.stage.Screen;

/**
 * Class dùng để hiện thông báo về các sự kiện
 * trong game như ăn điểm, tiêu diệt enemy...
 */
public class Notification extends Entity {
  private String notification;
  private int duration;
  private java.awt.Color color;
  private int size;

  public Notification(String notification, double x, double y,
      int duration, java.awt.Color color, int size) {
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

  public java.awt.Color getColor() {
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
  public void render(main.java.Graphics.Screen screen) {

  }

  @Override
  public void update() {

  }

  @Override
  public boolean collided(Entity entity) {
    return true;
  }
}
