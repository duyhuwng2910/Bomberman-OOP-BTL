package main.java.Level;

import main.java.Game;

public class Coordinates {
  public static int pixelToTile(double i) {
    return (int)(i / Game.TILES_SIZE);
  }

  public static int tileToPixel(int i) {
    return i * Game.TILES_SIZE;
  }

  public static int tileToPixel(double i) {
    return (int)(i * Game.TILES_SIZE);
  }
}
