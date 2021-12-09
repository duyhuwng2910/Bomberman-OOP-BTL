package main.java.Level;

import main.java.Board;
import main.java.Exception.LoadLevelException;

public abstract class LevelLoader implements ILevel {

	protected int width, height, level;
	protected String[] lineTiles;
	protected Board board;

	public LevelLoader(String path, Board board) throws LoadLevelException {
		loadLevel(path);
		this.board = board;
	}

	@Override
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
