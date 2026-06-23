package ais.view;

import ais.model.CommunicationTestModel;
import java.awt.*;
import javax.swing.*;



public class CommunicationTestView extends JPanel {

    // ===== 選択項目 =====
    public JList<String> actionList;
    private DefaultListModel<String> actionListModel;

    // ===== 表示項目 =====
    private JLabel destinationValue;
    private JLabel mmsiValue;
    private JLabel resultValue;

    public CommunicationTestView() {
        setLayout(new BorderLayout());
        initComponents();
    }

    private void initComponents() {

        /* ===== ヘッダ ===== */
        JLabel header = new JLabel("COMMUNICATION TEST", SwingConstants.LEFT);
        header.setFont(new Font("Meiryo UI", Font.BOLD, 32));
        header.setOpaque(true);
        header.setBackground(UiTheme.HEADER_BG);
        header.setBorder(BorderFactory.createEmptyBorder(8, 12, 8, 12));
        add(header, BorderLayout.NORTH);

        /* ===== 中央レイアウト ===== */
        JPanel center = new JPanel();
        center.setLayout(new BoxLayout(center, BoxLayout.Y_AXIS));
        center.setBorder(BorderFactory.createEmptyBorder(16, 24, 16, 24));

        /* ===== TX / CANCEL ===== */
        actionListModel = new DefaultListModel<>();
        actionListModel.addElement("[TX]");
        actionListModel.addElement("[CANCEL]");

        actionList = new JList<>(actionListModel);
        actionList.setFont(new Font("Meiryo UI", Font.PLAIN, 28));
        actionList.setSelectionBackground(UiTheme.SELECTION_BG);
        actionList.setVisibleRowCount(2);
        actionList.setFixedCellHeight(40);

        center.add(actionList);
        center.add(Box.createVerticalStrut(24));

        /* ===== 情報表示 ===== */
        center.add(createInfoRow("DESTINATION :", destinationValue = new JLabel("")));
        center.add(Box.createVerticalStrut(12));
        center.add(createInfoRow("MMSI :", mmsiValue = new JLabel("")));
        center.add(Box.createVerticalStrut(12));
        center.add(createInfoRow("RESULT :", resultValue = new JLabel("")));

        add(center, BorderLayout.CENTER);
    }

    private JPanel createInfoRow(String title, JLabel value) {
        JPanel row = new JPanel(new BorderLayout());
        JLabel label = new JLabel(title);
        label.setFont(new Font("Meiryo UI", Font.PLAIN, 26));
        value.setFont(new Font("Meiryo UI", Font.PLAIN, 26));

        row.add(label, BorderLayout.WEST);
        row.add(value, BorderLayout.CENTER);
        return row;
    }

    /* ===== 外部 API ===== */

    public int getSelectedAction() {
        return actionList.getSelectedIndex();
    }

    public void setSelectedAction(int index) {
        actionList.setSelectedIndex(index);
    }

    public void setDestination(String dest) {
        destinationValue.setText(dest);
    }

    public void setMmsi(String mmsi) {
        mmsiValue.setText(mmsi);
    }

    public void setResult(String result) {
        resultValue.setText(result);
    }

    public void refresh(CommunicationTestModel model) {

        /* ===== ACTION 選択 ===== */
        switch (model.getAction()) {
            case TX:
                actionList.setSelectedIndex(0);
                break;
            case CANCEL:
                actionList.setSelectedIndex(1);
                break;
        }

        /* ===== 表示専用項目 ===== */
        destinationValue.setText(model.getDestinationMmsi());
        // MMSI は DESTINATION と同義なら空 or 同じ値で可
        mmsiValue.setText(model.getDestinationMmsi());

        resultValue.setText(model.getResultText());
    }


    public void requestFocus() {
        actionList.requestFocusInWindow();
    }
}
