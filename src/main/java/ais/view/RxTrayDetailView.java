package ais.view;

import ais.model.RxMessage;
import java.awt.*;
import java.time.format.DateTimeFormatter;
import javax.swing.*;



public class RxTrayDetailView extends JPanel {

    private JLabel headerLabel;
    private JTextArea detailArea;

    private static final DateTimeFormatter UTC_FMT =
            DateTimeFormatter.ofPattern("yy/MM/dd  HH:mm");

    public RxTrayDetailView() {
        setLayout(new BorderLayout());
        initComponents();
    }

    private void initComponents() {

        /* ===== ヘッダ ===== */
        headerLabel = new JLabel("", SwingConstants.LEFT);
        headerLabel.setFont(new Font("Meiryo UI", Font.BOLD, 32));
        headerLabel.setOpaque(true);
        headerLabel.setBackground(UiTheme.HEADER_BG);
        headerLabel.setBorder(
                BorderFactory.createEmptyBorder(8, 12, 8, 12)
        );
        add(headerLabel, BorderLayout.NORTH);

        /* ===== 詳細表示 ===== */
        detailArea = new JTextArea();
        detailArea.setFont(new Font("Meiryo UI", Font.PLAIN, 30));
        detailArea.setEditable(false);
        detailArea.setFocusable(false);
        detailArea.setBorder(
                BorderFactory.createEmptyBorder(3, 16, 10, 16)
        );

        add(detailArea, BorderLayout.CENTER);
    }

    /* =====================================================
     * Model 連携
     * ===================================================== */

    public void setMessage(RxMessage msg) {
        if (msg == null) {
            headerLabel.setText("");
            detailArea.setText("");
            return;
        }

        /* ヘッダ：一覧表示と同一 */
        headerLabel.setText(msg.getListLabel());

        StringBuilder sb = new StringBuilder();

        // 1. UTC 日時
        sb.append(msg.getUtcDateTime().format(UTC_FMT)).append("\n");

        // 2. FORMAT（MMSI or BROADCAST）
        sb.append(msg.getDestinationLabel()).append("\n");

        // 3. メッセージ種別
        sb.append("CATEGORY : ").append(msg.getCategory()).append("\n");
        sb.append("FUNCTION : ").append(msg.getFunction()).append("\n");
        sb.append("REPLY    : ").append(msg.getReply()).append("\n");
        sb.append("CH       : ").append(msg.getChannel()).append("\n");

        detailArea.setText(sb.toString());
        detailArea.setCaretPosition(0);
    }
}
