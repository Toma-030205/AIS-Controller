package ais.view;

import ais.model.SelfDiagnosisModel;
import java.awt.*;
import javax.swing.*;




public class SelfDiagnosisView extends JPanel {

    /* =====================================================
     * 共通色定義
     * ===================================================== */
    private static final Color BG_CURSOR = UiTheme.SELECTION_BG;
    private static final Color FG_CURSOR = new Color(1, 1, 1);

    /* =====================================================
     * 1. TRANSPONDER
     * ===================================================== */
    private JLabel transponderRow;
    private JLabel tpItemValue;
    private JLabel tpAction;

    private JLabel tpResult;
    private JLabel tpCont;
    private JLabel tpGps;
    private JLabel tpTrx;
    private JLabel tpPs;
    private JLabel tpAntenna;

    /* =====================================================
     * 2. CONTROLLER
     * ===================================================== */
    private JLabel controllerRow;
    private JLabel controllerAction;
    private JLabel controllerResult;

    /* =====================================================
     * 3. CONTROLLER LAN
     * ===================================================== */
    private JLabel controllerLanRow;
    private JLabel controllerLanAction;
    private JLabel controllerLanResult;

    /* =====================================================
     * LOG
     * ===================================================== */
    private JLabel transponderLog;
    private JLabel controllerLog;
    private JLabel controllerLanLog;

    private final java.util.List<JLabel> cursorTargets = new java.util.ArrayList<>();
    private JScrollPane scrollPane;
    private JPanel body;

    public SelfDiagnosisView() {
        setLayout(new BorderLayout());
        initComponents();
    }

