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

public class Game extends Canvas {

	public static final double VERSION = 1.9;
	
	public static final int TILES_SIZE = 16,
							WIDTH = TILES_SIZE * (int)(31 / 2),
							HEIGHT = 13 * TILES_SIZE;

	public static int SCALE = 3;
	
	public static final String TITLE = "Bomberman " + VERSION;

	private static final int BOMBRATE = 1;
	private static final int BOMBRADIUS = 1;
	private static final double PLAYERSPEED = 1.0;
	
	public static final int TIME = 200;
	public static final int POINTS = 0;
	public static final int LIVES = 3;
	
	protected static int SCREENDELAY = 3;
	
	
	//can be modified with bonus
	protected static int bombRate = BOMBRATE;
	protected static int bombRadius = BOMBRADIUS;
	protected static double playerSpeed = PLAYERSPEED;
	
	
	//Time in the level screen in seconds
	protected int screenDelay = SCREENDELAY;
	
	private Keyboard input;
	private boolean running = false;
	private boolean paused = true;
	
	private Board board;
	private Screen screen;
	private Frame frame;

	private BufferedImage image = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
	private int[] pixels = ((DataBufferInt)image.getRaster().getDataBuffer()).getData();
	
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
		if(bs == null) {
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
		board.renderMessages(g);

		g.dispose();
		bs.show();
	}
	
	private void renderScreen() {
		BufferStrategy bs = getBufferStrategy();
		if(bs == null) {
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
		int frames = 0;
		int updates = 0;
		requestFocus();
		while(running) {
			long now = System.nanoTime();
			delta += (now - lastTime) / ns;
			lastTime = now;
			while(delta >= 1) {
				update();
				updates++;
				delta--;
			}
			
			if(paused) {
				if(screenDelay <= 0) {
					board.setShow(-1);
					paused = false;
				}
					
				renderScreen();
			} else {
				renderGame();
			}
				
			
			frames++;
			if(System.currentTimeMillis() - timer > 1000) {
				frame.setTime(board.subtractTime());
				frame.setPoints(board.getPoints());
				frame.setLives(board.getLives());
				timer += 1000;
				frame.setTitle(TITLE + " | " + updates + " rate, " + frames + " fps");
				updates = 0;
				frames = 0;
				
				if(board.getShow() == 2)
					--screenDelay;
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

	public int getScreenDelay() {
		return screenDelay;
	}
	
	public void decreaseScreenDelay() {
		screenDelay--;
	}
	
	public void resetScreenDelay() {
		screenDelay = SCREENDELAY;
	}

	public Keyboard getInput() {
		return input;
	}
	
	public Board getBoard() {
		return board;
	}
	
	public void run() {
		running = true;
		paused = false;
	}
	
	public void stop() {
		running = false;
	}

	public boolean isRunning() {
		return running;
	}
	
	public boolean isPaused() {
		return paused;
	}
	
	public void pause() {
		paused = true;
	}
	
}
