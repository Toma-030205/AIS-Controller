package ais.view;

import ais.model.ETAInputModel;
import java.awt.*;
import javax.swing.*;



public class ETAInputView extends JPanel {

    private ETAInputModel model;

    private JLabel headerLabel;
    private JLabel etaLabel;

    // 編集対象の 8 桁のみ
    private JLabel[] digitLabels;

    // カーソル強調用
    private static final Color CURSOR_BG = UiTheme.INPUT_CURSOR_BG;
    

    public ETAInputView(ETAInputModel model) {
        this.model = model;
        setLayout(new BorderLayout());
        initComponents();
        refresh();
    }

    private void initComponents() {

        /* ===== ヘッダ ===== */
        headerLabel = new JLabel("ETA (UTC)");
        headerLabel.setFont(new Font("Meiryo UI", Font.BOLD, 32));
        headerLabel.setOpaque(true);
        headerLabel.setBackground(UiTheme.HEADER_BG);
        headerLabel.setBorder(BorderFactory.createEmptyBorder(8, 12, 8, 12));
        add(headerLabel, BorderLayout.NORTH);

        /* ===== ETA 表示 ===== */
        JPanel center = new JPanel(new FlowLayout(FlowLayout.CENTER, 4, 20));

        digitLabels = new JLabel[8];   // ← 編集桁は 8 個だけ

        // MM
        addDigit(center, 0);
        addDigit(center, 1);

        // "/"
        center.add(createSeparator("/"));

        // DD
        addDigit(center, 2);
        addDigit(center, 3);

        // " "
        center.add(createSeparator(" "));

        // HH
        addDigit(center, 4);
        addDigit(center, 5);

        // ":"
        center.add(createSeparator(":"));

        // MM
        addDigit(center, 6);
        addDigit(center, 7);

        add(center, BorderLayout.CENTER);

        
    }

    /* =========================
     * 表示更新
     * ========================= */
    public void refresh() {

        int cursor = model.getCursor();   // 0〜7（編集桁）
        int[] digits = model.getDigits(); // 表示用に取得（getter想定）

        for (int i = 0; i < digitLabels.length; i++) {

            JLabel lbl = digitLabels[i];
            lbl.setText(String.valueOf(digits[i]));

            if (i == cursor) {
                lbl.setBackground(CURSOR_BG);
            } else {
                lbl.setBackground(getBackground());
            }
        }
    }

    private void addDigit(JPanel parent, int digitIndex) {

        JLabel lbl = new JLabel(" ");
        lbl.setFont(new Font("Consolas", Font.BOLD, 60));
        lbl.setOpaque(true);
        lbl.setBackground(getBackground());
        lbl.setPreferredSize(new Dimension(48, 80));
        lbl.setHorizontalAlignment(SwingConstants.CENTER);

        digitLabels[digitIndex] = lbl;
        parent.add(lbl);
    }

    private JLabel createSeparator(String s) {
        JLabel lbl = new JLabel(s);
        lbl.setFont(new Font("Consolas", Font.BOLD, 60));
        lbl.setOpaque(false);
        return lbl;
    }




    /* =========================
     * カーソル操作 API（Controller から）
     * ========================= */
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
