package ais.view;

import ais.app.AISMain2;
import ais.controller.InputController;
import java.awt.*;
import javax.swing.*;




public class EditAndTxSubMenuView extends JPanel {

    private JList<String> subMenuList;
    private DefaultListModel<String> subMenuListModel;

    public EditAndTxSubMenuView() {
        setLayout(new BorderLayout());
        initComponents();
    }

    private void initComponents() {

        // ===== メインヘッダ =====
        JLabel header = new JLabel("EDIT AND TX");
        header.setFont(new Font("Meiryo UI", Font.BOLD, 32));
        header.setOpaque(true);
        header.setBackground(UiTheme.HEADER_BG);
        header.setBorder(BorderFactory.createEmptyBorder(8, 12, 8, 12));

        // ===== サブヘッダ =====
        JLabel subHeader = new JLabel("* SUB MENU *");
        subHeader.setFont(new Font("Meiryo UI", Font.BOLD, 32));
        subHeader.setOpaque(true);
        subHeader.setBackground(new Color(230, 230, 230));
        subHeader.setBorder(BorderFactory.createEmptyBorder(8, 12, 8, 12));

        // ===== リスト =====
        subMenuListModel = new DefaultListModel<>();
        subMenuListModel.addElement("[ TX ]");
        subMenuListModel.addElement("[ EDIT ]");
        subMenuListModel.addElement("[ SAVE ]");
        subMenuListModel.addElement("[ EXIT ]");

        subMenuList = new JList<>(subMenuListModel);
        subMenuList.setFont(new Font("Meiryo UI", Font.PLAIN, 35));
        subMenuList.setSelectionBackground(UiTheme.SELECTION_BG);
        subMenuList.setFixedCellHeight(55);
        subMenuList.setBorder(
                BorderFactory.createEmptyBorder(8, 12, 8, 12)
        );

        // ===== カード構成 =====
        JPanel card = new JPanel(new BorderLayout());
        card.add(header, BorderLayout.NORTH);
        card.add(subHeader, BorderLayout.CENTER);
        card.add(subMenuList, BorderLayout.SOUTH);

        add(card, BorderLayout.CENTER);
    }

    /* =====================================================
     * AISMain2 / InputController 用 API
     * ===================================================== */
    public int getSelectedIndex() {
        return subMenuList.getSelectedIndex();
    }

    public String getSelectedItem() {
        return subMenuList.getSelectedValue();
    }

    public void setSelectedIndex(int index) {
        subMenuList.setSelectedIndex(index);
        subMenuList.ensureIndexIsVisible(index);
    }

    public void resetCursor() {
        setSelectedIndex(0);
    }

    public int getItemCount() {
        return subMenuListModel.getSize();
    }

    public void ensureIndexIsVisible(int index) {
        subMenuList.ensureIndexIsVisible(index);
    }

    public JList<String> getList() {
        return subMenuList;
    }

    @Override
    public void requestFocus() {
        subMenuList.requestFocusInWindow();
    }
}
