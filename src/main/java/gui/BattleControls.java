package gui;

import lombok.var;
import parser.BattleParser;

import javax.swing.*;
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

    private Thread demmon = new Thread(() -> {
        while (true) {

        }
    });

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

    public BattleControls(BattlePreview battlePreview) {
        this.battlePreview = battlePreview;

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
