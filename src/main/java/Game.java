package main.java;

import java.awt.Canvas;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
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
	public static final double VERSION = 1.0;
	public static final int TILES_SIZE = 16, WIDTH = TILES_SIZE * (31 / 2), HEIGHT = 13 * TILES_SIZE;

	public static int SCALE = 3;
	
	public static final String TITLE = "Bomberman";

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

	private static final ArrayList<String> topScoresToString = new ArrayList<>();
	private static final ArrayList<Integer> topScores = new ArrayList<>();

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
		board.renderNotifications(g);
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
		importTopScoresFromFile();
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

	public void importTopScoresFromFile() {
		try {
			FileReader fr = new FileReader("Top Scores.txt");
			BufferedReader br = new BufferedReader(fr);
			String tsline;
			int i = 0;

			while(i < 5) {
				tsline = br.readLine();
				if (tsline == null) {
					break;
				}
				topScoresToString.add(tsline);
				i++;
			}
			for (int j = 0; j < 5; j++) {
				topScores.add(Integer.valueOf(topScoresToString.get(j)));
			}
			Collections.sort(topScores);
			br.close();
			fr.close();
		} catch (Exception ignored){
			System.out.println("Error!");
		}
	}

	public void exportToTopScoresFile() {
		try {
			topScoresToString.clear();
			for (int i = 0; i < 5; i++) {
				topScoresToString.add(String.valueOf(topScores.get(4 - i)));
			}
			FileWriter fw = new FileWriter("Top Scores.txt");
			BufferedWriter bw = new BufferedWriter(fw);
			for (int i = 0; i < 5; i++) {
				bw.write(topScoresToString.get(4 - i));
				bw.newLine();
			}
			bw.close();
			fw.close();
		} catch (Exception ignored) {
			System.out.println("Error!");
		}
	}

	public ArrayList<Integer> getTopScores() {
		return topScores;
	}

	public static String getTopScoresList() {
		String result = "Đây là bảng điểm của chúng ta: \n";
		Collections.sort(topScores);
		for (int i = topScores.size() - 1; i >= 0; i--) {
			result = result + "Top " + (topScores.size() - i) + ": " + topScores.get(i) + "\n";
		}
		return result;
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
