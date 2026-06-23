package ais.view;

import java.awt.*;
import javax.swing.*;



public  class NAMEView extends JPanel {

    private JList<String> namelist;
    private DefaultListModel<String> nameModel;

    public NAMEView() {
        setLayout(new BorderLayout());

        JLabel namesub = new JLabel("LIST DISP . SET");
        namesub.setFont(new Font("Meiryo UI", Font.BOLD, 32));
        namesub.setOpaque(true);
        namesub.setBackground(UiTheme.HEADER_BG);
        namesub.setBorder(BorderFactory.createEmptyBorder(8, 12, 8, 12));

        JLabel nameLabel = new JLabel("* NAME SETTING *");
        nameLabel.setFont(new Font("Meiryo UI", Font.BOLD, 32));
        nameLabel.setOpaque(true);
        nameLabel.setBackground(new Color(230, 230, 230));
        nameLabel.setBorder(BorderFactory.createEmptyBorder(8, 12, 8, 12));

        nameModel = new DefaultListModel<>();
        nameModel.addElement("SHIP NAME");
        nameModel.addElement("MMSI");
        

        namelist = new JList<>(nameModel);
        namelist.setFont(new Font("Meiryo UI", Font.PLAIN, 30));
        namelist.setSelectionBackground(UiTheme.SELECTION_BG);
        namelist.setFixedCellHeight(42);
        namelist.setBorder(BorderFactory.createEmptyBorder(8, 12, 140, 12));

        add(namesub, BorderLayout.NORTH);
        add(nameLabel, BorderLayout.CENTER);
        add(namelist, BorderLayout.SOUTH);
    }

    // ===== Controller 用 API =====
    public int getSelectedIndex() {
        return namelist.getSelectedIndex();
    }

    public void setSelectedIndex(int index) {
        namelist.setSelectedIndex(index);
        namelist.ensureIndexIsVisible(index);
    }

    public int getItemCount() {
        return nameModel.getSize();
    }

    public String getSelectedItem() {
        return namelist.getSelectedValue();
    }

    public void requestFocus() {
        namelist.requestFocusInWindow();
    }

    // 指定行を可視化
    public void ensureIndexIsVisible(int index) {
        namelist.ensureIndexIsVisible(index);
    }

    public JList<String> getList() {
        return namelist;
    }
}
