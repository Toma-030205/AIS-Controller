package ais.view;

import ais.model.ShipTypeCargoInputModel;
import java.awt.*;
import javax.swing.*;




public class ShipTypeCargoInputView extends JPanel {

    private final ShipTypeCargoInputModel model;

    private JLabel headerLabel;
    private JLabel shipTypeLabel;
    private JLabel cargoTypeLabel;

    private static final Color CURSOR_BG = UiTheme.INPUT_CURSOR_BG;
    private static final Color NORMAL_BG = UiTheme.PANEL_BG;

    public ShipTypeCargoInputView(ShipTypeCargoInputModel model) {
        this.model = model;
        setLayout(new BorderLayout());
        initComponents();
        refresh();
    }

    private void initComponents() {

        headerLabel = new JLabel("TYPE OF SHIP AND CARGO");
        headerLabel.setFont(new Font("Meiryo UI", Font.BOLD, 32));
        headerLabel.setOpaque(true);
        headerLabel.setBackground(UiTheme.HEADER_BG);
        headerLabel.setBorder(BorderFactory.createEmptyBorder(8, 12, 8, 12));
        add(headerLabel, BorderLayout.NORTH);

        JPanel center = new JPanel(new GridLayout(2, 1, 4, 12));
        center.setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30));

        shipTypeLabel = createValueLabel();
        cargoTypeLabel = createValueLabel();

        center.add(shipTypeLabel);
        center.add(cargoTypeLabel);

        add(center, BorderLayout.CENTER);
    }

    private JLabel createValueLabel() {
        JLabel lbl = new JLabel();
        lbl.setFont(new Font("Consolas", Font.BOLD, 28));
        lbl.setOpaque(true);
        lbl.setBackground(NORMAL_BG);
        lbl.setBorder(BorderFactory.createEmptyBorder(8, 12, 8, 12));
        return lbl;
    }

    /* =====================
     * 表示更新
     * ===================== */
    public void refresh() {

        shipTypeLabel.setText(
                "     TYPE OF SHIP : " + model.getShipTypeDisplayText()
        );

        cargoTypeLabel.setText(
                "     CARGO TYPE   : " + model.getCargoTypeDisplayText()
        );

        // フェーズによるカーソル強調
        if (model.getPhase() == ShipTypeCargoInputModel.Phase.SHIP_TYPE) {
            shipTypeLabel.setBackground(CURSOR_BG);
            cargoTypeLabel.setBackground(NORMAL_BG);
        } else {
            shipTypeLabel.setBackground(NORMAL_BG);
            cargoTypeLabel.setBackground(CURSOR_BG);
        }
    }

    /* =====================
     * Controller 用 API
     * ===================== */
    public void increment() {
        if (model.getPhase() == ShipTypeCargoInputModel.Phase.SHIP_TYPE) {
            cycleShipType(+1);
        } else {
            cycleCargo(+1);
        }
        refresh();
    }

    public void decrement() {
        if (model.getPhase() == ShipTypeCargoInputModel.Phase.SHIP_TYPE) {
            cycleShipType(-1);
        } else {
            cycleCargo(-1);
        }
        refresh();
    }

    /* =====================
     * 内部ロジック
     * ===================== */
    private void cycleShipType(int dir) {

        int[] types = model.getSelectableShipTypes();
        int cur = model.getShipTypeCode();

        int idx = 0;
        for (int i = 0; i < types.length; i++) {
            if (types[i] == cur) {
                idx = i;
                break;
            }
        }

        idx = (idx + dir + types.length) % types.length;
        model.setShipTypeCode(types[idx]);
    }

    private void cycleCargo(int dir) {

        if (!model.isCargoSelectable()) {
            return;
        }

        int idx = model.getCargoIndex();
        idx = (idx + dir + 6) % 6; // 0～5
        model.setCargoIndex(idx);
    }

    @Override
    public boolean isFocusable() {
        return true;
    }
}
