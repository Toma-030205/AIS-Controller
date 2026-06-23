package ais.view;

import ais.model.PersonsInputModel;
import java.awt.*;
import javax.swing.*;




public class PersonsInputView extends JPanel {

    private PersonsInputModel model;
    private JLabel headerLabel;
    private JLabel[] digitLabels;
    private static final Color CURSOR_BG = UiTheme.INPUT_CURSOR_BG;
    private JLabel unitLabel; // PersonsInputView のフィールドに追加

    public PersonsInputView(PersonsInputModel model) {
        this.model = model;
        setLayout(new BorderLayout());
        initComponents();
        refresh();
    }

    private void initComponents() {
        headerLabel = new JLabel("PERSONS ON BOARD");
        headerLabel.setFont(new Font("Meiryo UI", Font.BOLD, 32));
        headerLabel.setOpaque(true);
        headerLabel.setBackground(UiTheme.HEADER_BG);
        headerLabel.setBorder(BorderFactory.createEmptyBorder(8, 12, 8, 12));
        add(headerLabel, BorderLayout.NORTH);

        JPanel center = new JPanel(new FlowLayout(FlowLayout.CENTER, 4, 20));
        digitLabels = new JLabel[4];

        unitLabel = new JLabel(" ");
        unitLabel.setFont(new Font("Consolas", Font.BOLD, 60));
        unitLabel.setOpaque(false);
        center.add(unitLabel);

        for (int i = 0; i < 4; i++) {
            JLabel lbl = new JLabel(" ");
            lbl.setFont(new Font("Consolas", Font.BOLD, 60));
            lbl.setOpaque(true);
            lbl.setBackground(getBackground());
            lbl.setPreferredSize(new Dimension(48, 80));
            lbl.setHorizontalAlignment(SwingConstants.CENTER);
            digitLabels[i] = lbl;
            center.add(lbl);
        }

        add(center, BorderLayout.CENTER);
    }

    public void refresh() {
        int[] digits = model.getDigits();
        int cursor = model.getCursor();
        int value = model.getValue();

        if (value >= 8191) {
            // 数字桁を非表示
            for (JLabel lbl : digitLabels) {
                lbl.setVisible(false);
            }
            // unitLabel に長い文字を表示
            unitLabel.setVisible(true);
            unitLabel.setText("8191 OR MORE");
            return;
        }

        // 通常表示
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

        unitLabel.setVisible(true);
        unitLabel.setText(""); // 通常表示時は空
    }


    // Controller用API
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
