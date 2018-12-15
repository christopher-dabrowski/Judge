package gui;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

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

    public BattleControls(BattlePreview battlePreview) {
        this.battlePreview = battlePreview;
        playButton.addActionListener(new ActionListener() {
            //            TODO Make it work
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });
        nextButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });
        previousButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });
        stopButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });
    }

    private void createUIComponents() {
        // TODO: place custom component creation code here
//        player1Label = new JLabel("Player");
    }
}