    /* =====================================================
     * 初期レイアウト
     * ===================================================== */
    private void initComponents() {

        JLabel header = new JLabel("SELF DIAGNOSIS");
        header.setFont(new Font("Meiryo UI", Font.BOLD, 32));
        header.setOpaque(true);
        header.setBackground(UiTheme.HEADER_BG);
        header.setBorder(BorderFactory.createEmptyBorder(8, 12, 8, 12));
        add(header, BorderLayout.NORTH);

        body = new JPanel();
        body.setLayout(new BoxLayout(body, BoxLayout.Y_AXIS));
        body.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));

        scrollPane = new JScrollPane(body);
        scrollPane.setBorder(null);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_NEVER);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

        add(scrollPane, BorderLayout.CENTER);

        /* ===== 1. TRANSPONDER ===== */
        transponderRow = title("1. TRANSPONDER:");
        body.add(transponderRow);

        tpItemValue = item(" TEST ALL ");
        body.add(tpItemValue);

        tpAction = item(" [ ENT ]");
        body.add(tpAction);

        tpResult = result(" RESULT    : OK");
        tpCont = result(" CONT      : OK");
        tpGps = result(" INT GPS   : OK");
        tpTrx = result(" TRX       : OK");
        tpPs = result(" PS        : OK");
        tpAntenna = result(" ANTENNA   : INTERNAL");
        body.add(tpResult);
        body.add(tpCont);
        body.add(tpGps);
        body.add(tpTrx);
        body.add(tpPs);
        body.add(tpAntenna);

        body.add(Box.createVerticalStrut(2));

        /* ===== 2. CONTROLLER ===== */
        controllerRow = title("2. CONTROLLER:");
        body.add(controllerRow);

        controllerAction = item(" [ ENT ] ");
        body.add(controllerAction);

        controllerResult = result(" RESULT    : OK");
        body.add(controllerResult);

        body.add(Box.createVerticalStrut(2));

        /* ===== 3. CONTROLLER LAN ===== */
        controllerLanRow = title("3. CONTROLLER LAN:");
        body.add(controllerLanRow);

        controllerLanAction = item(" [ ENT ] ");
        body.add(controllerLanAction);

        controllerLanResult = result(" RESULT    : OK");
        body.add(controllerLanResult);

        body.add(Box.createVerticalStrut(6));

        /* ===== LOG ===== */
        transponderLog = title("4. TRANSPONDER LOG");
        controllerLog = title("5. CONTROLLER LOG");
        controllerLanLog = title("6. CONTROLLER LAN LOG");

        body.add(transponderLog);
        body.add(controllerLog);
        body.add(controllerLanLog);
    }

    /* =====================================================
     * Model → View 反映
     * ===================================================== */
    public void update(SelfDiagnosisModel model) {

        clearCursor();

        if (model.getEditMode() == SelfDiagnosisModel.EditMode.ROW_SELECT) {
            highlightRow(model.getCurrentRow());
            adjustScroll(model.getCurrentRow());
        } else {
            highlightValue(model);
        }

        if (model.getCurrentRow() == SelfDiagnosisModel.Row.TRANSPONDER) {
            updateTransponderItemValue(model.getCurrentItem());
        }

        tpResult.setText(" RESULT    : " + model.getTransponderResult());
        tpCont.setText(" CONT      : " + model.getContResultText());
        tpGps.setText(" INT GPS   : " + model.getGpsResultText());
        tpTrx.setText(" TRX       : " + model.getTrxResultText());
        tpPs.setText(" PS        : " + model.getPsResultText());
        controllerResult.setText(" RESULT    : " + model.getControllerResult());
        controllerLanResult.setText(" RESULT    : " + model.getControllerLanResult());
    }

    /* =====================================================
     * TRANSPONDER 項目ブロック描画
     * ===================================================== */
    
    private void updateTransponderItemValue(SelfDiagnosisModel.TransponderItem item) {

        String text;
        switch (item) {
            case TEST_ALL:
                text = " TEST ALL ";
                break;
            case INT_GPS:
                text = " INT GPS ";
                break;
            case TRX:
                text = " TRX ";
                break;
            case PS:
                text = " PS ";
                break;
            default:
                text = "";
                break;
        }
        tpItemValue.setText(text);
    }


    private String formatItem(String text, boolean selected) {
        if (selected) {
            return "<span style='background-color:#0078D7;color:white;'>&nbsp;" + text + "</span>";
        }
        return "&nbsp;" + text;
    }

    /* =====================================================
     * カーソル制御
     * ===================================================== */
    private void highlightRow(SelfDiagnosisModel.Row row) {
        switch (row) {
            case TRANSPONDER:
                cursor(transponderRow);
                break;
            case CONTROLLER:
                cursor(controllerRow);
                break;
            case CONTROLLER_LAN:
                cursor(controllerLanRow);
                break;
            case TRANSPONDER_LOG:
                cursor(transponderLog);
                break;
            case CONTROLLER_LOG:
                cursor(controllerLog);
                break;
            case CONTROLLER_LAN_LOG:
                cursor(controllerLanLog);
                break;
        }
    }

    private void highlightValue(SelfDiagnosisModel model) {
        switch (model.getCurrentRow()) {
            case TRANSPONDER:
                if (model.getEditMode() == SelfDiagnosisModel.EditMode.TRANSPONDER_ITEM_SELECT) {
                    cursor(tpItemValue);
                }
                if (model.getEditMode() == SelfDiagnosisModel.EditMode.ACTION_SELECT) {
                    updateActionLabel(tpAction, model.getCurrentAction());
                    cursor(tpAction);
                }
                break;

            case CONTROLLER:
                updateActionLabel(controllerAction, model.getCurrentAction());
                cursor(controllerAction);
                break;

            case CONTROLLER_LAN:
                updateActionLabel(controllerLanAction, model.getCurrentAction());
                cursor(controllerLanAction);
                break;
        }
    }

    private void updateActionLabel(JLabel label, SelfDiagnosisModel.Action action) {
        label.setText(action == SelfDiagnosisModel.Action.ENT
                ? " [ ENT ] "
                : " [ CANCEL ] ");
    }

    private void adjustScroll(SelfDiagnosisModel.Row row) {

        JLabel target;

        switch (row) {
            case TRANSPONDER:
                target = transponderRow;
                break;
            case CONTROLLER:
                target = controllerRow;
                break;
            default:
                target = controllerLanRow; // LOG 含め常に 3 を基準
        }

        Rectangle r = SwingUtilities.convertRectangle(
                target.getParent(),
                target.getBounds(),
                body
        );
        scrollPane.getViewport().setViewPosition(new Point(0, r.y));
    }

    /* =====================================================
     * 見た目ヘルパ
     * ===================================================== */
    private JLabel title(String text) {
        JLabel l = new JLabel(text);
        l.setFont(new Font("Meiryo UI", Font.BOLD, 30));
        style(l);
        cursorTargets.add(l);
        return l;
    }

    private JLabel item(String text) {
        JLabel l = new JLabel(text);
        l.setFont(new Font("Meiryo UI", Font.PLAIN, 24));
        style(l);
        cursorTargets.add(l);
        return l;
    }

    private JLabel itemBlock(String text) {
        JLabel l = new JLabel(text);
        l.setFont(new Font("Meiryo UI", Font.PLAIN, 24));
        l.setOpaque(false);
        l.setForeground(Color.BLACK);
        return l;
    }

    private JLabel result(String text) {
        JLabel l = new JLabel(text);
        l.setFont(new Font("Meiryo UI", Font.PLAIN, 24));
        style(l);
        return l;
    }

    private void style(JLabel l) {
        l.setOpaque(false);
        l.setForeground(Color.BLACK);
    }

    private void cursor(JLabel l) {
        l.setOpaque(true);
        l.setBackground(BG_CURSOR);
        l.setForeground(FG_CURSOR);
    }

    private void clearCursor() {
        for (JLabel l : cursorTargets) {
            l.setOpaque(false);
            l.setForeground(Color.BLACK);
        }
    }
}
