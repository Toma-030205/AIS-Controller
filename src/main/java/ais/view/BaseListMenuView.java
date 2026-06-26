package ais.view;

import java.awt.BorderLayout;
import javax.swing.BorderFactory;
import javax.swing.DefaultListModel;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingConstants;

public class BaseListMenuView extends JPanel {
    private JList<String> menuList;
    private DefaultListModel<String> menuListModel;

    protected BaseListMenuView(String title, String[] items, int fontSize, int cellHeight) {
        setLayout(new BorderLayout());
        add(createHeader(title, UiTheme.HEADER_BG), BorderLayout.NORTH);
        createList(items, fontSize, cellHeight);
        add(new JScrollPane(menuList), BorderLayout.CENTER);
    }

    protected BaseListMenuView(String title, String subtitle, String[] items, int fontSize, int cellHeight) {
        setLayout(new BorderLayout());

        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.add(createHeader(title, UiTheme.HEADER_BG), BorderLayout.NORTH);
        headerPanel.add(createHeader(subtitle, UiTheme.SUB_HEADER_BG), BorderLayout.CENTER);
        add(headerPanel, BorderLayout.NORTH);

        createList(items, fontSize, cellHeight);
        menuList.setBorder(BorderFactory.createEmptyBorder(8, 12, 25, 12));
        add(menuList, BorderLayout.CENTER);
    }

    private JLabel createHeader(String text, java.awt.Color background) {
        JLabel header = new JLabel(text, SwingConstants.LEFT);
        header.setFont(UiTheme.headerFont());
        header.setOpaque(true);
        header.setBackground(background);
        header.setBorder(UiTheme.headerBorder());
        return header;
    }

    private void createList(String[] items, int fontSize, int cellHeight) {
        menuListModel = new DefaultListModel<>();
        for (String item : items) {
            menuListModel.addElement(item);
        }

        menuList = new JList<>(menuListModel);
        menuList.setFont(UiTheme.plainFont(fontSize));
        menuList.setSelectionBackground(UiTheme.SELECTION_BG);
        menuList.setFixedCellHeight(cellHeight);
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

    public String getSelectedItem() {
        return getSelectedValue();
    }

    public void selectNext() {
        int index = menuList.getSelectedIndex();
        if (index < menuListModel.size() - 1) {
            setSelectedIndex(index + 1);
        }
    }

    public void selectPrev() {
        int index = menuList.getSelectedIndex();
        if (index > 0) {
            setSelectedIndex(index - 1);
        }
    }

    public void resetCursor() {
        setSelectedIndex(0);
    }

    public void ensureIndexIsVisible(int index) {
        menuList.ensureIndexIsVisible(index);
    }

    public JList<String> getList() {
        return menuList;
    }

    @Override
    public void requestFocus() {
        menuList.requestFocusInWindow();
    }
}
