package ais.view;

import java.awt.BorderLayout;
import javax.swing.DefaultListModel;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingConstants;

public class BaseListMenuView extends JPanel {
    private final JList<String> menuList;
    private final DefaultListModel<String> menuListModel;

    protected BaseListMenuView(String title, String[] items, int fontSize, int cellHeight) {
        setLayout(new BorderLayout());

        JLabel header = new JLabel(title, SwingConstants.LEFT);
        header.setFont(UiTheme.headerFont());
        header.setOpaque(true);
        header.setBackground(UiTheme.HEADER_BG);
        header.setBorder(UiTheme.headerBorder());
        add(header, BorderLayout.NORTH);

        menuListModel = new DefaultListModel<>();
        for (String item : items) {
            menuListModel.addElement(item);
        }

        menuList = new JList<>(menuListModel);
        menuList.setFont(UiTheme.plainFont(fontSize));
        menuList.setSelectionBackground(UiTheme.SELECTION_BG);
        menuList.setFixedCellHeight(cellHeight);

        add(new JScrollPane(menuList), BorderLayout.CENTER);
    }

    public int getSelectedIndex() {
        return menuList.getSelectedIndex();
    }

    public void setSelectedIndex(int index) {
        if (index >= 0 && index < menuListModel.size()) {
            menuList.setSelectedIndex(index);
            menuList.ensureIndexIsVisible(index);
        }
    }

    public int getItemCount() {
        return menuListModel.size();
    }

    public String getSelectedValue() {
        return menuList.getSelectedValue();
    }

    public JList<String> getList() {
        return menuList;
    }

    @Override
    public void requestFocus() {
        menuList.requestFocusInWindow();
    }
}
