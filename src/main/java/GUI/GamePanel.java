package main.java.GUI;

import java.awt.BorderLayout;
import java.awt.Dimension;
import javax.swing.JPanel;
import main.java.Game;

public class GamePanel extends JPanel {
  private Game game;

  public GamePanel(Frame frame) {
    setLayout(new BorderLayout());
    setPreferredSize(new Dimension(Game.WIDTH * Game.SCALE, Game.HEIGHT * Game.SCALE));
    game = new Game(frame);

    add(game);

    game.setVisible(true);

    setVisible(true);
    setFocusable(true);
  }

  public Game getGame() {
    return game;
  }
}
