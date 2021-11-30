package main.java;

import java.awt.Canvas;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;
import main.java.Graphics.Screen;
import main.java.Input.Keyboard;

public class Game extends Canvas {
  public static final int TILES_SIZE = 16,
      WIDTH = TILES_SIZE * (31 / 2),
      HEIGHT = 13 * TILES_SIZE;

  public static int SCALE = 3;

  public static final String TITLE = "Bomberman";

  private static final int BOMB_RATE = 1;
  private static final int BOMB_RADIUS = 1;
  private static final double BOMBER_SPEED = 1.0;//toc do bomber

  public static final int TIME = 200;
  public static final int POINTS = 0;

  protected static int SCREEN_DELAY = 3;

  protected static int bombRate = BOMB_RATE;
  protected static int bombRadius = BOMB_RADIUS;
  protected static double bomberSpeed = BOMBER_SPEED;


  protected int screenDelay = SCREEN_DELAY;

  private Keyboard input;
  private boolean running = false;
  private boolean paused = true;

  private Board board;
  private Screen screen;
  private Frame frame;

  private BufferedImage image = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
  private int[] pixels = ((DataBufferInt)image.getRaster().getDataBuffer()).getData();

  public Game(Frame frame) {
    this.frame = frame;
    this.frame.setTitle(TITLE);
    this.screen = new Screen(WIDTH, HEIGHT);
    this.input = new Keyboard();
    board = new Board(this, input, screen);
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
    board.renderNotification(g);

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

  private void update() {
    input.update();
    board.update();
  }

  public void start() {
    running = true;
    long  lastTime = System.nanoTime();
    long timer = System.currentTimeMillis();
    final double ns = 1000000000.0 / 60.0; //nanosecond, 60 frames per second
    double delta = 0;
    int frames = 0;
    int updates = 0;
    requestFocus();

    while (running) {
      long now = System.nanoTime();
      delta += (now - lastTime) / ns;
      lastTime = now;
      while (delta >= 1) {
        update();
        updates++;
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

      frames++;

      if (System.currentTimeMillis() - timer > 1000) {
        frame.setTime(board.timeSubtraction());
        frame.setPoints(board.getPoints());

        timer += 1000;

        frame.setTitle(TITLE + " | " + updates + " rate, " + frames + " fps");

        updates = 0;
        frames = 0;

        if (board.getShow() == 2) {
          --screenDelay;
        }
      }
    }
  }

  public static double getBomberSpeed() {
    return bomberSpeed;
  }

  public static int getBombRate() {
    return bombRate;
  }

  public static int getBombRadius() {
    return bombRadius;
  }

  public static void addBomberSpeed(double i) {
    bomberSpeed += i;
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

  public boolean isPaused() {
    return paused;
  }

  public void pause() {
    paused = true;
  }
  public static void setBombRate(int bombRate) {
    Game.bombRate = bombRate;
  }

  public static void setBombRadius(int bombRadius) {
    Game.bombRadius = bombRadius;
  }

  public static void setBomberSpeed(double bomberSpeed) {
    Game.bomberSpeed = bomberSpeed;
  }
}
