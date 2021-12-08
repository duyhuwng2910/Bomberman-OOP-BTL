package main.java.GUI;

import java.awt.BorderLayout;
import java.io.IOException;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.JFrame;
import javax.swing.JPanel;
import main.java.GUI.menu.Menu;
import main.java.Game;

public class Frame extends JFrame {
  public GamePanel gamePanel;
  private JPanel jPanel;
  private InfoPanel infoPanel;

  private Game game;

  public Frame() throws UnsupportedAudioFileException, LineUnavailableException, IOException {
    setJMenuBar(new Menu(this));
    jPanel = new JPanel(new BorderLayout());
    gamePanel = new GamePanel(this);
    infoPanel = new InfoPanel(gamePanel.getGame());

    jPanel.add(infoPanel, BorderLayout.PAGE_START);
    jPanel.add(gamePanel, BorderLayout.PAGE_END);

    game = gamePanel.getGame();

    add(jPanel);
    setResizable(false);
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    pack();
    setLocationRelativeTo(null);
    setVisible(true);
    game.start();
  }

  public void pauseGame() {
    game.getBoard().gamePause();
  }

  public void resumeGame() {
    game.getBoard().gameResume();
  }

  public void setTime(int time) {
    infoPanel.setTime(time);
  }

  public void setPoints(int points) {
    infoPanel.setPoints(points);
  }

  public void setLives(int lives) {
    infoPanel.setLives(lives);
  }
}
