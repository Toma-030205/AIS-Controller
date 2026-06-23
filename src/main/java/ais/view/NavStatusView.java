package ais.view;

import ais.model.NavStatus;
import java.awt.*;
import javax.swing.*;



public class NavStatusView extends JPanel {

    private JList<String> navList;
    private DefaultListModel<String> model;
    

    public NavStatusView() {
        setLayout(new BorderLayout());

        JLabel header = new JLabel("* NAV. STATUS *");
        header.setFont(new Font("Meiryo UI", Font.BOLD, 32));
        header.setOpaque(true);
        header.setBackground(UiTheme.HEADER_BG);
        header.setBorder(BorderFactory.createEmptyBorder(8, 12, 8, 12));

        model = new DefaultListModel<>();
        model.addElement(" UNDER WAY USING ENGINE");
        model.addElement(" AT ANCHOR");
        model.addElement(" NOT UNDER COMMAND");
        model.addElement(" RESTRICTED MANOEUVRABILITY");
        model.addElement(" CONSTRAINED BY DRAFT");
        model.addElement(" MOORED");
        model.addElement(" AGROUND");
        model.addElement(" ENGAGED IN FISHING");
        model.addElement(" UNDER WAY SAILING");
        model.addElement(" RESERVED FOR HSC");
        model.addElement(" RESERVED FOR WIG");
        model.addElement(" NOT_DEFINED");
        
        navList = new JList<>(model);
        navList.setFont(new Font("Meiryo UI", Font.PLAIN, 35));
        navList.setSelectionBackground(UiTheme.SELECTION_BG);
        navList.setFixedCellHeight(60);

        add(header, BorderLayout.NORTH);
        add(new JScrollPane(navList), BorderLayout.CENTER);

        
    }

    // ===== Controller 用 API =====
    public NavStatus getSelectedNavStatus() {
        return NavStatus.fromIndex(navList.getSelectedIndex());
    }
    
    public int getSelectedIndex() {
        return navList.getSelectedIndex();
    }

    public String getSelectedItem() {
        return navList.getSelectedValue();
    }

    public void setSelectedIndex(int index) {
        navList.setSelectedIndex(index);
        navList.ensureIndexIsVisible(index);
    }

    // 要素数を返す
    public int getItemCount() {
        return model.getSize();
    }


    public void requestFocus() {
        navList.requestFocusInWindow();
    }

    // 指定行を可視化
    public void ensureIndexIsVisible(int index) {
        navList.ensureIndexIsVisible(index);
    }

    public JList<String> getList() {
        return navList;
    }

    public String getSelectedStatus() {
        return navList.getSelectedValue();
    }

}
