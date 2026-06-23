package ais.view;

import java.awt.*;
import javax.swing.*;



public class TxTransmittingView extends JPanel {

    private JLabel headerLabel;
    private JLabel messageLabel;

    public TxTransmittingView() {
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

        /* ===== メッセージ ===== */
        messageLabel = new JLabel("NOW TRANSMITTING...", SwingConstants.CENTER);
        messageLabel.setFont(new Font("SansSerif", Font.BOLD, 45));
        add(messageLabel, BorderLayout.CENTER);
    }
}
