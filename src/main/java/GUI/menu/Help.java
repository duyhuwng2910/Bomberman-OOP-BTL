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
import main.java.Game;

public class Help extends JMenu {

    public Help(Frame frame)  {
        super("Help");

        /*
         * Hướng dẫn chơi game.
         */
        JMenuItem instructions = new JMenuItem("Hướng dẫn");
        instructions.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_H, ActionEvent.CTRL_MASK));
        instructions.addActionListener(new MenuActionListener(frame));
        add(instructions);

        /*
         * Thông tin trò chơi
         */
        JMenuItem about = new JMenuItem("Thông tin");
        about.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_A, ActionEvent.CTRL_MASK));
        about.addActionListener(new MenuActionListener(frame));
        add(about);

    }

    class MenuActionListener implements ActionListener {
        public Frame frame;
        public MenuActionListener(Frame frame) {
            this.frame = frame;
        }

        @Override
        public void actionPerformed(ActionEvent e) {

            if(e.getActionCommand().equals("Hướng dẫn")) {
                new InfoCenter(frame, "Hướng dẫn", "Di chuyển: các phím W,A,S,D Hoặc lên, xuống, trái, phải để di chuyển.\nĐặt bom: Phím cách hoặc X."
                        + "\nNhiệm vụ của bạn là đặt bom tiêu diệt hết quái và tìm được cổng dịch \nchuyển trong các viên gạch để sang màn tiếp theo. Bạn sẽ thua khi quái \nchạm vào hoặc bị bom nổ trúng. "
                        + "\nTrong các viên gạch ngẫu nhiên sẽ có các vật phẩm hỗ trợ cho bạn."
                        + "\nChúc bạn chơi game vui vẻ."
                        , JOptionPane.QUESTION_MESSAGE);
            }

            if(e.getActionCommand().equals("Thông tin")) {
                new InfoCenter(frame, "Thông tin", "Phiên bản: " + Game.VERSION, JOptionPane.INFORMATION_MESSAGE);
            }

        }
    }
}