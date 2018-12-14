package gui;

import javax.swing.*;

public class GUIMain {
    private JPanel mainPanel;
    private JLabel TESTLabel;

    public static void main(String... args) {
        JFrame jFrame = new JFrame("Judge");
        GUIMain gui = new GUIMain();
        jFrame.setContentPane(gui.mainPanel);
        jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jFrame.pack();
        jFrame.setVisible(true);
    }
}
