package ais.view;

import java.awt.*;
import javax.swing.*;



public  class SORTView extends JPanel {

    private JList<String> sortlist;
    private DefaultListModel<String> sortModel;

    public SORTView() {
        setLayout(new BorderLayout());

        JLabel sortsub = new JLabel("LIST DISP . SET");
        sortsub.setFont(new Font("Meiryo UI", Font.BOLD, 32));
        sortsub.setOpaque(true);
        sortsub.setBackground(UiTheme.HEADER_BG);
        sortsub.setBorder(BorderFactory.createEmptyBorder(8, 12, 8, 12));

        JLabel sortLabel = new JLabel("* SORT SETTING *");
        sortLabel.setFont(new Font("Meiryo UI", Font.BOLD, 32));
        sortLabel.setOpaque(true);
        sortLabel.setBackground(new Color(230, 230, 230));
        sortLabel.setBorder(BorderFactory.createEmptyBorder(8, 12, 8, 12));

        sortModel = new DefaultListModel<>();
        sortModel.addElement("RANGE");
        sortModel.addElement("TCPA");
        sortModel.addElement("GROUP");
        

        sortlist = new JList<>(sortModel);
        sortlist.setFont(new Font("Meiryo UI", Font.PLAIN, 30));
        sortlist.setSelectionBackground(UiTheme.SELECTION_BG);
        sortlist.setFixedCellHeight(42);
        sortlist.setBorder(BorderFactory.createEmptyBorder(8, 12, 100, 12));

        add(sortsub, BorderLayout.NORTH);
        add(sortLabel, BorderLayout.CENTER);
        add(sortlist, BorderLayout.SOUTH);
    }

    // ===== Controller 用 API =====
    public int getSelectedIndex() {
        return sortlist.getSelectedIndex();
    }

    public void setSelectedIndex(int index) {
        sortlist.setSelectedIndex(index);
        sortlist.ensureIndexIsVisible(index);
    }

    public int getItemCount() {
        return sortModel.getSize();
    }

    public String getSelectedItem() {
        return sortlist.getSelectedValue();
    }

    public void requestFocus() {
        sortlist.requestFocusInWindow();
    }

    // 指定行を可視化
    public void ensureIndexIsVisible(int index) {
        sortlist.ensureIndexIsVisible(index);
    }

    public JList<String> getList() {
        return sortlist;
    }
}
