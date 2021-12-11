package main.java.GUI;

import java.awt.BorderLayout;
import java.awt.Dimension;
import javax.swing.JPanel;
import main.java.Game;
import main.java.Exception.GameException;

/**
 * Class đại diện cho hình ảnh game.
 */
public class GamePanel extends JPanel {
	private Game game;
	
	public GamePanel(Frame frame) {
		setLayout(new BorderLayout());
		setPreferredSize(new Dimension(Game.WIDTH * Game.SCALE, Game.HEIGHT * Game.SCALE));
		try {
			game = new Game(frame);
			add(game);
			game.setVisible(true);
		} catch (GameException e) {
			e.printStackTrace();
			System.exit(0);
		}
		setVisible(true);
		setFocusable(true);
	}
	
	public Game getGame() {
		return game;
	}
}
