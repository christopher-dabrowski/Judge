package gui;

import parser.BattleParser;

import javax.swing.*;

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

    public BattleControls(BattlePreview battlePreview, BattleParser battleParser) {
        this.battlePreview = battlePreview;
        this.battleParser = battleParser;
        //            TODO Make it work
        playButton.addActionListener(e -> {

        });
        nextButton.addActionListener(e -> {

        });
        previousButton.addActionListener(e -> {

        });
        backButton.addActionListener(e -> {

        });
        stopButton.addActionListener(e -> {

        });
    }

    private void createUIComponents() {
        // TODO: place custom component creation code here
//        player1Label = new JLabel("Player");
    }
}
