package ais.view;

import ais.model.NavStatus;
import ais.model.OwnShipInfo;
import ais.util.DetailRowUtil;
import java.awt.*;
import javax.swing.*;



public class OwnShipDetail1View extends JPanel {

    private JPanel ownshipPagePanel1;
    private JLabel ownshipNumberLabel1;

    public OwnShipDetail1View() {
        setLayout(new BorderLayout());

        JLabel ownshipHeader1 = new JLabel("OWN SHIP'S DETAIL 1");
        ownshipHeader1.setFont(new Font("Meiryo UI", Font.BOLD, 32));
        ownshipHeader1.setOpaque(true);
        ownshipHeader1.setBackground(UiTheme.HEADER_BG);
        ownshipHeader1.setBorder(BorderFactory.createEmptyBorder(8, 12, 8, 12));

        ownshipPagePanel1 = new JPanel();
        ownshipPagePanel1.setLayout(new GridLayout(0, 1));  // 縦に並べるだけでOK

        ownshipNumberLabel1 = new JLabel("1 / 6");
        ownshipNumberLabel1.setFont(new Font("Meiryo UI", Font.BOLD, 32));
        ownshipNumberLabel1.setBackground(UiTheme.HEADER_BG);

        JPanel ownshipDetailCard1 = new JPanel(new BorderLayout());
        ownshipDetailCard1.add(ownshipHeader1, BorderLayout.NORTH);
        ownshipDetailCard1.add(ownshipPagePanel1, BorderLayout.CENTER);
        ownshipDetailCard1.add(ownshipNumberLabel1, BorderLayout.SOUTH);
        add(ownshipDetailCard1, BorderLayout.CENTER);
    }
    
    //　自船詳細表示１の中身
    public void updateownShipPage1(OwnShipInfo myship, int page) {
    ownshipPagePanel1.removeAll();  // ← 一度クリア

    switch(page) {
        case 1:
            DetailRowUtil.addRow(ownshipPagePanel1," MMSI", myship.mmsi);
            DetailRowUtil.addRow(ownshipPagePanel1," NAME", myship.vesselName);
            DetailRowUtil.addRow(ownshipPagePanel1," IMO NO.", myship.imo);
            DetailRowUtil.addRow(ownshipPagePanel1," CALL SIGN", myship.callSign);
            break;

        case 2:
            DetailRowUtil.addRow(ownshipPagePanel1," ANT POSN EXT INT", "");
            DetailRowUtil.addRow(ownshipPagePanel1," BOW", myship.dimBow);
            DetailRowUtil.addRow(ownshipPagePanel1," STR", myship.dimStern);
            DetailRowUtil.addRow(ownshipPagePanel1," POR", myship.dimPort);
            DetailRowUtil.addRow(ownshipPagePanel1," STA", myship.dimStarboard);
            DetailRowUtil.addRow(ownshipPagePanel1," LENG", myship.length);
            DetailRowUtil.addRow(ownshipPagePanel1," BEAM", myship.beam);
            break;

        case 3:
            DetailRowUtil.addRow(ownshipPagePanel1," POSN DEVICE", "GPS");
            DetailRowUtil.addRow(ownshipPagePanel1," NAV STATUS", myship.navStatus);
            break;

        case 4:
            DetailRowUtil.addRow(ownshipPagePanel1," DESTINATION", myship.destination);
            String etaStr = String.format(
                    "%02d/%02d , %02d:%02d",
                    myship.etaMonth,
                    myship.etaDay,
                    myship.etaHour,
                    myship.etaMinute
            );
            DetailRowUtil.addRow(ownshipPagePanel1," ETA(M/D,H:M)", etaStr);
            break;

        case 5:
            DetailRowUtil.addRow(ownshipPagePanel1," DRAUGHT", myship.draught);
            DetailRowUtil.addRow(ownshipPagePanel1," PERSONS", myship.persons);
            DetailRowUtil.addRow(ownshipPagePanel1," TYPE OF SHIP",myship.shipType);
            break;

        case 6:
            DetailRowUtil.addRow(ownshipPagePanel1," CARGO/STATUS", myship.CargoType);
            break;
    }

    ownshipNumberLabel1.setText(page + " / 6");

    ownshipPagePanel1.revalidate();
    ownshipPagePanel1.repaint();
}
}
