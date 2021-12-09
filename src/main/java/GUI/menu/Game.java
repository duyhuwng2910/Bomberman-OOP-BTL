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
		super("Game");
		this.frame1 = frame;

		JMenuItem newgame = new JMenuItem("New Game");
		newgame.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N, ActionEvent.CTRL_MASK));
		newgame.addActionListener(new MenuActionListener(frame));
		add(newgame);

		JMenuItem scores = new JMenuItem("Top Scores");
		scores.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_T, ActionEvent.CTRL_MASK));
		scores.addActionListener(new MenuActionListener(frame));
		add(scores);

		JMenuItem codes = new JMenuItem("Codes");
		codes.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_C, ActionEvent.CTRL_MASK));
		codes.addActionListener(new MenuActionListener(frame));
		add(codes);
	}
	
	class MenuActionListener implements ActionListener {
		public Frame frame2;
		public MenuActionListener(Frame frame) {
			frame2 = frame;
		}
		
		  @Override
		public void actionPerformed(ActionEvent e) {
			  
			  if(e.getActionCommand().equals("New Game")) {
				  frame2.newGame();
			  }
			  
			  if(e.getActionCommand().equals("Top Scores")) {
				  new InfoCenter(frame2, "Top Scores", "If i had more time..", JOptionPane.INFORMATION_MESSAGE);
			  }
		  }
		}

}
