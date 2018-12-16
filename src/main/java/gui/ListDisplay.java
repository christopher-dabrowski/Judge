package gui;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class ListDisplay {
    private List content;

    private JPanel overlay;
    private JList list;
    private JScrollPane scrollPane;
    private JButton tekstButton;
    private JButton backButton;

    public ListDisplay(List toDisplay) {
        this.content = toDisplay;
    }

    public static void main(String... args) {
        JFrame jFrame = new JFrame("TEST");
        List<Integer> list = new ArrayList<>();
        for (int i = 0; i < 1000; i++) {
            list.add(i);
        }
        ListDisplay battle = new ListDisplay(list);
        jFrame.setContentPane(battle.overlay);
        jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jFrame.pack();
        jFrame.setVisible(true);
    }

    private void createUIComponents() {
        // TODO: place custom component creation code here
        list = new JList(content.toArray());
        list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        list.setLayoutOrientation(JList.VERTICAL);
        list.setVisibleRowCount(-1);
        scrollPane = new JScrollPane(list);
        scrollPane.setPreferredSize(new Dimension(250, 80));
        tekstButton = new JButton();
    }
}
