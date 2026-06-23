package ais.view;

import java.awt.*;
import javax.swing.*;



public class OtherShipSubMenuView extends JPanel {

    private JList<String> list;
    private DefaultListModel<String> model;

    public OtherShipSubMenuView() {
        setLayout(new BorderLayout());

        JLabel header = new JLabel("OTHER SHIP'S DETAIL");
        header.setFont(new Font("Meiryo UI", Font.BOLD, 32));
        header.setOpaque(true);
        header.setBackground(UiTheme.HEADER_BG);
        header.setBorder(BorderFactory.createEmptyBorder(8, 12, 8, 12));

        JLabel sub = new JLabel("* SUB MENU *");
        sub.setFont(new Font("Meiryo UI", Font.BOLD, 32));
        sub.setOpaque(true);
        sub.setBackground(new Color(230, 230, 230));
        sub.setBorder(BorderFactory.createEmptyBorder(8, 12, 8, 12));

        model = new DefaultListModel<>();
        model.addElement("[ EDIT AND TX ]");
        model.addElement("[ INTERROGATION ]");
        model.addElement("[ EXIT ]");

        list = new JList<>(model);
        list.setFont(new Font("Meiryo UI", Font.PLAIN, 35));
        list.setSelectionBackground(UiTheme.SELECTION_BG);
        list.setFixedCellHeight(60);
        list.setBorder(BorderFactory.createEmptyBorder(8, 12, 60, 12));

        add(header, BorderLayout.NORTH);
        add(sub, BorderLayout.CENTER);
        add(list, BorderLayout.SOUTH);
    }

    // ===== Controller 用 API =====

    public int getSelectedIndex() {
        return list.getSelectedIndex();
    }

    public String getSelectedItem() {
        return list.getSelectedValue();
    }

    public void setSelectedIndex(int index) {
        if (index >= 0 && index < model.getSize()) {
            list.setSelectedIndex(index);
            list.ensureIndexIsVisible(index);
        }
    }

    public int getItemCount() {
        return model.getSize();
    }

    public void requestFocus() {
        list.requestFocusInWindow();
    }

    public JList<String> getList() {
        return list;
    }
}
