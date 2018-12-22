package gui;

import lombok.var;
import parser.BattleParser;

import javax.swing.*;
import java.util.InputMismatchException;
import java.util.List;

public class BattleControls {
    private JPanel BattleControlsPanel;
    private JButton nextButton;
    private JButton previousButton;
    private JButton stopButton;
    private JButton playButton;
    private JButton backButton;
    private JLabel player1Label;
    private JLabel obstaclesLabel;
    private JLabel player0Label;

    private BattlePreview battlePreview;
    private BattleParser battleParser;

    // Recreating game
    private final JLabel[] colors = {player0Label, player1Label};
    private boolean play = false;
    private int index = 0;
    private Thread daemon = new Thread(() -> {
        while (true) {
            if (play) {
                try {
                    Thread.sleep(300);
                    takeNext();
                } catch (InterruptedException | InputMismatchException e) {
                    System.out.println("Ending");
                }
                Thread.yield();
            }
            System.out.println("\t" + play);
        }
    });
    public BattleControls(BattlePreview battlePreview) {
        this.battlePreview = battlePreview;

        //            TODO Make it work
        playButton.addActionListener(e -> {
            synchronized (this) {
                play = true;
            }
        });
        stopButton.addActionListener(e -> {
            synchronized (this) {
                play = false;
            }
        });
        nextButton.addActionListener(e -> {
            if (!play) {
                try {
                    takeNext();
                } catch (InputMismatchException e1) {
                    System.out.println("Ending");
                }
            }
        });
//TODO Implement viewing of previous moves
        previousButton.addActionListener(e -> {

        });
        backButton.addActionListener(e -> {

        });

        daemon.setDaemon(true);
        daemon.start();
    }

    private synchronized void takeNext() throws InputMismatchException {
        int[] coordinates = battleParser.nextMove();

        battlePreview.take(coordinates[0], coordinates[1], colors[index].getForeground());
        battlePreview.take(coordinates[2], coordinates[3], colors[index].getForeground());
        index++;
        index = index % 2;
        battlePreview.repaint();
    }

    // Recreating game
    public void setUpBattleParser(BattleParser battleParser) {
        this.battleParser = battleParser;
        //y; x
        List<Integer> obstacles = battleParser.nextObstacles();
        var PlayerOne = battleParser.nextPlayer();
        var PlayerTwo = battleParser.nextPlayer();
        player0Label.setText(PlayerOne);
        player1Label.setText(PlayerTwo);
        for (int i = 0; i < obstacles.size(); i += 2) {
            battlePreview.take(obstacles.get(i), obstacles.get(i + 1), obstaclesLabel.getForeground());
        }
        battleParser.skipOkEtc();

    }
}
