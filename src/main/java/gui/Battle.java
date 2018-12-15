package gui;

import javax.swing.*;

public class Battle {
    private BattlePreview battlePreview;
    private BattleControls battleControl;
    private JPanel overlay;

    public static void main(String... args) {
        JFrame jFrame = new JFrame("TEST" + BattleControls.class);
        Battle battle = new Battle();
        jFrame.setContentPane(battle.overlay);
        jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jFrame.pack();
        jFrame.setVisible(true);
    }

    private void createUIComponents() {
        // TODO: place custom component creation code here
        battlePreview = new BattlePreview();
        battleControl = new BattleControls(battlePreview);
    }
}
