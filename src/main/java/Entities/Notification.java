package main.java.Entities;

import java.awt.Color;
import main.java.Graphics.Screen;

/**
 * Class đại diện cho các thông báo sự kiện
 * trong quá trình game chạy.
 */
public class Notification extends Entity {
	protected String notification;
	protected int duration;
	protected Color color;
	protected int size;
	
	public Notification(String notification, double x, double y, int duration, Color color, int size) {
		this.x =x;
		this.y = y;
		this.notification = notification;
		this.duration = duration * 60;
		this.color = color;
		this.size = size;
	}

	public int getDuration() {
		return duration;
	}

	public void setDuration(int duration) {
		this.duration = duration;
	}

	public String getNotification() {
		return notification;
	}

	public Color getColor() {
		return color;
	}

	public int getSize() {
		return size;
	}

	@Override
	public void update() {}

	@Override
	public void render(Screen screen) {}

	@Override
	public boolean collided(Entity e) {
		return true;
	}
}
