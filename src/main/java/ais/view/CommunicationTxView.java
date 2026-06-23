package ais.view;

import java.awt.*;
import javax.swing.*;



public class CommunicationTxView extends JPanel {

    private JLabel header;
    private JLabel message;

    public CommunicationTxView() {
        setLayout(new BorderLayout());

        header = new JLabel("COMMUNICATION TEST");
        header.setFont(new Font("Meiryo UI", Font.BOLD, 32));
        header.setOpaque(true);
        header.setBackground(UiTheme.HEADER_BG);
        header.setBorder(BorderFactory.createEmptyBorder(8, 12, 8, 12));

        message = new JLabel("NOW TRANSMITTING...");
        message.setFont(new Font("Meiryo UI", Font.BOLD, 28));
        message.setHorizontalAlignment(SwingConstants.CENTER);

        add(header, BorderLayout.NORTH);
        add(message, BorderLayout.CENTER);
    }
}
