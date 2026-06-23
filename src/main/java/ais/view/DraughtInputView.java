package ais.view;

import ais.model.DraughtInputModel;
import java.awt.*;
import javax.swing.*;



public class DraughtInputView extends JPanel {

    private DraughtInputModel model;

    private JLabel headerLabel;

    // 編集桁用（3 桁）
    private JLabel[] digitLabels;

    // 区切り・単位用
    private JLabel dotLabel;
    private JLabel unitLabel;

    // カーソル表示
    private static final Color CURSOR_BG = UiTheme.INPUT_CURSOR_BG;
    
    public DraughtInputView(DraughtInputModel model) {
        this.model = model;
        setLayout(new BorderLayout());
        initComponents();
        refresh();
    }

    private void initComponents() {

        /* ===== ヘッダ ===== */
        headerLabel = new JLabel("DRAUGHT (m)");
        headerLabel.setFont(new Font("Meiryo UI", Font.BOLD, 32));
        headerLabel.setOpaque(true);
        headerLabel.setBackground(UiTheme.HEADER_BG);
        headerLabel.setBorder(BorderFactory.createEmptyBorder(8, 12, 8, 12));
        add(headerLabel, BorderLayout.NORTH);

        /* ===== 表示部 ===== */
        JPanel center = new JPanel(new FlowLayout(FlowLayout.CENTER, 4, 20));

        digitLabels = new JLabel[3];

        // tens
        addDigit(center, 0);

        // ones
        addDigit(center, 1);

        // "."
        dotLabel = createSeparator(".");
        center.add(dotLabel);

        // decimal
        addDigit(center, 2);

        // " m"
        unitLabel = createSeparator(" m");
        center.add(unitLabel);

        add(center, BorderLayout.CENTER);
    }

    private void addDigit(JPanel parent, int index) {
        JLabel lbl = new JLabel(" ");
        lbl.setFont(new Font("Consolas", Font.BOLD, 60));
        lbl.setOpaque(true);
        lbl.setBackground(getBackground());
        lbl.setPreferredSize(new Dimension(48, 80));
        lbl.setHorizontalAlignment(SwingConstants.CENTER);

        digitLabels[index] = lbl;
        parent.add(lbl);
    }

    private JLabel createSeparator(String text) {
        JLabel lbl = new JLabel(text);
        lbl.setFont(new Font("Consolas", Font.BOLD, 60));
        lbl.setOpaque(false);
        return lbl;
    }

    /* =====================================================
     * 表示更新
     * ===================================================== */

    public void refresh() {

        int[] digits = model.getDigits();
        int cursor = model.getCursor();

        for (int i = 0; i < digitLabels.length; i++) {
            JLabel lbl = digitLabels[i];
            lbl.setText(String.valueOf(digits[i]));

            if (i == cursor) {
                lbl.setBackground(CURSOR_BG);
            } else {
                lbl.setBackground(getBackground());
            }

            lbl.setVisible(true);
        }

        dotLabel.setVisible(true);
        unitLabel.setVisible(true);
        unitLabel.setText(" m");
    }


    
    /* =====================================================
     * Controller 用 API
     * ===================================================== */

    public void moveCursorLeft() {
        model.moveCursorLeft();
        refresh();
    }

    public void moveCursorRight() {
        model.moveCursorRight();
        refresh();
    }

    public void increment() {
        model.increment();
        refresh();
    }

    public void decrement() {
        model.decrement();
        refresh();
    }

    @Override
    public void requestFocus() {
        requestFocusInWindow();
    }

    @Override
    public boolean isFocusable() {
        return true;
    }
}
