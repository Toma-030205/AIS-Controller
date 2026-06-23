package ais.view;

import ais.model.TxMessage;
import java.awt.*;
import javax.swing.*;



public class TextViewScreen extends JPanel {

    private JLabel headerLabel;
    private JTextArea textArea;

    public TextViewScreen() {
        setLayout(new BorderLayout());
        initComponents();
    }

    private void initComponents() {

        /* ===== ヘッダ ===== */
        headerLabel = new JLabel("TEXT VIEW SCREEN", SwingConstants.LEFT);
        headerLabel.setFont(new Font("Meiryo UI", Font.BOLD, 32));
        headerLabel.setOpaque(true);
        headerLabel.setBackground(UiTheme.HEADER_BG);
        headerLabel.setBorder(BorderFactory.createEmptyBorder(8, 12, 8, 12));
        add(headerLabel, BorderLayout.NORTH);

        /* ===== 本文 ===== */
        textArea = new JTextArea();
        textArea.setFont(new Font("Meiryo UI", Font.BOLD, 26));
        textArea.setEditable(false);
        textArea.setLineWrap(true);
        textArea.setWrapStyleWord(true);
        textArea.setFocusable(false);
        textArea.setBorder(BorderFactory.createEmptyBorder(16, 16, 16, 16));

        add(textArea, BorderLayout.CENTER);
    }

    /* =====================================================
     * Model 連携 API
     * ===================================================== */

    public void setMessage(TxMessage msg) {
        textArea.setText(msg.text);
        textArea.setCaretPosition(0);
    }
}
