package main.java.GUI;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

public class InfoCenter implements WindowListener {
  private Frame frame;

  public InfoCenter(Frame frame, String title, String notification, int option) {
    frame = frame;

    final JFrame center = new JFrame(title);
    final JButton button = new JButton("FLEX");
    button.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e)
      {
        center.dispose();
      }
    });

    JButton[] buttons = { button };
    JOptionPane optionPane = new JOptionPane(notification, option, 0, null, buttons, button);
    center.getContentPane().add(optionPane);
    center.setSize(500,300);
    center.setLocationRelativeTo(frame);
    center.setVisible(true);
    center.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    center.addWindowListener(this);
  }

  @Override
  public void windowOpened(WindowEvent e) {
    frame.pauseGame();
  }

  @Override
  public void windowClosing(WindowEvent e) {}

  @Override
  public void windowClosed(WindowEvent e) {
    frame.resumeGame();
  }

  @Override
  public void windowIconified(WindowEvent e) {}

  @Override
  public void windowDeiconified(WindowEvent e) {}

  @Override
  public void windowActivated(WindowEvent e) {}

  @Override
  public void windowDeactivated(WindowEvent e) {}
}
