package ais.view;

import ais.model.OwnShipInfo;
import ais.model.ShipManager;
import java.awt.*;
import java.time.*;
import java.time.format.DateTimeFormatter;
import javax.swing.*;




public class PosnTimeView extends JPanel {

    private JLabel lblLat;
    private JLabel lblLon;
    private JLabel lblTime;
    private JLabel lblDate;

    public PosnTimeView() {
        setLayout(new BorderLayout());

        /* ===== ヘッダ ===== */
        JLabel header = new JLabel("POSN & TIME");
        header.setFont(new Font("Meiryo UI", Font.BOLD, 32));
        header.setOpaque(true);
        header.setBackground(UiTheme.HEADER_BG);
        header.setBorder(BorderFactory.createEmptyBorder(8, 12, 8, 12));
        add(header, BorderLayout.NORTH);

        /* ===== 本体 ===== */
        JPanel body = new JPanel();
        body.setLayout(new BoxLayout(body, BoxLayout.Y_AXIS));
        body.setBorder(BorderFactory.createEmptyBorder(16, 16, 16, 16));

        lblLat = createLabel("");
        lblLon = createLabel("");
        JLabel sep = createLabel("-----------------------");
        lblTime = createLabel("");
        lblDate = createLabel("");

        body.add(lblLat);
        body.add(lblLon);
        body.add(Box.createVerticalStrut(8));
        body.add(sep);
        body.add(Box.createVerticalStrut(8));
        body.add(lblTime);
        body.add(lblDate);

        add(body, BorderLayout.CENTER);

        /* ===== 初期表示 ===== */
        refresh();
    }

    private JLabel createLabel(String text) {
        JLabel l = new JLabel(text);
        l.setFont(new Font("Meiryo UI", Font.BOLD, 32));
        return l;
    }

    /* ===== 表示更新 ===== */
    public void refresh() {
        ShipManager sm = ShipManager.getInstance();
        OwnShipInfo own = sm.getOwnShip();

        // 位置（固定値をそのまま）
        lblLat.setText(formatLat(own.lat));
        lblLon.setText(formatLon(own.lon));

        // UTC 時刻
        ZonedDateTime now = ZonedDateTime.now(ZoneOffset.UTC);
        lblTime.setText("TIME : "
                + now.format(DateTimeFormatter.ofPattern("HH:mm")) + "  UTC");
        lblDate.setText("DATE : "
                + now.format(DateTimeFormatter.ofPattern("dd/MMM/yyyy")).toUpperCase());
    }

    /* ===== フォーマット ===== */
    private String formatLat(double lat) {
        char hemi = (lat >= 0) ? 'N' : 'S';
        lat = Math.abs(lat);

        int deg = (int) lat;
        double min = (lat - deg) * 60.0;

        return String.format("%c %02d° %07.4f'", hemi, deg, min);
    }

    private String formatLon(double lon) {
        char hemi = (lon >= 0) ? 'E' : 'W';
        lon = Math.abs(lon);

        int deg = (int) lon;
        double min = (lon - deg) * 60.0;

        return String.format("%c %03d° %07.4f'", hemi, deg, min);
    }
}
