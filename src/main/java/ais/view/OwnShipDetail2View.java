package ais.view;

import ais.model.OwnShipInfo;
import ais.util.DetailRowUtil;
import java.awt.*;
import javax.swing.*;



public class OwnShipDetail2View extends JPanel {

    private JPanel ownshipPagePanel2;
    private JLabel ownshipNumberLabel2;

    public OwnShipDetail2View() {
        setLayout(new BorderLayout());

        JLabel ownshipHeader2 = new JLabel("OWN SHIP'S DETAIL 2");
        ownshipHeader2.setFont(new Font("Meiryo UI", Font.BOLD, 32));
        ownshipHeader2.setOpaque(true);
        ownshipHeader2.setBackground(UiTheme.HEADER_BG);
        ownshipHeader2.setBorder(BorderFactory.createEmptyBorder(8, 12, 8, 12));

        ownshipPagePanel2 = new JPanel();
        ownshipPagePanel2.setLayout(new GridLayout(0, 1));  // 縦に並べるだけでOK

        ownshipNumberLabel2 = new JLabel("1 / 4");
        ownshipNumberLabel2.setFont(new Font("Meiryo UI", Font.BOLD, 32));
        ownshipNumberLabel2.setBackground(UiTheme.HEADER_BG);

        JPanel ownshipDetailCard2 = new JPanel(new BorderLayout());
        ownshipDetailCard2.add(ownshipHeader2, BorderLayout.NORTH);
        ownshipDetailCard2.add(ownshipPagePanel2, BorderLayout.CENTER);
        ownshipDetailCard2.add(ownshipNumberLabel2, BorderLayout.SOUTH);
        add(ownshipDetailCard2, BorderLayout.CENTER);
    }
    
    //　自船詳細表示２の中身
    public void updateownShipPage2(OwnShipInfo myship, int page) {
    ownshipPagePanel2.removeAll();  // ← 一度クリア

    switch(page) {
        case 1:
            DetailRowUtil.addRow(ownshipPagePanel2," POSN DEVICE", "GPS");
            DetailRowUtil.addRow(ownshipPagePanel2," LAT", myship.lat + "");
            DetailRowUtil.addRow(ownshipPagePanel2," LON", myship.lon + "");
            DetailRowUtil.addRow(ownshipPagePanel2," SOG", myship.sog + "kn");
            DetailRowUtil.addRow(ownshipPagePanel2," COG", myship.cog + "°");
            break;

        case 2:
            DetailRowUtil.addRow(ownshipPagePanel2," HDG", myship.heading + "°");
            DetailRowUtil.addRow(ownshipPagePanel2," ROT", myship.rot + "°/min");
            DetailRowUtil.addRow(ownshipPagePanel2," POSN QUALITY", myship.posnQuality);
            DetailRowUtil.addRow(ownshipPagePanel2," PA", "LOW");
            DetailRowUtil.addRow(ownshipPagePanel2," RAIM", "NO USE");
            DetailRowUtil.addRow(ownshipPagePanel2," TIMESTAMP", "27");
            break;

        case 3:
            DetailRowUtil.addRow(ownshipPagePanel2," ACC FROM RAIM", "NO RAIM PROCESS AVAILABLE");
            break;

        case 4:
            DetailRowUtil.addRow(ownshipPagePanel2," SYNC STATE", "UTC DIRECT");
            DetailRowUtil.addRow(ownshipPagePanel2," RCV STATIONS", "10");
            break;

    }

    ownshipNumberLabel2.setText(page + " / 4");

    ownshipPagePanel2.revalidate();
    ownshipPagePanel2.repaint();
    }
}
