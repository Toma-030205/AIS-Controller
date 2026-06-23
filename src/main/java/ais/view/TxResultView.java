package ais.view;

import java.awt.*;
import javax.swing.*;



public class TxResultView extends JPanel {

    private JLabel headerLabel;
    private JLabel resultLabel;
    private JLabel okLabel;
    private boolean cursorOn = true;

    private static final Color CURSOR_BG = UiTheme.INPUT_CURSOR_BG;
    private static final Color NORMAL_BG = UiTheme.PANEL_BG;


    public TxResultView() {
        setLayout(new BorderLayout());
        initComponents();
        setCursor(true);
    }

    private void initComponents() {

        /* ===== ヘッダ ===== */
        headerLabel = new JLabel("EDIT AND TX", SwingConstants.LEFT);
        headerLabel.setFont(new Font("Meiryo UI", Font.BOLD, 32));
        headerLabel.setOpaque(true);
        headerLabel.setBackground(UiTheme.HEADER_BG);
        headerLabel.setBorder(BorderFactory.createEmptyBorder(8, 12, 8, 12));
        add(headerLabel, BorderLayout.NORTH);

        /* ===== RESULT 表示 ===== */
        resultLabel = new JLabel("RESULT : OK", SwingConstants.CENTER);
        resultLabel.setFont(new Font("Meiryo UI", Font.BOLD, 45));

        JPanel centerPanel = new JPanel(new BorderLayout());
        centerPanel.add(resultLabel, BorderLayout.CENTER);
        add(centerPanel, BorderLayout.CENTER);

        /* ===== OK 表示（操作は Controller 側） ===== */
        okLabel = new JLabel("[ OK ]", SwingConstants.CENTER);
        okLabel.setFont(new Font("Meiryo UI", Font.BOLD, 36));
        okLabel.setOpaque(true);
        okLabel.setBackground(CURSOR_BG);
        okLabel.setBorder(BorderFactory.createEmptyBorder(10, 0, 20, 0));
        add(okLabel, BorderLayout.SOUTH);
    }

    /* =====================================================
     * Controller 用 API
     * ===================================================== */

    public void setResultText(String text) {
        resultLabel.setText(text);
    }

    public void setCursor(boolean on) {
        this.cursorOn = on;
        okLabel.setBackground(on ? CURSOR_BG : NORMAL_BG);
    }

}
