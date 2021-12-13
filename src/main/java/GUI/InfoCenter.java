package main.java.GUI;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

/**
 * Class đại diện cho các hình ảnh liên quan đến thông tin
 * của game, bao gồm thời gian, số điểm, số mạng còn lại.
 */
public class InfoCenter implements WindowListener {
		private Frame frame;
	
	  public InfoCenter(Frame frame, String title, String message, int option) {
		  this.frame = frame;
		  final JFrame center = new JFrame(title);
			final JButton button = new JButton("Đã hiểu");
			button.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent actionEvent)
	            {  
	                center.dispose();
	            }});
	        JButton[] buttons = { button };  
	        JOptionPane optionPane = new JOptionPane(message, option, 0, null, buttons, button);
	        center.getContentPane().add(optionPane);
	        center.setSize(500,300);
	        center.setLocationRelativeTo(frame);
	        center.setVisible(true);
	        center.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
	        center.addWindowListener(this);
	  }

	@Override
	public void windowOpened(WindowEvent windowEvent) {
		frame.pauseGame();
	}

	@Override
	public void windowClosing(WindowEvent windowEvent) {
	}

	@Override
	public void windowClosed(WindowEvent windowEvent) {
		frame.resumeGame();
	}

	@Override
	public void windowIconified(WindowEvent windowEvent) {
	}

	@Override
	public void windowDeiconified(WindowEvent windowEvent) {
	}

	@Override
	public void windowActivated(WindowEvent windowEvent) {
	}

	@Override
	public void windowDeactivated(WindowEvent windowEvent) {
	}
}
