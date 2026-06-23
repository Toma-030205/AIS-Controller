package ais.view;

import ais.model.RxMessage;
import ais.model.RxTrayModel;
import java.awt.*;
import javax.swing.*;



public class RxTrayListView extends JPanel {

    private JLabel headerLabel;
    private JList<String> list;
    private DefaultListModel<String> listModel;
    private JScrollPane scrollPane;

    public RxTrayListView() {
        setLayout(new BorderLayout());
        initComponents();
    }

    private void initComponents() {

        /* ===== ヘッダ ===== */
        headerLabel = new JLabel("OTHERS MESSAGES TRAY", SwingConstants.LEFT);
        headerLabel.setFont(new Font("Meiryo UI", Font.BOLD, 32));
        headerLabel.setOpaque(true);
        headerLabel.setBackground(UiTheme.HEADER_BG);
        headerLabel.setBorder(BorderFactory.createEmptyBorder(8, 12, 8, 12));
        add(headerLabel, BorderLayout.NORTH);

        /* ===== 一覧 ===== */
        listModel = new DefaultListModel<>();
        list = new JList<>(listModel);
        list.setFont(new Font("Meiryo UI", Font.PLAIN, 43));
        list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        list.setFocusable(false);

        /* ===== スクロール ===== */
        scrollPane = new JScrollPane(list);
        scrollPane.setVerticalScrollBarPolicy(
                ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER
        );
        scrollPane.setHorizontalScrollBarPolicy(
                ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER
        );

        add(scrollPane, BorderLayout.CENTER);
    }

    /* =====================================================
     * Model 連携 API
     * ===================================================== */

    public void updateFromModel(RxTrayModel model) {
        listModel.clear();
        for (RxMessage msg : model.getAll()) {
            listModel.addElement(msg.getListLabel());
        }
        if (!listModel.isEmpty()) {
            list.setSelectedIndex(0);
            list.ensureIndexIsVisible(0);
        }
    }

    public int getSelectedIndex() {
        return list.getSelectedIndex();
    }

    public void selectNext() {
        int i = list.getSelectedIndex();
        if (i < listModel.size() - 1) {
            list.setSelectedIndex(i + 1);
            list.ensureIndexIsVisible(i + 1);
        }
    }

    public void selectPrev() {
        int i = list.getSelectedIndex();
        if (i > 0) {
            list.setSelectedIndex(i - 1);
            list.ensureIndexIsVisible(i - 1);
        }
    }

    public JList<String> getList() {
        return list;
    }
}
