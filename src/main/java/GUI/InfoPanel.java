package main.java.GUI;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import main.java.Game;

public class InfoPanel extends JPanel {
	protected JLabel timeLabel;
	protected JLabel pointsLabel;
	protected JLabel livesLabel;

	public InfoPanel(Game game) {
		setLayout(new GridLayout());
		
		timeLabel = new JLabel("Thời gian: " + game.getBoard().getTime());
		timeLabel.setForeground(Color.white);
		timeLabel.setHorizontalAlignment(JLabel.CENTER);
		
		pointsLabel = new JLabel("Điểm số: " + game.getBoard().getPoints());
		pointsLabel.setForeground(Color.white);
		pointsLabel.setHorizontalAlignment(JLabel.CENTER);
		
		livesLabel = new JLabel("Mạng: " + game.getBoard().getLives());
		livesLabel.setForeground(Color.white);
		livesLabel.setHorizontalAlignment(JLabel.CENTER);
		
		add(timeLabel);
		add(pointsLabel);
		add(livesLabel);

		setBackground(Color.black);
		setPreferredSize(new Dimension(0, 40));
	}
	
	public void setTime(int t) {
		timeLabel.setText("Thời gian: " + t);
	}

	public void setPoints(int t) {
		pointsLabel.setText("Điểm số: " + t);
	}

	public void setLives(int t) {
		livesLabel.setText("Mạng: " + t);
	}
	
}
