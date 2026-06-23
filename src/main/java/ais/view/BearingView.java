package ais.view;

import java.awt.*;
import javax.swing.*;



public  class BearingView extends JPanel {

    private JList<String> bearinglist;
    private DefaultListModel<String> bearingModel;

    public BearingView() {
        setLayout(new BorderLayout());

        JLabel bearingsub = new JLabel("LIST DISP . SET");
        bearingsub.setFont(new Font("Meiryo UI", Font.BOLD, 32));
        bearingsub.setOpaque(true);
        bearingsub.setBackground(UiTheme.HEADER_BG);
        bearingsub.setBorder(BorderFactory.createEmptyBorder(8, 12, 8, 12));

        JLabel bearingLabel = new JLabel("* BERARING SETTING *");
        bearingLabel.setFont(new Font("Meiryo UI", Font.BOLD, 32));
        bearingLabel.setOpaque(true);
        bearingLabel.setBackground(new Color(230, 230, 230));
        bearingLabel.setBorder(BorderFactory.createEmptyBorder(8, 12, 8, 12));

        bearingModel = new DefaultListModel<>();
        bearingModel.addElement("HEAD UP");
        bearingModel.addElement("NORTH UP");
        

        bearinglist = new JList<>(bearingModel);
        bearinglist.setFont(new Font("Meiryo UI", Font.PLAIN, 30));
        bearinglist.setSelectionBackground(UiTheme.SELECTION_BG);
        bearinglist.setFixedCellHeight(42);
        bearinglist.setBorder(BorderFactory.createEmptyBorder(8, 12, 140, 12));

        add(bearingsub, BorderLayout.NORTH);
        add(bearingLabel, BorderLayout.CENTER);
        add(bearinglist, BorderLayout.SOUTH);
    }

    // ===== Controller 用 API =====
    public int getSelectedIndex() {
        return bearinglist.getSelectedIndex();
    }

    public void setSelectedIndex(int index) {
        bearinglist.setSelectedIndex(index);
        bearinglist.ensureIndexIsVisible(index);
    }

    public int getItemCount() {
        return bearingModel.getSize();
    }

    public String getSelectedItem() {
        return bearinglist.getSelectedValue();
    }

    public void requestFocus() {
        bearinglist.requestFocusInWindow();
    }

    // 指定行を可視化
    public void ensureIndexIsVisible(int index) {
        bearinglist.ensureIndexIsVisible(index);
    }

    public JList<String> getList() {
        return bearinglist;
    }
}
