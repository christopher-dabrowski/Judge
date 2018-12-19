package gui;

import javax.swing.*;
import java.awt.*;

public class BattlePreview extends JPanel {

    private BattleImage battleImage;

    BattlePreview() {
        super();
        this.setDoubleBuffered(true);
    }

    public static void main(String... args) {
        JFrame jFrame = new JFrame("TEST" + BattlePreview.class);
        BattlePreview battlePreview = new BattlePreview();
        battlePreview.createBattleImage(51);
        jFrame.setContentPane(battlePreview);
        jFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        jFrame.pack();
        jFrame.setVisible(true);
    }

    public void createBattleImage(int number) {
        battleImage = new BattleImage(new Dimension(1000, 500), number);
        this.setPreferredSize(new Dimension(battleImage.getBufferedImage().getWidth(), battleImage.getBufferedImage().getHeight()));
    }

    public void take(int y, int x, Color color) {
        battleImage.take(y, x, color);
    }

    @Override
    public void paintComponent(Graphics graphics) {
        super.paintComponent(graphics);
        if (battleImage != null)
            graphics.drawImage(battleImage.getBufferedImage(), 0, 0, null);
    }
}
