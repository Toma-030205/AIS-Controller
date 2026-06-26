package ais.model;

import java.time.format.DateTimeFormatter;
import java.time.LocalDateTime;




public class TxMessage {

    /* ===== 基本属性 ===== */
    private int index;                 // TRAY 内番号（1～10）
    private boolean transmitted;       // 未送信 = false → "*" 表示用
    private LocalDateTime utcDateTime;  // SAVE / TX 時の UTC

    /* ===== メッセージ種別 ===== */
    private EditAndTxModel.Format format;
    private EditAndTxModel.Category category;
    private boolean reply;             // REPLY ON / OFF
    private String function;           // TEXT
    private int channel;               // CH

    /* ===== 宛先 ===== */
    private String destination;         // MMSI / BROADCAST

    /* ===== 本文 ===== */
    private String text;

    /* ===== 結果 ===== */
    private TxResult result;            // NONE / OK / NG

    /* ===== コンストラクタ ===== */
    public TxMessage() {
        this.transmitted = false;
        this.utcDateTime = LocalDateTime.now(); // SAVE 時刻
        this.result = TxResult.NONE;
    }

    public void configure(
            EditAndTxModel.Format format,
            EditAndTxModel.Category category,
            boolean reply,
            String function,
            int channel,
            String destination,
            String text) {

        this.format = format;
        this.category = category;
        this.reply = reply;
        this.function = function;
        this.channel = channel;
        this.destination = destination;
        this.text = text;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    /* =====================================================
     * 状態遷移 API（直接代入は禁止）
     * ===================================================== */
    /**
     * SAVE 時
     */
    public void markSaved() {
        this.transmitted = false;
        this.result = TxResult.NONE;
        this.utcDateTime = LocalDateTime.now();
    }

    /**
     * TX 開始時
     */
    public void markTransmitting() {
        this.transmitted = true;
        this.utcDateTime = LocalDateTime.now();
    }

    /**
     * ACK 受信時
     */
    public void markAckOk() {
        this.result = TxResult.OK;
    }

    /**
     * ACK NG（将来用）
     */
    public void markAckNg() {
        this.result = TxResult.NG;
    }

    /* =====================================================
     * 表示用ヘルパ
     * ===================================================== */
    public String getListLabel() {
        String prefix = transmitted ? "" : "*";
        return prefix + index + ". " + destination;
    }

    public String getUtcString() {
        DateTimeFormatter fmt
                = DateTimeFormatter.ofPattern("yy/MM/dd  HH:mm");
        return utcDateTime.format(fmt);
    }

    public boolean isAddressed() {
        return format == EditAndTxModel.Format.ADDRESSED;
    }

    public boolean isBroadcast() {
        return format == EditAndTxModel.Format.BROADCAST;
    }

    public LocalDateTime getUtcDateTime() {
        return utcDateTime;
    }

    public String getDestination() {
        return destination;
    }

    public TxResult getResult() {
        return result;
    }

    /* =====================================================
     * Getter（EditAndTx 再編集用）
     * ===================================================== */
    public String getMmsi() {
        return destination;
    }

    public String getCategory() {
        return category.name();
    }

    public String getFunction() {
        return function;
    }

    public String getReply() {
        return reply ? "ON" : "OFF";
    }

    public String getChannel() {
        switch (channel) {
            case 0:
                return "AUTO";
            case 1:
                return "A";
            case 2:
                return "B";
            case 3:
                return "A_B";
            default:
                return "AUTO";
        }
    }

    public String getText() {
        return text;
    }
}
