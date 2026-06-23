package ais.view;

import ais.model.InterrogationModel;
import java.awt.*;
import javax.swing.*;



public class InterrogationView extends JPanel {

    private final InterrogationModel model;

    private JLabel headerLabel;
    private JPanel contentPanel;

    private JLabel[] rowLabels;
    private JLabel[] valueLabels;
    private JLabel[] mmsiDigits;

    private JLabel resultLabel;

    private static final Color CURSOR_BG = UiTheme.INPUT_CURSOR_BG;
    private static final Color NORMAL_BG = Color.WHITE;
    private static final Color LEFT_CURSOR_BG = new Color(200, 200, 255);

    public InterrogationView(InterrogationModel model) {
        this.model = model;
        setLayout(new BorderLayout());
        initComponents();
        refresh();
    }

    private void initComponents() {

        /* ===== HEADER ===== */
        headerLabel = new JLabel("INTERROGATION", SwingConstants.LEFT);
        headerLabel.setFont(new Font("Meiryo UI", Font.BOLD, 32));
        headerLabel.setOpaque(true);
        headerLabel.setBackground(UiTheme.HEADER_BG);
        headerLabel.setBorder(BorderFactory.createEmptyBorder(8, 12, 8, 12));
        add(headerLabel, BorderLayout.NORTH);

        /* ===== CONTENT ===== */
        contentPanel = new JPanel(null);
        contentPanel.setPreferredSize(new Dimension(800, 300));
        add(contentPanel, BorderLayout.CENTER);

        String[] rows = {
                "1. MMSI",
                "2. REQUEST",
                "RESULT"
        };

        rowLabels = new JLabel[rows.length];
        valueLabels = new JLabel[rows.length];

        int y = 20;
        int rowGap = 60;

        for (int i = 0; i < rows.length; i++) {
            rowLabels[i] = new JLabel(rows[i]);
            rowLabels[i].setFont(new Font("Meiryo UI", Font.PLAIN, 30));
            rowLabels[i].setBounds(10, y, 300, 40);
            contentPanel.add(rowLabels[i]);

            valueLabels[i] = new JLabel();
            valueLabels[i].setFont(new Font("Meiryo UI", Font.PLAIN, 28));
            valueLabels[i].setOpaque(true);
            valueLabels[i].setBounds(320, y, 460, 40);
            contentPanel.add(valueLabels[i]);

            y += rowGap;
        }

        /* ===== MMSI DIGITS ===== */
        mmsiDigits = new JLabel[9];
        int mmsiY = 20;

        for (int i = 0; i < 9; i++) {
            mmsiDigits[i] = new JLabel("0", SwingConstants.CENTER);
            mmsiDigits[i].setFont(new Font("Meiryo UI", Font.PLAIN, 22));
            mmsiDigits[i].setOpaque(true);
            mmsiDigits[i].setBorder(BorderFactory.createLineBorder(Color.GRAY));
            mmsiDigits[i].setBounds(320 + i * 36, mmsiY, 34, 40);
            contentPanel.add(mmsiDigits[i]);
        }

        // MMSI 行の valueLabel は使用しない
        valueLabels[0].setVisible(false);

        /* ===== RESULT LABEL ===== */
        resultLabel = valueLabels[2];
        resultLabel.setText("");
    }

    /* =====================================================
     * Model → View 反映
     * ===================================================== */
    public void refresh() {

        /* ===== REQUEST ===== */
        valueLabels[1].setText(model.getRequestText());

        /* ===== RESULT ===== */
        resultLabel.setText(model.getResultText());

        /* ===== MMSI ===== */
        String mmsi = model.getMmsiText();
        int cursor = model.getMmsiCursor();

        for (int i = 0; i < 9; i++) {
            mmsiDigits[i].setText(String.valueOf(mmsi.charAt(i)));

            if (model.getEditMode() == InterrogationModel.EditMode.MMSI_EDIT
                    && i == cursor) {
                mmsiDigits[i].setBackground(CURSOR_BG);
            } else {
                mmsiDigits[i].setBackground(NORMAL_BG);
            }
        }

        /* ===== CURSOR RESET ===== */
        for (JLabel label : rowLabels) {
            label.setOpaque(false);
        }
        for (JLabel label : valueLabels) {
            label.setBackground(NORMAL_BG);
        }

        int rowIndex = model.getCurrentRow().ordinal();

        /* ===== LEFT / RIGHT CURSOR ===== */
        if (model.getEditMode() == InterrogationModel.EditMode.ROW_SELECT) {
            rowLabels[rowIndex].setOpaque(true);
            rowLabels[rowIndex].setBackground(LEFT_CURSOR_BG);
        } else {
            valueLabels[rowIndex].setBackground(CURSOR_BG);
        }

        repaint();
    }
}
