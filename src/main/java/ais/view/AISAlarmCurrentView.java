package ais.view;

import ais.model.AISAlarmModel;
import java.awt.*;
import java.util.List;
import javax.swing.*;




public class AISAlarmCurrentView extends JPanel {

    private JLabel header;
    private JPanel listPanel;
    private JLabel pageUpMark;
    private JLabel pageDownMark;

    public AISAlarmCurrentView() {
        setLayout(new BorderLayout());
        initComponents();
    }

    /* =====================================================
     * 初期化
     * ===================================================== */
    private void initComponents() {

        /* ===== ヘッダ ===== */
        header = new JLabel("", SwingConstants.LEFT);
        header.setFont(new Font("Meiryo UI", Font.BOLD, 32));
        header.setOpaque(true);
        header.setBackground(UiTheme.HEADER_BG);
        header.setBorder(BorderFactory.createEmptyBorder(8, 12, 8, 12));
        add(header, BorderLayout.NORTH);

        /* ===== 一覧エリア ===== */
        listPanel = new JPanel();
        listPanel.setLayout(new BoxLayout(listPanel, BoxLayout.Y_AXIS));
        listPanel.setBorder(BorderFactory.createEmptyBorder(16, 24, 16, 24));

        JScrollPane scrollPane = new JScrollPane(listPanel);
        scrollPane.setBorder(null);
        add(scrollPane, BorderLayout.CENTER);

        /* ===== ページマーク ===== */
        JPanel markPanel = new JPanel(new BorderLayout());

        pageUpMark = new JLabel("▲", SwingConstants.CENTER);
        pageDownMark = new JLabel("▼", SwingConstants.CENTER);

        pageUpMark.setFont(new Font("Meiryo UI", Font.PLAIN, 24));
        pageDownMark.setFont(new Font("Meiryo UI", Font.PLAIN, 24));

        pageUpMark.setVisible(false);
        pageDownMark.setVisible(false);

        markPanel.add(pageUpMark, BorderLayout.NORTH);
        markPanel.add(pageDownMark, BorderLayout.SOUTH);

        add(markPanel, BorderLayout.EAST);
    }

    /* =====================================================
     * ヘッダ制御（Controller から呼ぶ）
     * ===================================================== */
    public void setHeaderText(String text) {
        header.setText(text);
    }

    /* =====================================================
     * NO DATA 表示
     * ===================================================== */
    public void showNoData() {

        listPanel.removeAll();

        JLabel noData = new JLabel("NO DATA");
        noData.setFont(new Font("Meiryo UI", Font.PLAIN, 28));
        noData.setAlignmentX(Component.LEFT_ALIGNMENT);

        listPanel.add(noData);

        pageUpMark.setVisible(false);
        pageDownMark.setVisible(false);

        revalidate();
        repaint();
    }

    /* =====================================================
     * アラーム一覧表示
     * ===================================================== */
    public void showAlarms(List<String[]> alarmLines,
            boolean hasPrev,
            boolean hasNext,
            int startIndex) {

        listPanel.removeAll();

        int no = startIndex + 1;

        for (String[] alarm : alarmLines) {

            // 1行目：番号 + 時刻
            JLabel time = new JLabel(no + ". " + alarm[0]);
            time.setFont(new Font("Meiryo UI", Font.PLAIN, 22));
            time.setAlignmentX(Component.LEFT_ALIGNMENT);

            // 2行目：内容
            JLabel content = new JLabel("    " + alarm[1]);
            content.setFont(new Font("Meiryo UI", Font.PLAIN, 22));
            content.setAlignmentX(Component.LEFT_ALIGNMENT);

            listPanel.add(time);
            listPanel.add(content);
            listPanel.add(Box.createVerticalStrut(10));

            no++;
        }

        pageUpMark.setVisible(hasPrev);
        pageDownMark.setVisible(hasNext);

        revalidate();
        repaint();
    }

    /* =====================================================
     * Model 反映
     * ===================================================== */
    public void refresh(AISAlarmModel model) {

        if (model.isNoData()) {
            showNoData();
            return;
        }

        showAlarms(
                model.getCurrentPageLines(),
                model.hasPrev(),
                model.hasNext(),
                model.getPageStartIndex()
        );
    }
}
