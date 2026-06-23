package ais.view;

import ais.model.ShipTypeUSInputModel;
import java.awt.*;
import javax.swing.*;



public class ShipTypeUSInputView extends JPanel {

    private ShipTypeUSInputModel model;
    private JLabel headerLabel;
    private JLabel valueLabel;

    private static final Color CURSOR_BG = UiTheme.INPUT_CURSOR_BG;

    public ShipTypeUSInputView(ShipTypeUSInputModel model) {
        this.model = model;
        setLayout(new BorderLayout());
        initComponents();
        refresh();
    }

    private void initComponents() {
        // ヘッダ
        headerLabel = new JLabel("SHIP TYPE U.S.");
        headerLabel.setFont(new Font("Meiryo UI", Font.BOLD, 32));
        headerLabel.setOpaque(true);
        headerLabel.setBackground(UiTheme.HEADER_BG);
        headerLabel.setBorder(BorderFactory.createEmptyBorder(8, 12, 8, 12));
        add(headerLabel, BorderLayout.NORTH);

        // 中央に値ラベル
        JPanel center = new JPanel(new FlowLayout(FlowLayout.CENTER, 4, 20));
        valueLabel = new JLabel("OFF");
        valueLabel.setFont(new Font("Consolas", Font.BOLD, 60));
        valueLabel.setOpaque(true);
        valueLabel.setBackground(CURSOR_BG);
        valueLabel.setPreferredSize(new Dimension(160, 80));
        valueLabel.setHorizontalAlignment(SwingConstants.CENTER);
        center.add(valueLabel);

        add(center, BorderLayout.CENTER);
    }

    public void refresh() {
        valueLabel.setText(model.getValue());
        valueLabel.setBackground(CURSOR_BG);
    }

    // Controller 用 API
    public void toggle() {
        model.toggle();
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
