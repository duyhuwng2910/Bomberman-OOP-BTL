package main.java.GUI.menu;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.KeyStroke;
import main.java.GUI.Frame;
import main.java.GUI.InfoCenter;

public class Game extends JMenu {
	public Frame frame1;

	public Game(Frame frame) {
		super("Trò chơi");
		this.frame1 = frame;

		JMenuItem newGame = new JMenuItem("Trò chơi mới");
		newGame.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N, ActionEvent.CTRL_MASK));
		newGame.addActionListener(new MenuActionListener(frame));
		add(newGame);

		JMenuItem scores = new JMenuItem("Điểm cao");
		scores.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_T, ActionEvent.CTRL_MASK));
		scores.addActionListener(new MenuActionListener(frame));
		add(scores);
	}

	class MenuActionListener implements ActionListener {
		public Frame frame2;
		public MenuActionListener(Frame frame) {
			frame2 = frame;
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			if (e.getActionCommand().equals("Trò chơi mới")) {
				frame2.newGame();
			}
			if (e.getActionCommand().equals("Điểm cao")) {
				new InfoCenter(frame2, "Điểm cao", main.java.Game.getTopScoresList(), JOptionPane.INFORMATION_MESSAGE);
			}
		}
	}
}