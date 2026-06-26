package ais.model;


public class CommunicationTestModel implements CursorEditableModel, TransmissionStateModel {

    /* =====================================================
     * 行定義
     * ===================================================== */
    public enum Row {
        ACTION,
        RESULT
    }

    /* =====================================================
     * ACTION
     * ===================================================== */
    public enum Action {
        TX,
        CANCEL
    }

    /* =====================================================
     * RESULT
     * ===================================================== */
    public enum Result {
        NONE,
        OK,
        NG
    }

    public enum TxState {
        IDLE,
        TRANSMITTING,
        OK,
        NG
    }

    /* =====================================================
     * 状態
     * ===================================================== */
    private Row currentRow = Row.ACTION;
    private Action action = Action.TX;

    /* ===== 自動設定 MMSI（表示専用） ===== */
    private String destinationMmsi = "";

    private Result result = Result.NONE;
    private TxState txState = TxState.IDLE;

    /* =====================================================
     * 行移動
     * ===================================================== */
    public void moveRowUp() {
        if (currentRow == Row.RESULT) {
            currentRow = Row.ACTION;
        }
    }

    public void moveRowDown() {
        if (currentRow == Row.ACTION) {
            currentRow = Row.RESULT;
        }
    }

    /* =====================================================
     * 値変更（ACTION 切替）
     * ===================================================== */
    public void incrementValue() {
        if (currentRow == Row.ACTION) {
            toggleAction();
        }
    }

    public void decrementValue() {
        if (currentRow == Row.ACTION) {
            toggleAction();
        }
    }

    private void toggleAction() {
        action = (action == Action.TX) ? Action.CANCEL : Action.TX;
    }

    /* =====================================================
     * CLR
     * ===================================================== */
    public boolean onClr() {
        reset();
        return true;    // MAINTENANCE へ戻る
    }

    public void enter() {
        // COMMUNICATION TEST の Enter は画面側の Action 行で処理する。
    }

    public void reset() {
        currentRow = Row.ACTION;
        action = Action.TX;
        result = Result.NONE;
        txState = TxState.IDLE;
    }

    /* =====================================================
     * TX
     * ===================================================== */
    public void startTest() {
        txState = TxState.TRANSMITTING;
        result = Result.NONE;
    }

    @Override
    public void startTx() {
        startTest();
    }

    public void setResult(boolean ok) {
        txState = ok ? TxState.OK : TxState.NG;
        result = ok ? Result.OK : Result.NG;
    }

    @Override
    public void setTxResult(boolean ok) {
        setResult(ok);
    }

    /* =====================================================
     * 自動 MMSI 設定（外部入力）
     * ===================================================== */
    public void setDestinationMmsi(String mmsi) {
        this.destinationMmsi = mmsi;
    }

    /* =====================================================
     * Getter（View / Controller 用）
     * ===================================================== */
    public Row getCurrentRow() {
        return currentRow;
    }

    public Action getAction() {
        return action;
    }

    public String getDestinationMmsi() {
        return destinationMmsi;
    }

    public TxState getTxState() {
        return txState;
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
}
