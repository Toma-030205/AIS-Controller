package ais.view;

import java.awt.*;
import javax.swing.*;



public class RxTraySubMenuView extends JPanel {

    private JLabel headerLabel;
    private JLabel subHeaderLabel;
    private JList<String> menuList;
    private DefaultListModel<String> menuModel;

    public RxTraySubMenuView() {
        setLayout(new BorderLayout());
        initComponents();
    }

    private void initComponents() {

        /* ===== ヘッダ ===== */
        headerLabel = new JLabel(
                "OTHERS MESSAGES TRAY",
                SwingConstants.LEFT
        );
        headerLabel.setFont(new Font("Meiryo UI", Font.BOLD, 32));
        headerLabel.setOpaque(true);
        headerLabel.setBackground(UiTheme.HEADER_BG);
        headerLabel.setBorder(
                BorderFactory.createEmptyBorder(8, 12, 8, 12)
        );

        /* ===== サブヘッダ ===== */
        subHeaderLabel = new JLabel("* SUB MENU *");
        subHeaderLabel.setFont(new Font("Meiryo UI", Font.BOLD, 32));
        subHeaderLabel.setOpaque(true);
        subHeaderLabel.setBackground(new Color(230, 230, 230));
        subHeaderLabel.setBorder(
                BorderFactory.createEmptyBorder(8, 12, 8, 12)
        );

        /* ===== メニュー ===== */
        menuModel = new DefaultListModel<>();
        menuModel.addElement("[ DETAIL VIEW ]");
        menuModel.addElement("[ EDIT ]");
        menuModel.addElement("[ DELETE ]");
        menuModel.addElement("[ EXIT ]");

        menuList = new JList<>(menuModel);
        menuList.setFont(new Font("Meiryo UI", Font.PLAIN, 40));
        menuList.setSelectionBackground(
                UiTheme.SELECTION_BG
        );
        menuList.setFixedCellHeight(50);
        menuList.setBorder(
                BorderFactory.createEmptyBorder(0, 0, 35, 0)
        );
        menuList.setSelectedIndex(0);
        menuList.setFocusable(false);

        /* ===== カード全体 ===== */
        JPanel card = new JPanel(new BorderLayout());
        card.add(headerLabel, BorderLayout.NORTH);
        card.add(subHeaderLabel, BorderLayout.CENTER);
        card.add(menuList, BorderLayout.SOUTH);

        add(card, BorderLayout.CENTER);
    }

    /* =====================================================
     * Controller 用 API（TX と完全互換）
     * ===================================================== */

    public int getSelectedIndex() {
        return menuList.getSelectedIndex();
    }

    public String getSelectedItem() {
        return menuList.getSelectedValue();
    }

    public void selectNext() {
        int i = menuList.getSelectedIndex();
        if (i < menuModel.size() - 1) {
            menuList.setSelectedIndex(i + 1);
        }
    }

    public void selectPrev() {
        int i = menuList.getSelectedIndex();
        if (i > 0) {
            menuList.setSelectedIndex(i - 1);
        }
    }

    public void setSelectedIndex(int idx) {
        menuList.setSelectedIndex(idx);
    }

    public JList<String> getList() {
        return menuList;
    }
}
