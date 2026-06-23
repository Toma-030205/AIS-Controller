package ais.view;

import java.awt.*;
import javax.swing.*;




public class EditAndTxTxConfirmView extends JPanel {

    private JLabel headerLabel;
    private JLabel messageLabel;

    private JList<String> optionList;
    private DefaultListModel<String> optionModel;

    public EditAndTxTxConfirmView() {
        setLayout(new BorderLayout());
        initComponents();
    }

    private void initComponents() {

        /* ===== ヘッダ ===== */
        headerLabel = new JLabel("EDIT AND TX", SwingConstants.LEFT);
        headerLabel.setFont(new Font("Meiryo UI", Font.BOLD, 32));
        headerLabel.setOpaque(true);
        headerLabel.setBackground(UiTheme.HEADER_BG);
        headerLabel.setBorder(BorderFactory.createEmptyBorder(8, 12, 8, 12));
        add(headerLabel, BorderLayout.NORTH);

        /* ===== 中央メッセージ（余白を詰める） ===== */
        messageLabel = new JLabel("START TRANSMIT THIS MESSAGE?", SwingConstants.CENTER);
        messageLabel.setFont(new Font("SansSerif", Font.BOLD, 45));

        JPanel messagePanel = new JPanel(new BorderLayout());
        messagePanel.setBorder(BorderFactory.createEmptyBorder(10, 0, 5, 0)); // ← 詰める
        messagePanel.add(messageLabel, BorderLayout.CENTER);

        add(messagePanel, BorderLayout.CENTER);

        /* ===== 選択肢 ===== */
        optionModel = new DefaultListModel<>();
        optionModel.addElement("[OK]");
        optionModel.addElement("[CANCEL]");

        optionList = new JList<>(optionModel);
        optionList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        optionList.setSelectedIndex(0);
        optionList.setLayoutOrientation(JList.HORIZONTAL_WRAP);
        optionList.setVisibleRowCount(1);
        optionList.setFocusable(false);

        /* フォントを大きく */
        optionList.setFont(new Font("SansSerif", Font.BOLD, 42));

        /* セルサイズを内容に応じて変えるレンダラ */
        optionList.setCellRenderer(new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(
                    JList<?> list, Object value, int index,
                    boolean isSelected, boolean cellHasFocus) {

                JLabel label = (JLabel) super.getListCellRendererComponent(
                        list, value, index, isSelected, cellHasFocus);

                label.setHorizontalAlignment(SwingConstants.CENTER);
                label.setBorder(BorderFactory.createEmptyBorder(12, 30, 12, 30)); // ← 大きさ調整

                if (isSelected) {
                    label.setBackground(new Color(255, 200, 100));
                    label.setForeground(Color.BLACK);
                } else {
                    label.setBackground(Color.LIGHT_GRAY);
                    label.setForeground(Color.BLACK);
                }

                label.setOpaque(true);
                return label;
            }
        });

        JPanel optionPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 50, 5));
        optionPanel.add(optionList);

        add(optionPanel, BorderLayout.SOUTH);
    }

    /* =====================================================
     * Controller 用 API
     * ===================================================== */
    public int getSelectedIndex() {
        return optionList.getSelectedIndex();
    }

    public void selectNext() {
        int idx = optionList.getSelectedIndex();
        if (idx < optionModel.size() - 1) {
            optionList.setSelectedIndex(idx + 1);
        }
    }

    public void selectPrev() {
        int idx = optionList.getSelectedIndex();
        if (idx > 0) {
            optionList.setSelectedIndex(idx - 1);
        }
    }
}
