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

public class Options extends JMenu implements ChangeListener {

	Frame frame;
	
	public Options(Frame frame) {
		super("Options");
		
		this.frame = frame;
		
		JMenuItem pause = new JMenuItem("Pause");
		pause.setAccelerator(KeyStroke.getKeyStroke((char) KeyEvent.VK_P));
		pause.addActionListener(new MenuActionListener(frame));
		add(pause);
		
		JMenuItem resume = new JMenuItem("Resume");
		resume.setAccelerator(KeyStroke.getKeyStroke((char) KeyEvent.VK_R));
		resume.addActionListener(new MenuActionListener(frame));
		add(resume);
		
		addSeparator();
	}

	@Override
	public void stateChanged(ChangeEvent e) {}

	class MenuActionListener implements ActionListener {
		public Frame frame;
		public MenuActionListener(Frame frame) {
			frame = frame;
		}
		
		@Override
		public void actionPerformed(ActionEvent e) {
			if(e.getActionCommand().equals("Pause")) {
				  frame.pauseGame();
			  }
			if(e.getActionCommand().equals("Resume")) {
				  frame.resumeGame();
			  }
		}
	}
}
