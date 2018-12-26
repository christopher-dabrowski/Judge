package gui;

import javafx.stage.FileChooser;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GUIMain {
    private JPanel mainPanel;
    private JButton startButton;
    private JLabel heding;
    private JTextArea playerFolderTextArea;
    private JButton pickFolderButton;
    private JFileChooser fileChooser;

    public static void main(String... args) {
        JFrame jFrame = new JFrame("Judge");
        GUIMain gui = new GUIMain(jFrame);
        jFrame.setContentPane(gui.mainPanel);
        jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        jFrame.setSize(800, 600);


        //jFrame.pack();
        jFrame.setVisible(true);
    }

    public GUIMain(JFrame frame) {

        fileChooser = new JFileChooser();
        fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);

        startButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                JOptionPane.showMessageDialog(frame, "Button has been pressed!\nThe tournament is about to begin");

                int returnValue = fileChooser.showOpenDialog(frame);
                if (returnValue == JFileChooser.APPROVE_OPTION) {
                    System.out.println(fileChooser.getSelectedFile());
                    JOptionPane.showMessageDialog(frame, fileChooser.getSelectedFile());
                }

            }
        });
    }
}
