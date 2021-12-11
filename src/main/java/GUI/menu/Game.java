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

/**
 * Class menu dùng để hiện điểm số cao nhất.
 */
public class Game extends JMenu {
	public Frame frame;
	
	public Game(Frame frame) {
		super("Game");
		this.frame = frame;
		JMenuItem scores = new JMenuItem("Top Scores");
		scores.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_T, ActionEvent.CTRL_MASK));
		scores.addActionListener(new MenuActionListener(frame));
		add(scores);
	}
	
	class MenuActionListener implements ActionListener {
		public Frame _frame;
		public MenuActionListener(Frame frame) {
			_frame = frame;
		}
		
		  @Override
		public void actionPerformed(ActionEvent e) {
			  if(e.getActionCommand().equals("Top Scores")) {
				  new InfoCenter(_frame, "Top Scores", "If i had more time..", JOptionPane.INFORMATION_MESSAGE);
			  }
		  }
		}
}
