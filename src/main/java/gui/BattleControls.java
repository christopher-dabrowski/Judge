package gui;

import lombok.AllArgsConstructor;
import lombok.var;
import parser.BattleParser;

import javax.swing.*;
import java.awt.*;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Stack;

public class BattleControls {
    private JPanel battleControlsPanel;
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
    private Stack<Block> pastBlocks = new Stack<>();
    private Stack<Block> revertedBlocks = new Stack<>();
    private Thread daemon = new Thread(() -> {
        while (true) {
            if (play) {
                try {
                    Thread.sleep(400);
                    takeNext();
                } catch (InputMismatchException e) {
                    System.out.println("Ending");
                } catch (InterruptedException e) {
                    return;
                }
                Thread.yield();
            }
            System.out.println("\t" + play);
        }
    });

    public BattleControls(BattlePreview battlePreview) {
        this.battlePreview = battlePreview;

        playButton.addActionListener(e -> {
            synchronized (this) {
                play = true;
            }
            Thread.yield();
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
        previousButton.addActionListener(e -> {
            if (!play)
                unTake();
            System.out.println("previousButton");
        });
        backButton.addActionListener(e -> {
            SwingUtilities.getWindowAncestor(battleControlsPanel).dispose();
            daemon.interrupt();
        });

        daemon.setDaemon(true);
        daemon.start();
    }

    private synchronized void takeNext() throws InputMismatchException {
        int[] coordinates;
        Color blockColor;

        if (revertedBlocks.empty()) {
            coordinates = battleParser.nextMove();
            pastBlocks.push(new Block(coordinates[0], coordinates[1], colors[index].getForeground()));
            pastBlocks.push(new Block(coordinates[2], coordinates[3], colors[index].getForeground()));
            blockColor = colors[index].getForeground();
        } else {
            coordinates = new int[4];
            Block block = null;
            for (int i = 0; i < 4; i++) {
                block = revertedBlocks.pop();
                coordinates[i++] = block.y;
                coordinates[i] = block.x;
                pastBlocks.push(block);
            }
            blockColor = block.playerColor;
        }

        battlePreview.take(coordinates[0], coordinates[1], blockColor);
        battlePreview.take(coordinates[2], coordinates[3], blockColor);
        //Moving to next player
        index++;
        index = index % 2;
        //Apply changes
        battlePreview.repaint();
    }

    private synchronized void unTake() {
        if (!pastBlocks.empty()) {
            for (int i = 0; i < 2; i++) {
                Block block = pastBlocks.pop();
                battlePreview.clear(block.y, block.x);
                revertedBlocks.push(block);
            }
            battlePreview.repaint();
        }
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

    @AllArgsConstructor
    private class Block {
        int y;
        int x;
        Color playerColor;
    }
}
