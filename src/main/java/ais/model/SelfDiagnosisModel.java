package ais.model;

import java.util.EnumSet;



public class SelfDiagnosisModel {

    /* =====================================================
     * 行（左カーソル）
     * ===================================================== */
    public enum Row {
        TRANSPONDER,
        CONTROLLER,
        CONTROLLER_LAN,
        TRANSPONDER_LOG,
        CONTROLLER_LOG,
        CONTROLLER_LAN_LOG
    }

    /* =====================================================
     * 編集モード（EditAndTx と同型）
     * ===================================================== */
    public enum EditMode {
        ROW_SELECT,
        TRANSPONDER_ITEM_SELECT,
        ACTION_SELECT
    }


    /* =====================================================
     * TRANSPONDER 試験項目
     * ===================================================== */
    public enum TransponderItem {
        TEST_ALL,
        INT_GPS,
        TRX,
        PS
    }

    public enum ContNgReason {
        CPU_FROM,
        SRAM,
        FROM,
        TX_DAC,
        RX1_PORT,
        RX2_PORT,
        RX3_PORT
    }

    public enum GpsNgReason {
        PPS_CONT,
        SAT_RCV
    }

    public enum TrxNgReason {
        RX1_UNLK,
        RX2_UNLK,
        RX3_UNLK,
        TX_UNLK,
        RX1_LOOP,
        RX2_LOOP,
        RX1_RSSI,
        RX2_RSSI,
        RX3_RSSI,
        PA
    }

    public enum PsNgReason {
        PS_LOW
    }

    public enum ControllerNgReason {
        SRAM,
        FROM
    }

    public enum ControllerLanNgReason {
        CPU_FROM,
        CPU_DRAM,
        CPU_RAM,
        LAN
    }

    /* =====================================================
     * ENT / CANCEL
     * ===================================================== */
    public enum Action {
        ENT,
        CANCEL
    }    
    
    /* =====================================================
     * 状態
     * ===================================================== */
    private Row currentRow = Row.TRANSPONDER;
    private EditMode editMode = EditMode.ROW_SELECT;

    private TransponderItem currentItem = TransponderItem.TEST_ALL;
    private Action currentAction = Action.ENT;
    
    /* ===== 診断結果（仮） ===== */
    private DiagnoseResult contResult = DiagnoseResult.OK;
    private DiagnoseResult gpsResult = DiagnoseResult.OK;
    private DiagnoseResult trxResult = DiagnoseResult.OK;
    private DiagnoseResult psResult = DiagnoseResult.OK;
    private String transponderResult = "";
    private String controllerResult = "";
    private String controllerLanResult = "";
    private EnumSet<ContNgReason> contNgReasons = EnumSet.noneOf(ContNgReason.class);
    private EnumSet<GpsNgReason> gpsNgReasons = EnumSet.noneOf(GpsNgReason.class);
    private EnumSet<TrxNgReason> trxNgReasons = EnumSet.noneOf(TrxNgReason.class);
    private EnumSet<PsNgReason> psNgReasons = EnumSet.noneOf(PsNgReason.class);


    /* ===== LOG 遷移要求 ===== */
    private Row requestedLogRow = null;

    /* =====================================================
     * 上下キー
     * ===================================================== */
    public void moveUp() {
        if (editMode == EditMode.ROW_SELECT) {
            moveRowUp();
        } else {
            moveValueUp();
        }
    }

    public void moveDown() {
        if (editMode == EditMode.ROW_SELECT) {
            moveRowDown();
        } else {
            moveValueDown();
        }
    }

    /* =====================================================
     * ENTER
     * ===================================================== */
    public void enter() {

        switch (editMode) {

            case ROW_SELECT:

                // ★ LOG 行は即時遷移
                if (isLogRow(currentRow)) {
                    requestedLogRow = currentRow;
                    return;
                }

                // TRANSPONDER だけ特殊
                if (currentRow == Row.TRANSPONDER) {
                    editMode = EditMode.TRANSPONDER_ITEM_SELECT;
                } else {
                    editMode = EditMode.ACTION_SELECT;
                }
                return;

            case TRANSPONDER_ITEM_SELECT:
                editMode = EditMode.ACTION_SELECT;
                return;

            case ACTION_SELECT:
                if (currentAction == Action.ENT) {
                    executeByRow();
                }
                editMode = EditMode.ROW_SELECT;
                return;
        }
    }
    
    private boolean isLogRow(Row r) {
        return r == Row.TRANSPONDER_LOG
                || r == Row.CONTROLLER_LOG
                || r == Row.CONTROLLER_LAN_LOG;
    }


    /* =====================================================
     * CLR
     *
     * @return true = 画面を抜ける
     * ===================================================== */
    public boolean onClr() {

        if (editMode != EditMode.ROW_SELECT) {
            editMode = EditMode.ROW_SELECT;
            return false;
        }

        reset();
        return true;
    }


    /* =====================================================
     * ROW 移動
     * ===================================================== */
    private void moveRowUp() {
        Row[] rows = Row.values();
        int idx = currentRow.ordinal();
        idx = (idx - 1 + rows.length) % rows.length;
        currentRow = rows[idx];
    }

    private void moveRowDown() {
        Row[] rows = Row.values();
        int idx = currentRow.ordinal();
        idx = (idx + 1) % rows.length;
        currentRow = rows[idx];
    }

    /* =====================================================
     * VALUE 移動
     * ===================================================== */
    private void moveValueUp() {
        switch (editMode) {
            case TRANSPONDER_ITEM_SELECT:
                moveItemUp();
                break;

            case ACTION_SELECT:
                toggleAction();
                break;
        }
    }

