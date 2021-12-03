package main.java.GUI;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import main.java.Game;

public class InfoPanel extends JPanel {
  private JLabel timeLabel;
  private JLabel pointsLabel;

  public InfoPanel(Game game) {
    setLayout(new GridLayout());

    timeLabel = new JLabel("Time: " + game.getBoard().getTime());
    timeLabel.setForeground(Color.white);
    timeLabel.setHorizontalAlignment(JLabel.CENTER);

    pointsLabel = new JLabel("Points: " + game.getBoard().getPoints());
    pointsLabel.setForeground(Color.white);
    pointsLabel.setHorizontalAlignment(JLabel.CENTER);

    add(timeLabel);
    add(pointsLabel);

    setBackground(Color.black);
    setPreferredSize(new Dimension(0, 40));
  }

  public void setTime(int t) {
    timeLabel.setText("Time: " + t);
  }

  public void setPoints(int t) {
    pointsLabel.setText("Score: " + t);
  }
}
