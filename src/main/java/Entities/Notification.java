package main.java.Entities;

import java.awt.Color;

import main.java.Graphics.Screen;

public class Notification extends Entity {

	protected String notification;
	protected int duration;
	protected Color color;
	protected int size;
	
	public Notification(String message, double x, double y, int duration, Color color, int size) {
		this.x =x;
		this.y = y;
		notification = message;
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

	public String getMessage() {
		return notification;
	}

	public Color getColor() {
		return color;
	}

	public int getSize() {
		return size;
	}

	@Override
	public void update() {
	}

	@Override
	public void render(Screen screen) {
	}

	@Override
	public boolean collide(Entity e) {
		return true;
	}
	
	
}
