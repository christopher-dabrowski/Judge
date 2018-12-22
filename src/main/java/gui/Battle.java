package gui;

import parser.BattleParser;

import javax.swing.*;
import java.io.File;
import java.io.FileNotFoundException;

public class Battle {
    private BattlePreview battlePreview;
    private BattleControls battleControl;
    private JPanel overlay;
    private BattleParser battleParser;

    public static void main(String... args) {
        JFrame jFrame = new JFrame("TEST" + BattleControls.class);
        try {
            Battle battle = new Battle();
            battle.giveFile(new File("game_logs/123456VS987654.log"));
            jFrame.setContentPane(battle.overlay);
            jFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
            jFrame.pack();
            jFrame.setVisible(true);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void giveFile(File file) throws FileNotFoundException {
        battleParser = new BattleParser(file);
        //The only use of battleParser outside of BattleControl
        battlePreview.createBattleImage(battleParser.nextBoard());
        battleControl.setUpBattleParser(battleParser);
    }

    private void createUIComponents() {
        // TODO: place custom component creation code here
        battlePreview = new BattlePreview();
        battleControl = new BattleControls(battlePreview);
    }
}
