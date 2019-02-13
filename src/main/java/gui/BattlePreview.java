package gui;

import javax.swing.*;
import java.awt.*;

public class BattlePreview extends JPanel {

    private BattleImage battleImage;

    BattlePreview() {
        super();
        this.setDoubleBuffered(true);
    }

    //Main used to test GUI element
    public static void main(String... args) {
        JFrame jFrame = new JFrame("TEST" + BattlePreview.class);
        BattlePreview battlePreview = new BattlePreview();
        battlePreview.createBattleImage(51);
        jFrame.setContentPane(battlePreview);
        jFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        jFrame.pack();
        jFrame.setVisible(true);
    }

    void createBattleImage(int number) {
        battleImage = new BattleImage(new Dimension(1000, 500), number);
        this.setPreferredSize(new Dimension(battleImage.getBufferedImage().getWidth(), battleImage.getBufferedImage().getHeight()));
    }

    void take(int x, int y, Color color) {
        battleImage.take(x, y, color);
    }

    void clear(int x, int y) {
        battleImage.clear(x, y);
    }

    @Override
    public void paintComponent(Graphics graphics) {
        super.paintComponent(graphics);
        if (battleImage != null)
            graphics.drawImage(battleImage.getBufferedImage(), 0, 0, null);
    }
}
