package gui;

import javax.swing.*;

public class GUIMain {
    private JPanel mainPanel;
    private JButton startButton;

    public static void main(String... args) {
        JFrame jFrame = new JFrame("Judge");
        GUIMain gui = new GUIMain();
        jFrame.setContentPane(gui.mainPanel);
        jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        jFrame.setSize(800, 600);

        //jFrame.pack();
        jFrame.setVisible(true);
    }
}
