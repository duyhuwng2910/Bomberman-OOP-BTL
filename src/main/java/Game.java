package main.java;

import java.awt.Canvas;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;
import java.io.IOException;
import main.java.Exception.GameException;
import main.java.Graphics.Screen;
import main.java.GUI.Frame;
import main.java.Input.Keyboard;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

/**
 * Class thực hiện toàn bộ quá trình chơi game.
 */
public class Game extends Canvas {
	public static final int TILES_SIZE = 16, WIDTH = TILES_SIZE * (31 / 2), HEIGHT = 13 * TILES_SIZE;

	public static int SCALE = 3;
	
	public static final String TITLE = "Bomberman ";

	private static final int BOMB_RATE = 1;
	private static final int BOMB_RADIUS = 1;
	private static final double PLAYER_SPEED = 1.0;
	
	public static final int TIME = 200;
	public static final int POINTS = 0;
	public static final int LIVES = 3;
	
	protected static int SCREEN_DELAY = 3;

	protected static int bombRate = BOMB_RATE;
	protected static int bombRadius = BOMB_RADIUS;
	protected static double playerSpeed = PLAYER_SPEED;

	protected int screenDelay = SCREEN_DELAY;

	protected Keyboard input;
	protected boolean running = false;
	protected boolean paused = true;

	protected Board board;
	protected Screen screen;
	protected Frame frame;

	protected BufferedImage image = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
	protected int[] pixels = ((DataBufferInt)image.getRaster().getDataBuffer()).getData();
	
	public Game(Frame frame) throws GameException {
		this.frame = frame;
		this.frame.setTitle(TITLE);
		screen = new Screen(WIDTH, HEIGHT);
		input = new Keyboard();
		board = new Board(this, input, screen);
		addKeyListener(input);
	}

	private void renderGame() {
		BufferStrategy bs = getBufferStrategy();
		if (bs == null) {
			createBufferStrategy(3);
			return;
		}
		screen.clear();
		board.render(screen);
		
		for (int i = 0; i < pixels.length; i++) {
			pixels[i] = screen.pixels[i];
		}
		Graphics g = bs.getDrawGraphics();
		g.drawImage(image, 0, 0, getWidth(), getHeight(), null);
		board.renderNotification(g);
		g.dispose();
		bs.show();
	}
	
	private void renderScreen() {
		BufferStrategy bs = getBufferStrategy();
		if (bs == null) {
			createBufferStrategy(3);
			return;
		}
		screen.clear();
		Graphics g = bs.getDrawGraphics();
		board.drawScreen(g);
		g.dispose();
		bs.show();
	}

	private void update() throws UnsupportedAudioFileException, LineUnavailableException, IOException {
		input.update();
		board.update();
	}
	
	public void start() throws UnsupportedAudioFileException, LineUnavailableException, IOException {
		running = true;
		long  lastTime = System.nanoTime();
		long timer = System.currentTimeMillis();
		final double ns = 1000000000.0 / 60.0;
		double delta = 0;
		requestFocus();

		while (running) {
			long now = System.nanoTime();
			delta += (now - lastTime) / ns;
			lastTime = now;
			while (delta >= 1) {
				update();
				delta--;
			}
			
			if (paused) {
				if (screenDelay <= 0) {
					board.setShow(-1);
					paused = false;
				}
				renderScreen();
			} else {
				renderGame();
			}
			if (System.currentTimeMillis() - timer > 1000) {
				frame.setTime(board.subtractTime());
				frame.setPoints(board.getPoints());
				frame.setLives(board.getLives());
				timer += 1000;

        if (board.getShow() == 2) {
          --screenDelay;
				}
			}
		}
	}

	public static double getPlayerSpeed() {
		return playerSpeed;
	}
	
	public static int getBombRate() {
		return bombRate;
	}
	
	public static int getBombRadius() {
		return bombRadius;
	}
	
	public static void addPlayerSpeed(double i) {
		playerSpeed += i;
	}
	
	public static void addBombRadius(int i) {
		bombRadius += i;
	}
	
	public static void addBombRate(int i) {
		bombRate += i;
	}
	
	public void resetScreenDelay() {
		screenDelay = SCREEN_DELAY;
	}
	
	public Board getBoard() {
		return board;
	}
	
	public void run() {
		running = true;
		paused = false;
	}
	
	public boolean isPaused() {
		return paused;
	}
	
	public void pause() {
		paused = true;
	}
	
}
