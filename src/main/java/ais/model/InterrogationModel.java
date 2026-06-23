package ais.model;


public class InterrogationModel implements CursorEditableModel, TransmissionStateModel {

    /* =====================================================
     * 行定義
     * ===================================================== */
    public enum Row {
        MMSI,
        REQUEST,
        RESULT
    }

    /* =====================================================
     * 編集モード
     * ===================================================== */
    public enum EditMode {
        ROW_SELECT,
        MMSI_EDIT,
        VALUE_EDIT   // REQUEST 用
    }

    public enum Result {
        NONE,
        OK,
        NG
    }

    private Result result = Result.NONE;

    public enum TxState {
        IDLE,
        TRANSMITTING,
        OK,
        NG
    }
    /* =====================================================
     * REQUEST ITEM（マニュアル準拠）
     * ===================================================== */
    public enum RequestItem {
        POSN_REPORT_A("POSN REPORT(A)"),
        STATIC_VOYAGE_A("STATIC / VOYAGE(A)"),
        SAR_AIRCRAFT("SAR AIRCRAFT POSN REPORT"),
        UTC_DATE("UTC AND DATE"),
        POSN_REPORT_B("POSN REPORT(B)"),
        STATIC_VOYAGE_B("STATIC / VOYAGE(B)"),
        ATON_REPORT("AIDS-TO-NAVIGATION REPORT"),
        BASE_STATION("BASE STATION REPORT"),
        STATIC_DATA("STATIC DATA REPORT");

        private final String label;

        RequestItem(String label) {
            this.label = label;
        }

        public String getLabel() {
            return label;
        }
    }

    /* =====================================================
     * 状態
     * ===================================================== */
    private Row currentRow = Row.MMSI;
    private EditMode editMode = EditMode.ROW_SELECT;
    private TxState txState = TxState.IDLE;
    /* ===== MMSI ===== */
    private final char[] mmsi = new char[9];
    private int mmsiCursor = 0;

    /* ===== REQUEST ===== */
    private int requestIndex = 0;
    private RequestItem request = RequestItem.POSN_REPORT_A;
    /* ===== RESULT ===== */
    private String resultText = "";

    public InterrogationModel() {
        for (int i = 0; i < mmsi.length; i++) {
            mmsi[i] = '0';
        }
    }

    /* =====================================================
     * 行移動
     * ===================================================== */
    public void moveRowUp() {
        if (editMode != EditMode.ROW_SELECT) return;

        if (currentRow == Row.REQUEST) {
            currentRow = Row.MMSI;
        }
    }

    public void moveRowDown() {
        if (editMode != EditMode.ROW_SELECT) return;

        if (currentRow == Row.MMSI) {
            currentRow = Row.REQUEST;
        }
    }

    /* =====================================================
     * 値変更
     * ===================================================== */
    public void incrementValue() {

        /* ===== MMSI EDIT ===== */
        if (editMode == EditMode.MMSI_EDIT) {
            if (mmsi[mmsiCursor] < '9') {
                mmsi[mmsiCursor]++;
            }
            return;
        }

        if (editMode != EditMode.VALUE_EDIT) {
            return;
        }

        switch (currentRow) {
            case REQUEST:
                request = RequestItem.values()[(request.ordinal() + 1) % RequestItem.values().length];
                break;
        }
    }


    public void decrementValue() {

        if (editMode == EditMode.MMSI_EDIT) {
            if (mmsi[mmsiCursor] > '0') {
                mmsi[mmsiCursor]--;
            }
            return;
        }

        if (editMode != EditMode.VALUE_EDIT) {
            return;
        }

        switch (currentRow) {
            case REQUEST:
                int idx = request.ordinal() - 1;
                if (idx < 0) {
                    idx = RequestItem.values().length - 1;
                }
                request = RequestItem.values()[idx];
                break;
        }
    }


    /* =====================================================
     * ENTER
     * ===================================================== */
    public void enter() {

        if (editMode == EditMode.MMSI_EDIT) {
            if (mmsiCursor < mmsi.length - 1) {
                mmsiCursor++;
            } else {
                // ★ 9桁目確定 → REQUEST へ
                editMode = EditMode.ROW_SELECT;
                currentRow = Row.REQUEST;
            }
            return;
        }

        if (editMode == EditMode.ROW_SELECT) {
            if (currentRow == Row.MMSI) {
                editMode = EditMode.MMSI_EDIT;
                mmsiCursor = 0;
            } else if (currentRow == Row.REQUEST) {
                editMode = EditMode.VALUE_EDIT;
            }
            return;
        }

        if (editMode == EditMode.VALUE_EDIT) {
            editMode = EditMode.ROW_SELECT;
        }
    }

    /* =====================================================
     * CLR
     * ===================================================== */
    public boolean onClr() {

        if (editMode == EditMode.MMSI_EDIT) {
            if (mmsiCursor > 0) {
                mmsiCursor--;
            }
            // 先頭桁でも編集モードを維持して画面遷移しない
            return false;
        }

        if (editMode == EditMode.VALUE_EDIT) {
            editMode = EditMode.ROW_SELECT;
            return false;
        }

        // それ以外の ROW_SELECT で CLR 押下ならリセット
        reset();
        return true;
    }


    public void reset() {
        currentRow = Row.MMSI;
        editMode = EditMode.ROW_SELECT;

        for (int i = 0; i < mmsi.length; i++) {
            mmsi[i] = '0';
        }
        mmsiCursor = 0;

        request = RequestItem.POSN_REPORT_A;
        result = Result.NONE;
    }


    /* =====================================================
    * SUB MENU: CLEAR
    * ===================================================== */
    public void clearCursor() {
        currentRow = Row.MMSI;
        editMode = EditMode.ROW_SELECT;
        mmsiCursor = 0;
    }

    /* =====================================================
    *  TX
    * ===================================================== */
    public void startTx() {
        txState = TxState.TRANSMITTING;
        result = Result.NONE;
    }

    public void setTxResult(boolean ok) {
        txState = ok ? TxState.OK : TxState.NG;
        result = ok ? Result.OK : Result.NG;
    }

    public TxState getTxState() {
        return txState;
    }
    /* =====================================================
     * Getter（View 用）
     * ===================================================== */
    public Row getCurrentRow() { return currentRow; }
    public EditMode getEditMode() { return editMode; }

    public String getMmsiText() {
        return new String(mmsi);
    }

    public int getMmsiCursor() {
        return mmsiCursor;
    }

    public String getRequestText() {
        return request.getLabel();
    }

    public RequestItem getRequestItem() {
        return request;
    }

    public String getResultText() {
        switch (result) {
            case OK:
                return "RESULT : OK";
            case NG:
                return "RESULT : NG";
            default:
                return "";
        }
    }

    public void setResult(Result result) {
        this.result = result;
    }

}
