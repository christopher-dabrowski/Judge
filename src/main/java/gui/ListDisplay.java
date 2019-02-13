package gui;

import gamequeuing.GameStatistics;
import lombok.Getter;
import lombok.var;
import mainlogic.Player;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

public class ListDisplay<Type> {

    private final JFileChooser fileChooser = new JFileChooser();
    @Getter
    private JPanel overlay;
    private JScrollPane scrollPane;
    private JList<Type> list;
    private JButton nextButton;
    private JLabel head;
    private JPanel content;

    ListDisplay() {

        fileChooser.setCurrentDirectory(new java.io.File("."));
        fileChooser.setDialogTitle("Choose a game to preview");
        var filter = new FileNameExtensionFilter("Game logs", "log");
        fileChooser.setFileFilter(filter);

        nextButton.addActionListener(e -> {
            if (fileChooser.showOpenDialog(content) == JFileChooser.APPROVE_OPTION) {
                //Start Battle
                Battle battle = new Battle();
                try {
                    battle.giveFile(fileChooser.getSelectedFile());
                    JFrame jFrame = new JFrame("Game preview");
                    jFrame.setContentPane(battle.getOverlay());
                    jFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                    jFrame.pack();
                    jFrame.setVisible(true);
                } catch (FileNotFoundException error) {
                    error.printStackTrace();
                }
            }
        });
    }

    //Main used to test GUI element
    public static void main(String... args) {
        JFrame jFrame = new JFrame("TEST");
        List<GameStatistics> integerArrayList = new ArrayList<>();
        for (int i = 0; i < 9; i++) {
            integerArrayList.add(new GameStatistics(new Player(123495 + "", i + "ala", Integer.toString(i), Integer.toString(i), Integer.toString(i))));
        }
        integerArrayList.get(0).incrementWins();
        integerArrayList.get(1).incrementKnockouts();
        integerArrayList.get(integerArrayList.size() - 1).incrementWins();
        integerArrayList.get(integerArrayList.size() - 1).incrementWins();

        integerArrayList.sort((o1, o2) -> {
            if (o2.getKnockouts() - o1.getKnockouts() == 0)
                return o2.getWins() - o1.getWins();
            else
                return o2.getWins() - o1.getWins();
        });
        ListDisplay<GameStatistics> listDisplay = new ListDisplay<>();
        listDisplay.setSubject(integerArrayList);
        jFrame.setContentPane(listDisplay.overlay);
        jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jFrame.pack();
        jFrame.setVisible(true);
    }

    @SuppressWarnings("unchecked")
    void setSubject(List<Type> list) {
        Object[] object = new Object[1];
        this.list.setListData(list.toArray((Type[]) object));
        String separation = "       |       ";
        String label = String.format("%10s%s%10s%s%4s%s%9s", "Alias", separation, "Index", separation, "Wins", separation, "Knockouts");
        head.setText(label);
    }

    private void createUIComponents() {
        // TODO: place custom component creation code here
        list = new JList<>();
        list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        list.setLayoutOrientation(JList.VERTICAL);
        list.setVisibleRowCount(-1);
        scrollPane = new JScrollPane(list);
        scrollPane.setPreferredSize(new Dimension(200, 300));
    }
}
