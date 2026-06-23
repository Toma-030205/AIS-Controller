package ais.model;

import java.time.format.DateTimeFormatter;
import java.time.LocalDateTime;



public class RxMessage {

    /* ===== 基本属性 ===== */
    public int index;                  // TRAY 内番号
    public LocalDateTime utcDateTime;   // 受信時刻（仮）

    /* ===== 状態フラグ ===== */
    public boolean unread;              // 未読 = true → "*" 表示
    public boolean replyRequired;       // "R" 表示
    public boolean replyReceived;       // "A" 表示

    /* ===== メッセージ種別 ===== */
    public EditAndTxModel.Format format;
    public EditAndTxModel.Category category;
    public boolean reply;               // REPLY ON / OFF
    public String function;             // TEXT 等
    public int channel;                 // CH

    /* ===== 送信元 ===== */
    public String source;               // MMSI or NAME

    /* ===== 本文 ===== */
    public String text;

    /* ===== コンストラクタ ===== */
    public RxMessage() {
        this.unread = true;
        this.replyRequired = false;
        this.replyReceived = false;
        this.utcDateTime = LocalDateTime.now();
    }

    /* =====================================================
     * 状態遷移 API（直接代入は禁止）
     * ===================================================== */

    /**
     * 受信時
     */
    public void markReceived() {
        this.unread = true;
        this.utcDateTime = LocalDateTime.now();
    }

    /**
     * TEXT VIEW 表示時
     */
    public void markRead() {
        this.unread = false;
    }

    /**
     * 返信要求あり（未返信）
     */
    public void markReplyRequired() {
        this.replyRequired = true;
        this.replyReceived = false;
    }

    /**
     * 返信メッセージ受信
     */
    public void markReplyReceived() {
        this.replyRequired = false;
        this.replyReceived = true;
    }

    /* =====================================================
     * 表示用ヘルパ
     * ===================================================== */

    /**
     * RX TRAY 一覧表示用
     */
    public String getListLabel() {
        // 未読
        String unreadMark = unread ? "*" : " ";

        // 返信要求 / 返信済み
        String replyMark = " ";
        if (replyRequired) {
            replyMark = "R"; 
        }else if (replyReceived) {
            replyMark = "A";
        }

        // 番号＋宛先
        String numAndDest = index + ". " + getDestinationLabel();

        // 空白で整列（未読1文字＋返信1文字＋スペース1文字）
        return String.format("%s%s %s", unreadMark, replyMark, numAndDest);
    }



    /**
     * 一覧・詳細共通：宛先表示
     */
    public String getDestinationLabel() {
        return (format == EditAndTxModel.Format.BROADCAST)
                ? "BROADCAST"
                : source;
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
     * Getter（DETAIL VIEW / EDIT 用）
     * ===================================================== */

    public String getSource() {
        return source;
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
