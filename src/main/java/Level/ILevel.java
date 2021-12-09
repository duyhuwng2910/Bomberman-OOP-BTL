package main.java.Level;


import main.java.Exception.LoadLevelException;

public interface ILevel {

	public void loadLevel(String path) throws LoadLevelException;
}
