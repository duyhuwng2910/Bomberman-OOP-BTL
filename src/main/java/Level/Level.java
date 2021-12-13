package main.java.Level;

import main.java.Board;
import main.java.Exception.LoadLevelException;

/**
 * Class trừu tượng phục vụ quá trình
 * load level từ tệp cấu hình.
 */
public abstract class Level {
	protected int width, height, level;
	protected String[] lineTiles;
	protected Board board;

	public Level(String path, Board board) throws LoadLevelException {
		loadLevel(path);
		this.board = board;
	}

	public abstract void loadLevel(String path) throws LoadLevelException;
	
	public abstract void createEntities();

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}

	public int getLevel() {
		return level;
	}
}
