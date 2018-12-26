package gui;

import javafx.stage.FileChooser;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GUIMain {
    private JFrame frame;
    private JPanel mainPanel;
    private JButton startButton;
    private JLabel heding;
    private JButton pickFolderButton;
    private JTextField playerFolderTextField;
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
        this.frame = frame;

        fileChooser = new JFileChooser();
        fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);

        startButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //TODO Lunch tournament

                JOptionPane.showMessageDialog(frame, "Button has been pressed!\nThe tournament is about to begin");



            }
        });

        pickFolderButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                handlePickPlayersFolderButtonClick();
            }
        });
    }

    private void handlePickPlayersFolderButtonClick() {
        int returnValue = fileChooser.showOpenDialog(frame);

        if (returnValue == JFileChooser.APPROVE_OPTION) {
            playerFolderTextField.setText(fileChooser.getSelectedFile().toString());
        }
    }
}
