package gui;

import lombok.Getter;
import parser.BattleParser;

import javax.swing.*;
import java.io.File;
import java.io.FileNotFoundException;

public class Battle {
    private BattlePreview battlePreview;
    private BattleControls battleControl;
    @Getter
    private JPanel overlay;

    public static void main(String... args) {
        JFrame jFrame = new JFrame("TEST");
        try {
            Battle battle = new Battle();
            battle.giveFile(new File("C:\\Users\\tooR\\Documents\\AA_Studia\\Judge\\game_logs\\024680VS123456.log"));
            jFrame.setContentPane(battle.overlay);
            jFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
            jFrame.pack();
            jFrame.setVisible(true);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void giveFile(File file) throws FileNotFoundException {
        BattleParser battleParser = new BattleParser(file);
        //The only use of battleParser outside of BattleControl
        battlePreview.createBattleImage(battleParser.nextBoard());
        battleControl.setUpBattleParser(battleParser);
    }

    private void createUIComponents() {
        battlePreview = new BattlePreview();
        battleControl = new BattleControls(battlePreview);
    }
}
