package gui;

import javafx.stage.FileChooser;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

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


                if (verifyPlayersFolder()) {
                    //TODO Lunch tournament
                }
                else {
                    JOptionPane.showMessageDialog(frame, "Chosen directory does not exist");
                }
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

    private boolean verifyPlayersFolder() {
        File folder = new File(playerFolderTextField.getText());

        return folder.exists() && folder.isDirectory();
    }
}
