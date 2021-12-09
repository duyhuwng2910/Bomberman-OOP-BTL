package main.java.GUI.menu;

import javax.swing.JMenuBar;

import main.java.GUI.Frame;

public class Menu extends JMenuBar {
	
	public Menu(Frame frame) {
		add( new Game(frame) );
		add( new Options(frame) );
	}
	
}
