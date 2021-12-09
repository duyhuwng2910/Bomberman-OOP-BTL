package main.java.GUI.menu;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JSlider;
import javax.swing.KeyStroke;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import main.java.GUI.Frame;
import main.java.Game;

public class Options extends JMenu implements ChangeListener {

	Frame frame1;
	
	public Options(Frame frame) {
		super("Options");
		
		this.frame1 = frame;
		
		JMenuItem pause = new JMenuItem("Pause");
		pause.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_P, ActionEvent.CTRL_MASK));
		pause.addActionListener(new MenuActionListener(frame));
		add(pause);
		
		JMenuItem resume = new JMenuItem("Resume");
		resume.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_R, ActionEvent.CTRL_MASK));
		resume.addActionListener(new MenuActionListener(frame));
		add(resume);
		
		addSeparator();
		
		add(new JLabel("Size: "));
		
		JSlider sizeRange = new JSlider(JSlider.HORIZONTAL,
                1, 5, 3);

		sizeRange.setMajorTickSpacing(2);
		sizeRange.setMinorTickSpacing(1);
		sizeRange.setPaintTicks(true);
		sizeRange.setPaintLabels(true);
		sizeRange.addChangeListener(this);
		
		add(sizeRange);
		
	}
	
	@Override
	public void stateChanged(ChangeEvent e) {
		JSlider source = (JSlider)e.getSource();
	    if (!source.getValueIsAdjusting()) {
	        int fps = (int)source.getValue();
	        
	        Game.SCALE = fps;
	        System.out.println( Game.SCALE);
	        
	        frame1.gamePanel.changeSize();
			  frame1.revalidate();
			  frame1.pack();
	    }
	}
	
	class MenuActionListener implements ActionListener {
		public Frame frame2;
		public MenuActionListener(Frame frame) {
			frame2 = frame;
		}
		
		@Override
		public void actionPerformed(ActionEvent actionEvent) {
			  
			  if(actionEvent.getActionCommand().equals("Pause")) {
				  frame2.pauseGame();
			  }
				  
			  if(actionEvent.getActionCommand().equals("Resume")) {
				  frame2.resumeGame();
			  }
		  }
	}
}
