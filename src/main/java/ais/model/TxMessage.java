package ais.model;

import java.time.format.DateTimeFormatter;
import java.time.LocalDateTime;




public class TxMessage {

    /* ===== 基本属性 ===== */
    public int index;                 // TRAY 内番号（1～10）
    public boolean transmitted;       // 未送信 = false → "*" 表示用
    public LocalDateTime utcDateTime;  // SAVE / TX 時の UTC

    /* ===== メッセージ種別 ===== */
    public EditAndTxModel.Format format;
    public EditAndTxModel.Category category;
    public boolean reply;             // REPLY ON / OFF
    public String function;           // TEXT
    public int channel;               // CH

    /* ===== 宛先 ===== */
    public String destination;         // MMSI / BROADCAST

    /* ===== 本文 ===== */
    public String text;

    /* ===== 結果 ===== */
    public TxResult result;            // NONE / OK / NG

    /* ===== コンストラクタ ===== */
    public TxMessage() {
        this.transmitted = false;
        this.utcDateTime = LocalDateTime.now(); // SAVE 時刻
        this.result = TxResult.NONE;
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
