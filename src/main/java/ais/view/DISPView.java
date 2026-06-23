package ais.view;

import java.awt.*;
import javax.swing.*;



public  class DISPView extends JPanel {

    private JList<String> displist;
    private DefaultListModel<String> dispModel;

    public DISPView() {
        setLayout(new BorderLayout());

        JLabel dispsub = new JLabel("LIST DISP . SET");
        dispsub.setFont(new Font("Meiryo UI", Font.BOLD, 32));
        dispsub.setOpaque(true);
        dispsub.setBackground(UiTheme.HEADER_BG);
        dispsub.setBorder(BorderFactory.createEmptyBorder(8, 12, 8, 12));

        JLabel dispLabel = new JLabel("* DISPLAY SETTING *");
        dispLabel.setFont(new Font("Meiryo UI", Font.BOLD, 32));
        dispLabel.setOpaque(true);
        dispLabel.setBackground(new Color(230, 230, 230));
        dispLabel.setBorder(BorderFactory.createEmptyBorder(8, 12, 8, 12));

        dispModel = new DefaultListModel<>();
        dispModel.addElement("NORMAL");
        dispModel.addElement("TYPE1");
        dispModel.addElement("TYPE2");
        

        displist = new JList<>(dispModel);
        displist.setFont(new Font("Meiryo UI", Font.PLAIN, 30));
        displist.setSelectionBackground(UiTheme.SELECTION_BG);
        displist.setFixedCellHeight(42);
        displist.setBorder(BorderFactory.createEmptyBorder(8, 12, 100, 12));

        add(dispsub, BorderLayout.NORTH);
        add(dispLabel, BorderLayout.CENTER);
        add(displist, BorderLayout.SOUTH);
    }

    // ===== Controller 用 API =====
    public int getSelectedIndex() {
        return displist.getSelectedIndex();
    }

    public void setSelectedIndex(int index) {
        displist.setSelectedIndex(index);
        displist.ensureIndexIsVisible(index);
    }

    public int getItemCount() {
        return dispModel.getSize();
    }

    public String getSelectedItem() {
        return displist.getSelectedValue();
    }

    public void requestFocus() {
        displist.requestFocusInWindow();
    }

    // 指定行を可視化
    public void ensureIndexIsVisible(int index) {
        displist.ensureIndexIsVisible(index);
    }

    public JList<String> getList() {
        return displist;
    }
}