    private void moveValueDown() {
        switch (editMode) {
            case TRANSPONDER_ITEM_SELECT:
                moveItemDown();
                break;

            case ACTION_SELECT:
                toggleAction();
                break;
        }
    }



    private void moveItemUp() {
        TransponderItem[] v = TransponderItem.values();
        int idx = currentItem.ordinal();
        idx = (idx - 1 + v.length) % v.length;
        currentItem = v[idx];
    }

    private void moveItemDown() {
        TransponderItem[] v = TransponderItem.values();
        int idx = currentItem.ordinal();
        idx = (idx + 1) % v.length;
        currentItem = v[idx];
    }

    private void toggleAction() {
        currentAction = (currentAction == Action.ENT)
                ? Action.CANCEL
                : Action.ENT;
    }

    /* =====================================================
     * 実行処理
     * ===================================================== */
    private void executeByRow() {

        switch (currentRow) {

            case TRANSPONDER:
                executeTransponder();
                break;

            case CONTROLLER:
                controllerResult = "OK";
                break;

            case CONTROLLER_LAN:
                controllerLanResult = "OK";
                break;

            case TRANSPONDER_LOG:
            case CONTROLLER_LOG:
            case CONTROLLER_LAN_LOG:
                requestedLogRow = currentRow;
                break;
        }
    }

    private void executeTransponder() {

        clearAllNgReasons();

        switch (currentItem) {

            case TEST_ALL:
                contNgReasons.add(ContNgReason.CPU_FROM);
                gpsResult = DiagnoseResult.OK;
                trxResult = DiagnoseResult.OK;
                psResult = DiagnoseResult.OK;
                transponderResult = "OK";
                updateResults();
                break;

            case INT_GPS:
                gpsResult = DiagnoseResult.OK;
                transponderResult = "OK";
                updateResults();
                break;

            case TRX:
                trxResult = DiagnoseResult.OK;
                transponderResult = "OK";
                updateResults();
                break;

            case PS:
                psResult = DiagnoseResult.OK;
                transponderResult = "OK";
                updateResults();
                break;
        }
    }

    private void updateResults() {
        contResult = contNgReasons.isEmpty() ? DiagnoseResult.OK : DiagnoseResult.NG;
        gpsResult = gpsNgReasons.isEmpty() ? DiagnoseResult.OK : DiagnoseResult.NG;
        trxResult = trxNgReasons.isEmpty() ? DiagnoseResult.OK : DiagnoseResult.NG;
        psResult = psNgReasons.isEmpty() ? DiagnoseResult.OK : DiagnoseResult.NG;

        transponderResult
                = (contResult == DiagnoseResult.OK
                && gpsResult == DiagnoseResult.OK
                && trxResult == DiagnoseResult.OK
                && psResult == DiagnoseResult.OK)
                        ? "OK"
                        : "NG";
    }

    public String getContResultText() {
        if (contNgReasons.isEmpty()) {
            return "OK";
        }
        if (contNgReasons.size() > 1) {
            return "NG";
        }
        return "NG " + contNgReasons.iterator().next().name().replace('_', ' ');
    }

    public String getGpsResultText() {
        if (gpsNgReasons.isEmpty()) {
            return "OK";
        }
        if (gpsNgReasons.size() > 1) {
            return "NG";
        }
        return "NG " + gpsNgReasons.iterator().next().name().replace('_', ' ');
    }

    public String getTrxResultText() {
        if (trxNgReasons.isEmpty()) {
            return "OK";
        }
        if (trxNgReasons.size() > 1) {
            return "NG";
        }
        return "NG " + trxNgReasons.iterator().next().name().replace('_', ' ');
    }

    public String getPsResultText() {
        if (psNgReasons.isEmpty()) {
            return "OK";
        }
        if (psNgReasons.size() > 1) {
            return "NG";
        }
        return "NG " + psNgReasons.iterator().next().name().replace('_', ' ');
    }
    
    /* =====================================================
     * 初期化
     * ===================================================== */
    public void reset() {
        currentRow = Row.TRANSPONDER;
        editMode = EditMode.ROW_SELECT;
        currentItem = TransponderItem.TEST_ALL;
        currentAction = Action.ENT;
        requestedLogRow = null;
    }

    private void clearAllNgReasons() {
        contNgReasons.clear();
        gpsNgReasons.clear();
        trxNgReasons.clear();
        psNgReasons.clear();
    }

    /* =====================================================
     * Getter（View / Controller 用）
     * ===================================================== */
    public Row getCurrentRow() {
        return currentRow;
    }

    public EditMode getEditMode() {
        return editMode;
    }

    public TransponderItem getCurrentItem() {
        return currentItem;
    }

    public Action getCurrentAction() {
        return currentAction;
    }

    public String getTransponderResult() {
        return transponderResult;
    }

    public String getControllerResult() {
        return controllerResult;
    }

    public String getControllerLanResult() {
        return controllerLanResult;
    }

    public DiagnoseResult getContResult() {
        return contResult;
    }

    public DiagnoseResult getGpsResult() {
        return gpsResult;
    }

    public DiagnoseResult getTrxResult() {
        return trxResult;
    }

    public DiagnoseResult getPsResult() {
        return psResult;
    }

    /* =====================================================
     * LOG 遷移要求
     * ===================================================== */
    public Row consumeRequestedLogRow() {
        Row r = requestedLogRow;
        requestedLogRow = null;
        return r;
    }
}
