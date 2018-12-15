package gui;

import javax.swing.*;
import java.awt.*;

public class BattlePreview extends JPanel {

    private BattleImage battleImage;

    BattlePreview() {
        super();
        this.setDoubleBuffered(true);

        //TODO Make it taken from file
        //Until then its hardcoded TODO TODO TODO
        battleImage = new BattleImage(new Dimension(1000, 500), 13);
        this.setPreferredSize(new Dimension(battleImage.getBufferedImage().getWidth(), battleImage.getBufferedImage().getHeight()));
        for (int i = 0; i < 13; i++)
            battleImage.take(i, i, Color.RED);
        for (int i = 0; i < 13; i++)
            battleImage.take(i, 0, Color.GRAY);
        for (int i = 0; i < 13; i++)
            battleImage.take(0, i, Color.BLUE);
    }

    public static void main(String... args) {
        JFrame jFrame = new JFrame("TEST" + BattlePreview.class);
        BattlePreview battlePreview = new BattlePreview();
        jFrame.setContentPane(battlePreview);
        jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jFrame.pack();
        jFrame.setVisible(true);
    }

    @Override
    public void paintComponent(Graphics graphics) {
        super.paintComponent(graphics);
        graphics.drawImage(battleImage.getBufferedImage(), 0, 0, null);
    }
}
