package ais.view;

import java.awt.*;
import javax.swing.*;



public class ListSubView extends JPanel {

    private JList<String> Listsublist;
    private DefaultListModel<String> ListsubModel;

    public ListSubView() {
        setLayout(new BorderLayout());
        initComponents();
    }

    private void initComponents() {
        
        JLabel listsub = new JLabel("LIST DISP . SET");
        listsub.setFont(new Font("Meiryo UI", Font.BOLD, 32));
        listsub.setOpaque(true);
        listsub.setBackground(UiTheme.HEADER_BG);
        listsub.setBorder(BorderFactory.createEmptyBorder(8, 12, 8, 12));

        JLabel listsubLabel = new JLabel("* SUB MENU *");
        listsubLabel.setFont(new Font("Meiryo UI", Font.BOLD, 32));
        listsubLabel.setOpaque(true);
        listsubLabel.setBackground(new Color(230, 230, 230));
        listsubLabel.setBorder(BorderFactory.createEmptyBorder(8, 12, 8, 12));

        ListsubModel = new DefaultListModel<>();
        ListsubModel.addElement("1.[ BEARING ]");
        ListsubModel.addElement("2.[ SORT ]");
        ListsubModel.addElement("3.[ NAME ]");
        ListsubModel.addElement("4.[ DISP ]");
        ListsubModel.addElement("5.[ EXIT ]");

        Listsublist = new JList<>(ListsubModel);
        Listsublist.setFont(new Font("Meiryo UI", Font.PLAIN, 30));
        Listsublist.setSelectionBackground(UiTheme.SELECTION_BG);
        Listsublist.setFixedCellHeight(42);
        Listsublist.setBorder(BorderFactory.createEmptyBorder(8, 12, 25, 12));


        JPanel listsubCard = new JPanel(new BorderLayout());

        // 上から順に配置していく
        listsubCard.add(listsub, BorderLayout.NORTH);
        listsubCard.add(listsubLabel, BorderLayout.CENTER);
        listsubCard.add(Listsublist, BorderLayout.SOUTH);

        add(listsubCard, BorderLayout.CENTER);
    }

    // ===== Controller 用 API =====
    public int getSelectedIndex() {
        return Listsublist.getSelectedIndex();
    }

    public String getSelectedItem() {
        return Listsublist.getSelectedValue();
    }

    public void setSelectedIndex(int index) {
        Listsublist.setSelectedIndex(index);
        Listsublist.ensureIndexIsVisible(index);
    }

    public int getItemCount() {
        return ListsubModel.getSize();
    }

    public void requestFocus() {
        Listsublist.requestFocusInWindow();
    }

    
}