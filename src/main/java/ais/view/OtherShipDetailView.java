package ais.view;

import ais.model.OwnShipInfo;
import ais.model.ShipInfo;
import ais.util.DetailRowUtil;
import ais.util.NavigationUtil;
import java.awt.*;
import javax.swing.*;



public class OtherShipDetailView extends JPanel {

    private JPanel othershipPagePanel;
    private JLabel pageNumberLabel;

    public OtherShipDetailView() {
        setLayout(new BorderLayout());
        initComponents();
    }

    private void initComponents() {

        othershipPagePanel = new JPanel();
        othershipPagePanel.setLayout(new GridLayout(0, 1));  // 縦に並べるだけでOK

        JLabel othershipHeader = new JLabel("OTHER SHIP'S DETAIL");
        othershipHeader.setFont(new Font("Meiryo UI", Font.BOLD, 32));
        othershipHeader.setOpaque(true);
        othershipHeader.setBackground(UiTheme.HEADER_BG);
        othershipHeader.setBorder(BorderFactory.createEmptyBorder(8, 12, 8, 12));

        pageNumberLabel = new JLabel("1 / 9");
        pageNumberLabel.setFont(new Font("Meiryo UI", Font.BOLD, 32));
        pageNumberLabel.setBackground(UiTheme.HEADER_BG);

        JPanel shipDetailCard = new JPanel(new BorderLayout());
        shipDetailCard.add(othershipHeader, BorderLayout.NORTH);
        shipDetailCard.add(othershipPagePanel, BorderLayout.CENTER);
        shipDetailCard.add(pageNumberLabel, BorderLayout.SOUTH);
        
        add(shipDetailCard, BorderLayout.CENTER);
    }

    public void showPage(OwnShipInfo own, ShipInfo ship, int page) {
        othershipPagePanel.removeAll();  // ← 一度クリア

    switch(page) {
        case 1:
            DetailRowUtil.addRow(othershipPagePanel," MMSI", ship.getMmsi());           // 船を識別する9桁のID
            DetailRowUtil.addRow(othershipPagePanel," NAME", ship.getVesselName());     // 船名
            DetailRowUtil.addRow(othershipPagePanel," IMO NO.", ship.getImo());         // IMO（国際海事機関）番号。船に固有で、基本的に一生変わらない識別番号。
            DetailRowUtil.addRow(othershipPagePanel," CALL SIGN", ship.getCallSign());  // 無線呼出符号（船舶局免許で決まる）
            break;

        case 2:
            DetailRowUtil.addRow(othershipPagePanel," POSN DEVICE", ship.getEpfdDisplay());        // Position Device:位置計測に使用しているデバイス名
            DetailRowUtil.addRow(othershipPagePanel," LAT", ship.getLat());             // 緯度
            DetailRowUtil.addRow(othershipPagePanel," LON", ship.getLon());             // 経度
            DetailRowUtil.addRow(othershipPagePanel," SOG", ship.getSog());             // 対地速力(ノット)
            DetailRowUtil.addRow(othershipPagePanel," COG", ship.getCog());             // 対地針路（度）
            break;

        case 3:
            DetailRowUtil.addRow(othershipPagePanel," HDG", ship.getTrueHeading());     // 船首が向いている向き（真方位）
            DetailRowUtil.addRow(othershipPagePanel," ROT", ship.getRot());             // 旋回率（度/分）
            DetailRowUtil.addRow(othershipPagePanel," POSN QUALITY", ship.getPosnQualityDisplay());          // 位置情報の信頼度
            DetailRowUtil.addRow(othershipPagePanel," RAIM", ship.getRaimFlagDisplay());                  // GNSS 受信機が自律的に衛星の異常を検出する仕組み
            DetailRowUtil.addRow(othershipPagePanel," TIMESTAMP", ship.getTimestamp()); // AIS 動的メッセージが送られたタイムスタンプ（0〜59秒）
            break;

        case 4:
            DetailRowUtil.addRow(othershipPagePanel," SYNC STATE", ship.getSyncStateDisplay());// AIS の送信タイミング（TDMAスロット）の同期状態
            DetailRowUtil.addRow(othershipPagePanel," RCV STATIONS", "??");          // 陸上局が受信した数や基地局情報
            break;

        case 5:
            DetailRowUtil.addRow(othershipPagePanel," NAV STATUS", ship.getNavStatusDisplay());    // AIS が送る航行状態
            DetailRowUtil.addRow(othershipPagePanel," DESTINATION", ship.getDestination()); // 航海目的地
            break;

        case 6:
            DetailRowUtil.addRow(othershipPagePanel," ETA(M/D,H:M)", ship.getEtaDisplay());    // 到着予定時刻
            DetailRowUtil.addRow(othershipPagePanel," DRAUGHT", ship.getDraught());                 // 喫水（船底から水面までの高さ）
            DetailRowUtil.addRow(othershipPagePanel," LENGTH", ship.getLength());                   // 船体長
            break;
        
        case 7:
            DetailRowUtil.addRow(othershipPagePanel," BEAM", ship.getBeam());               // 船幅
            DetailRowUtil.addRow(othershipPagePanel," TYPE OF SHIP", ship.getShipTypeDisplay());   // 船舶の種類
            break;
        case 8:
            DetailRowUtil.addRow(othershipPagePanel," CARGO/STATUS", ship.getCargo());              // 積み荷のタイプ
            DetailRowUtil.addRow(othershipPagePanel," CLASS", ship.getAisClassText());                     // クラス
            DetailRowUtil.addRow(othershipPagePanel," MF ID", "??");                     // 製造元のコード
            DetailRowUtil.addRow(othershipPagePanel," MODEL CODE", "??");                // AIS トランシーバの機器モデル番号
            break;

        case 9:
            // --- BRG / RNG 計算（GraphicView と同一） ---
            double brg = NavigationUtil.calcBearingDeg(
                    own.lat, own.lon,
                    ship.getLat(), ship.getLon()
            );

            double rng = NavigationUtil.calcRangeNm(
                    own.lat, own.lon,
                    ship.getLat(), ship.getLon()
            );
            DetailRowUtil.addRow(othershipPagePanel," NO", "??");         // 他船で使用されているAISのシリアル番号
            DetailRowUtil.addRow(othershipPagePanel," CPA", "");        // 自船と他船が今の航跡を続けた場合に最も近づく距離（海里）
            DetailRowUtil.addRow(othershipPagePanel," TCPA", "");       // CPA が発生するまでの時間（分）
            DetailRowUtil.addRow(othershipPagePanel," BEARING",String.format("%05.1f°", brg));    // 自船から見た他船の方向（0〜359°）
            DetailRowUtil.addRow(othershipPagePanel," RANGE", String.format("%6.2f NM", rng));      // 自船から対象船までの距離（海里）
            break;

    }

    pageNumberLabel.setText(page + " / 9");

    othershipPagePanel.revalidate();
    othershipPagePanel.repaint();
}

    // ===== Controller 用 API =====

    public void setPage(int current, int total) {
        pageNumberLabel.setText(current + " / " + total);
    }

    public void showPage(JPanel page) {
        othershipPagePanel.removeAll();
        othershipPagePanel.add(page);
        othershipPagePanel.revalidate();
        othershipPagePanel.repaint();
    }

    public void requestFocus() {
        requestFocusInWindow();
    }
}
