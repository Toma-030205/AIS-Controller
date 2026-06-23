package ais.view;

import ais.model.EditAndTxModel;
import java.awt.*;
import javax.swing.*;



public class EditAndTxView extends JPanel {

    private final EditAndTxModel model;

    private JLabel headerLabel;
    private JLabel[] rowLabels;
    private JLabel[] valueLabels;
    private JLabel[] mmsiDigits;
    private JPanel contentPanel;   

    private static final Color CURSOR_BG = UiTheme.INPUT_CURSOR_BG;
    private static final Color NORMAL_BG = Color.WHITE;
    private static final Color LEFT_CURSOR_BG = new Color(200, 200, 255);

    public EditAndTxView(EditAndTxModel model) {
        this.model = model;
        setLayout(new BorderLayout());
        initComponents();
        refresh();
    }

    private void initComponents() {
        JLabel header = new JLabel("EDIT AND TX", SwingConstants.LEFT);
        header.setFont(new Font("Meiryo UI", Font.BOLD, 32));
        header.setOpaque(true);
        header.setBackground(UiTheme.HEADER_BG);
        header.setBorder(BorderFactory.createEmptyBorder(8, 12, 8, 12));
        add(header, BorderLayout.NORTH);

        // ===== 中身用パネル =====
        contentPanel = new JPanel(null);
        contentPanel.setPreferredSize(new Dimension(400, 200)); // ★ 追加
        add(contentPanel, BorderLayout.CENTER);

        String[] rows = {
                "1. FORMAT",
                "       MMSI",
                "2. CATEGORY",
                "3. FUNCTION",
                "4. REPLY",
                "5. CH",
                "6. NUMBER OF RETRY"
        };

        rowLabels = new JLabel[rows.length];
        valueLabels = new JLabel[rows.length];

        int y = 10;     
        int rowGap = 40;

        for (int i = 0; i < rows.length; i++) {
            rowLabels[i] = new JLabel(rows[i]);
            rowLabels[i].setFont(new Font("Meiryo UI", Font.PLAIN, 30));
            rowLabels[i].setBounds(5, y, 350, 38);
            contentPanel.add(rowLabels[i]);

            valueLabels[i] = new JLabel();
            valueLabels[i].setFont(new Font("Meiryo UI", Font.PLAIN, 30));
            valueLabels[i].setOpaque(true);
            valueLabels[i].setBounds(380, y, 450, 38);
            contentPanel.add(valueLabels[i]);

            y += rowGap;
        }

        mmsiDigits = new JLabel[9];
        int mmsiY = 10 + 40; // MMSI 行（FORMAT の次）

        for (int i = 0; i < 9; i++) {
            mmsiDigits[i] = new JLabel("0", SwingConstants.CENTER);
            mmsiDigits[i].setFont(new Font("Meiryo UI", Font.PLAIN, 22));
            mmsiDigits[i].setOpaque(true);
            mmsiDigits[i].setBorder(BorderFactory.createLineBorder(Color.GRAY));
            mmsiDigits[i].setBounds(380 + i * 32, mmsiY, 30, 38);
            contentPanel.add(mmsiDigits[i]);
        }

        // ★ 既存の MMSI valueLabel は使わない
        valueLabels[1].setVisible(false);
    }

    /* =====================================================
     * Model → View 反映
     * ===================================================== */
    public void refresh() {

        valueLabels[0].setText(model.getFormatText());
        valueLabels[2].setText(model.getCategoryText());
        valueLabels[3].setText(model.getFunctionText());
        valueLabels[4].setText(model.getReplyText());
        valueLabels[5].setText(model.getChannelText());
        valueLabels[6].setText(model.getRetryText());

        boolean addressed = model.getFormatText().equals("ADDRESSED");

        rowLabels[1].setVisible(addressed);
        for (JLabel d : mmsiDigits) {
            d.setVisible(addressed);
        }

        valueLabels[4].setVisible(addressed);
        rowLabels[4].setVisible(addressed);

        valueLabels[6].setVisible(addressed);
        rowLabels[6].setVisible(addressed);

        for (JLabel label : valueLabels) {
            label.setBackground(NORMAL_BG);
        }

        if (addressed) {
            String mmsi = model.getMmsiText();
            int cursor = model.getMmsiCursor();

            for (int i = 0; i < 9; i++) {
                mmsiDigits[i].setText(String.valueOf(mmsi.charAt(i)));

                if (model.getEditMode() == EditAndTxModel.EditMode.MMSI_EDIT
                        && i == cursor) {
                    mmsiDigits[i].setBackground(CURSOR_BG);
                } else {
                    mmsiDigits[i].setBackground(NORMAL_BG);
                }
            }
        }

        // 左側カーソル初期化
        for (JLabel label : rowLabels) {
            label.setOpaque(false);
            label.setBackground(null);
        }

        // 右側カーソル初期化
        for (JLabel label : valueLabels) {
            label.setBackground(NORMAL_BG);
        }

        int rowIndex = model.getCurrentRow().ordinal();

        // ===== 左側カーソル（行選択モード）=====
        if (model.getEditMode() == EditAndTxModel.EditMode.ROW_SELECT) {
            rowLabels[rowIndex].setOpaque(true);
            rowLabels[rowIndex].setBackground(new Color(200, 200, 255));
        } // ===== 右側カーソル（値編集モード）=====
        else if (model.getEditMode() == EditAndTxModel.EditMode.VALUE_EDIT) {
            valueLabels[rowIndex].setBackground(CURSOR_BG);
        }
        repaint();
    }

}
