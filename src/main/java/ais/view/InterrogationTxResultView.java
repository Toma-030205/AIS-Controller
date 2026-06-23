package ais.view;

import java.awt.*;
import javax.swing.*;




public class InterrogationTxResultView extends JPanel {

    private JLabel header;
    private JLabel resultLabel;
    private JLabel okLabel;

    private static final Color CURSOR_BG = UiTheme.SELECTION_BG;
    private static final Color NORMAL_BG = UiTheme.PANEL_BG;

    public InterrogationTxResultView() {
        setLayout(new BorderLayout());

        /* ===== HEADER ===== */
        header = new JLabel("INTERROGATION");
        header.setFont(new Font("Meiryo UI", Font.BOLD, 32));
        header.setOpaque(true);
        header.setBackground(UiTheme.HEADER_BG);
        header.setBorder(BorderFactory.createEmptyBorder(8, 12, 8, 12));

        /* ===== RESULT ===== */
        resultLabel = new JLabel("", SwingConstants.CENTER);
        resultLabel.setFont(new Font("Meiryo UI", Font.BOLD, 28));
        resultLabel.setBorder(BorderFactory.createEmptyBorder(40, 0, 20, 0));

        /* ===== OK LABEL ===== */
        okLabel = new JLabel("[ OK ]", SwingConstants.CENTER);
        okLabel.setFont(new Font("Meiryo UI", Font.PLAIN, 28));
        okLabel.setOpaque(true);
        okLabel.setBackground(CURSOR_BG);
        okLabel.setBorder(BorderFactory.createEmptyBorder(10, 40, 10, 40));

        /* ===== CENTER PANEL ===== */
        JPanel center = new JPanel();
        center.setLayout(new BoxLayout(center, BoxLayout.Y_AXIS));
        center.add(resultLabel);
        center.add(Box.createVerticalStrut(30));
        center.add(okLabel);

        /* ===== ALIGN CENTER ===== */
        resultLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        okLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        add(header, BorderLayout.NORTH);
        add(center, BorderLayout.CENTER);
    }

    /* =====================================================
     * API
     * ===================================================== */
    public void setResult(boolean ok) {
        resultLabel.setText(ok ? "RESULT : OK" : "RESULT : NG");
    }

    /**
     * 将来カーソル ON/OFF を切り替える用
     */
    public void setCursor(boolean selected) {
        okLabel.setBackground(selected ? CURSOR_BG : NORMAL_BG);
    }
}
