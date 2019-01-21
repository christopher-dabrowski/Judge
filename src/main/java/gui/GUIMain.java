package gui;

import filemanager.FileManager;
import gamequeuing.GameQueue;
import gamequeuing.GameStatistics;
import mainlogic.Player;

import javax.swing.*;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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

        jFrame.setSize(600, 350);


        //jFrame.pack();
        jFrame.setVisible(true);
    }

    private GUIMain(JFrame frame) {
        this.frame = frame;

        //java.net.URL url = GUIMain.class.getResource("../../img/folder.png"); //Doesn't work
//        try {
//            Image img = ImageIO.read(getClass().getResource("img/folder.png")); //This won't work niter
//            pickFolderButton.setIcon(new ImageIcon(img));
//        } catch (IOException e) {
//
//        }

        fileChooser = new JFileChooser();
        fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);

        startButton.addActionListener(e -> {
            if (verifyPlayersFolder()) {
                try {
                    ArrayList<String> errors = new ArrayList<>(); //If there are errors with parsing player files we might print them
                    ArrayList<Player> players = FileManager.importPlayers(playerFolderTextField.getText(), errors);
                    GameQueue gameQueue = new GameQueue(players);
                    Map<Player, GameStatistics> list = gameQueue.morituriTeSalutant();
                    List<GameStatistics> scores = new ArrayList<>(list.values());
                    scores.sort((o1, o2) -> {
                        if (o2.getKnockouts() - o1.getKnockouts() == 0)
                            return o2.getWins() - o1.getWins();
                        else
                            return o2.getWins() - o1.getWins();
                    });
                    ListDisplay<GameStatistics> listDisplay = new ListDisplay<>();
                    listDisplay.setSubject(scores);

                    frame.setVisible(false); //Close this window

                    JFrame jFrame = new JFrame("Scores");
                    jFrame.setContentPane(listDisplay.getOverlay());
                    jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                    jFrame.pack();
                    jFrame.setVisible(true);
                } catch (Exception exception) {
                    JOptionPane.showMessageDialog(frame, "Error occurred when loading player files");
                    exception.printStackTrace();
                }

            } else {
                JOptionPane.showMessageDialog(frame, "Chosen directory does not exist");
            }
        });

        pickFolderButton.addActionListener(e -> handlePickPlayersFolderButtonClick());
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
