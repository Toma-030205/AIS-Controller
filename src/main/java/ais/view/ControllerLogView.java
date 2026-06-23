package ais.view;

import ais.model.DiagnosisLogEntry;
import java.awt.*;
import java.time.format.DateTimeFormatter;
import java.time.LocalDateTime;
import java.util.List;
import javax.swing.*;



public class ControllerLogView extends JPanel {

    /* =====================================================
     * UI
     * ===================================================== */
    private JLabel header;
    private JPanel body;

    /* =====================================================
     * 表示名（CONTROLLER / CONTROLLER LAN）
     * ===================================================== */
    private final String unitName;

    /* =====================================================
     * 日付フォーマット
     * ===================================================== */
    private static final DateTimeFormatter DATE_FORMAT =
            DateTimeFormatter.ofPattern("MM/dd HH:mm");

    public ControllerLogView(String unitName) {
        this.unitName = unitName;
        setLayout(new BorderLayout());
        initComponents();
    }

    /* =====================================================
     * 初期化
     * ===================================================== */
    private void initComponents() {

        header = new JLabel(unitName + " LOG");
        header.setFont(new Font("Meiryo UI", Font.BOLD, 32));
        header.setOpaque(true);
        header.setBackground(UiTheme.HEADER_BG);
        header.setBorder(BorderFactory.createEmptyBorder(8, 12, 8, 12));
        add(header, BorderLayout.NORTH);

        body = new JPanel();
        body.setLayout(new BoxLayout(body, BoxLayout.Y_AXIS));
        body.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));

        add(body, BorderLayout.CENTER);
    }

    /* =====================================================
     * Model → View（1ページ分）
     * ===================================================== */
    public void update(List<DiagnosisLogEntry> pageEntries) {

        body.removeAll();

        if (pageEntries == null || pageEntries.isEmpty()) {
            body.add(createEmptyLabel());
        } else {
            DiagnosisLogEntry e = pageEntries.get(0);
            body.add(createEntryBlock(e));
        }

        revalidate();
        repaint();
    }

    /* =====================================================
     * エントリ描画
     * ===================================================== */
    private JPanel createEntryBlock(DiagnosisLogEntry e) {

        JPanel p = new JPanel();
        p.setLayout(new BoxLayout(p, BoxLayout.Y_AXIS));
        p.setAlignmentX(Component.LEFT_ALIGNMENT);

        p.add(line("1. " + unitName + " : " + resultText(e.isOk())));
        p.add(line(" DATE : " + formatDate(e.getDateTime())));

        return p;
    }

    /* =====================================================
     * 表示ヘルパ
     * ===================================================== */
    private JLabel line(String text) {
        JLabel l = new JLabel(text);
        l.setFont(new Font("Meiryo UI", Font.PLAIN, 24));
        l.setForeground(Color.BLACK);
        l.setAlignmentX(Component.LEFT_ALIGNMENT);
        return l;
    }

    private JLabel createEmptyLabel() {
        JLabel l = new JLabel("NO LOG DATA");
        l.setFont(new Font("Meiryo UI", Font.PLAIN, 24));
        l.setForeground(Color.GRAY);
        return l;
    }

    private String resultText(boolean ok) {
        return ok ? "OK" : "NG";
    }

    private String formatDate(LocalDateTime dt) {
        if (dt == null) {
            return "--/-- --:--";
        }
        return dt.format(DATE_FORMAT);
    }
}
