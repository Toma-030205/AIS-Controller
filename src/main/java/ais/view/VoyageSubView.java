package ais.view;

import ais.app.AISMain2;
import java.awt.*;
import javax.swing.*;



public class VoyageSubView extends JPanel {

    private JList<String> voyagesubList;
    private DefaultListModel<String> voyagesubListModel;

    public VoyageSubView() {
        setLayout(new BorderLayout());
        initComponents();
    }

    private void initComponents() {

        JLabel voyagesubHeader = new JLabel("VOYAGE DATA");
        voyagesubHeader.setFont(new Font("Meiryo UI", Font.BOLD, 32));
        voyagesubHeader.setOpaque(true);
        voyagesubHeader.setBackground(UiTheme.HEADER_BG);
        voyagesubHeader.setBorder(BorderFactory.createEmptyBorder(8, 12, 8, 12));

        JLabel voyagesub = new JLabel("* SUB MENU *");
        voyagesub.setFont(new Font("Meiryo UI", Font.BOLD, 32));
        voyagesub.setOpaque(true);
        voyagesub.setBackground(new Color(230, 230, 230));
        voyagesub.setBorder(BorderFactory.createEmptyBorder(8, 12, 8, 12));

        voyagesubListModel = new DefaultListModel<>();
        voyagesubListModel.addElement("[ SET ]");
        voyagesubListModel.addElement("[ DEST LOAD ]");
        voyagesubListModel.addElement("[ EXIT ]");

        voyagesubList = new JList<>(voyagesubListModel);
        voyagesubList.setFont(new Font("Meiryo UI", Font.PLAIN, 35));
        voyagesubList.setSelectionBackground(UiTheme.SELECTION_BG);
        voyagesubList.setFixedCellHeight(60);
        voyagesubList.setBorder(BorderFactory.createEmptyBorder(8, 12, 60, 12));

        JPanel voyagesubCard = new JPanel(new BorderLayout());
        voyagesubCard.add(voyagesubHeader, BorderLayout.NORTH);
        voyagesubCard.add(voyagesub, BorderLayout.CENTER);
        voyagesubCard.add(voyagesubList, BorderLayout.SOUTH);

        add(voyagesubCard, BorderLayout.CENTER);
    }

    // ===== AISMain2 から呼ぶためのAPI =====

    public int getSelectedIndex() {
        return voyagesubList.getSelectedIndex();
    }

    public String getSelectedItem() {
        return voyagesubList.getSelectedValue();
    }

    public void setSelectedIndex(int index) {
        voyagesubList.setSelectedIndex(index);
        voyagesubList.ensureIndexIsVisible(index);
    }

    public void requestFocus() {
        voyagesubList.requestFocusInWindow();
    }
    
    // 要素数を返す
    public int getItemCount() {
        return voyagesubListModel.getSize();
    }

    // 指定行を可視化
    public void ensureIndexIsVisible(int index) {
        voyagesubList.ensureIndexIsVisible(index);
    }

    public JList<String> getList() {
        return voyagesubList;
    }

}
