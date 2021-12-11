package main.java.GUI.menu;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.KeyStroke;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import main.java.GUI.Frame;

/**
 * Class đại diện cho tùy chọn và hướng dẫn.
 */
public class Options extends JMenu implements ChangeListener {
	Frame frame;
	
	public Options(Frame frame) {
		super("Options");
		this.frame = frame;
		
		JMenuItem pause = new JMenuItem("Pause");
		pause.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_P, ActionEvent.CTRL_MASK));
		pause.addActionListener(new MenuActionListener(frame));
		add(pause);
		
		JMenuItem resume = new JMenuItem("Resume");
		resume.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_R, ActionEvent.CTRL_MASK));
		resume.addActionListener(new MenuActionListener(frame));
		add(resume);
	}
	
	@Override
	public void stateChanged(ChangeEvent e) {}
	
	class MenuActionListener implements ActionListener {
		public Frame _frame;
		public MenuActionListener(Frame frame) {
			_frame = frame;
		}
		
		@Override
		public void actionPerformed(ActionEvent actionEvent) {
			  if(actionEvent.getActionCommand().equals("Pause")) {
				  _frame.pauseGame();
			  }
			  if(actionEvent.getActionCommand().equals("Resume")) {
				  _frame.resumeGame();
			  }
		  }
	}
}
