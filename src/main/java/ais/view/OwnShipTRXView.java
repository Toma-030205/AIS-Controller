package ais.view;

import ais.model.OwnShipInfo;
import ais.util.DetailRowUtil;
import java.awt.*;
import javax.swing.*;



public class OwnShipTRXView extends JPanel {

    private JPanel owntrxPagePanel;
    private JLabel owntrxNumberLabel;

    public OwnShipTRXView() {
        setLayout(new BorderLayout());

        JLabel owntrxHeader = new JLabel("OWN SHIP'S TRX");
        owntrxHeader.setFont(new Font("Meiryo UI", Font.BOLD, 32));
        owntrxHeader.setOpaque(true);
        owntrxHeader.setBackground(UiTheme.HEADER_BG);
        owntrxHeader.setBorder(BorderFactory.createEmptyBorder(8, 12, 8, 12));

        owntrxPagePanel = new JPanel();
        owntrxPagePanel.setLayout(new GridLayout(0, 1));  // 縦に並べるだけでOK

        owntrxNumberLabel = new JLabel("1 / 4");
        owntrxNumberLabel.setFont(new Font("Meiryo UI", Font.BOLD, 32));
        owntrxNumberLabel.setBackground(UiTheme.HEADER_BG);

        JPanel owntrxDetailCard = new JPanel(new BorderLayout());
        owntrxDetailCard.add(owntrxHeader, BorderLayout.NORTH);
        owntrxDetailCard.add(owntrxPagePanel, BorderLayout.CENTER);
        owntrxDetailCard.add(owntrxNumberLabel, BorderLayout.SOUTH);
        add(owntrxDetailCard, BorderLayout.CENTER);
    }
    
   //自船無線運用画面の中身
    public void updateowntrxPage(OwnShipInfo myship, int page) {
    owntrxPagePanel.removeAll();  // ← 一度クリア

    switch(page) {
        case 1:
            DetailRowUtil.addRow(owntrxPagePanel," CH A", "2087");
            DetailRowUtil.addRow(owntrxPagePanel," CH B", "2088");
            DetailRowUtil.addRow(owntrxPagePanel," TX POWER", "HIGH");
            DetailRowUtil.addRow(owntrxPagePanel," MODE(A,B)", "");
            DetailRowUtil.addRow(owntrxPagePanel," CH A", "TX/RX");
            DetailRowUtil.addRow(owntrxPagePanel," CH B", "TX/RX");
            break;

        case 2:
            DetailRowUtil.addRow(owntrxPagePanel," AREA (NE)", "");
            DetailRowUtil.addRow(owntrxPagePanel," N", "N  36°  0.000'");
            DetailRowUtil.addRow(owntrxPagePanel," E", "E 139° 45.000'");
            DetailRowUtil.addRow(owntrxPagePanel," AREA (SW)", "");
            DetailRowUtil.addRow(owntrxPagePanel," S", "N  35° 20.000'");
            DetailRowUtil.addRow(owntrxPagePanel," W", "E 139° 15.000'");
            break;

        case 3:
            DetailRowUtil.addRow(owntrxPagePanel," SOURCE", "MANUAL INPUT");
            DetailRowUtil.addRow(owntrxPagePanel," BASE STN MMSI", "");
            DetailRowUtil.addRow(owntrxPagePanel," UTC", "");
            break;

        case 4:
            DetailRowUtil.addRow(owntrxPagePanel," ZONE SIZE", "5 NM");
            break;

    }

    owntrxNumberLabel.setText(page + " / 4");

    owntrxPagePanel.revalidate();
    owntrxPagePanel.repaint();
    }
}
