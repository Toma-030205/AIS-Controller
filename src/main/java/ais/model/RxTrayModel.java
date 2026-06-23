package ais.model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;



public class RxTrayModel {

    private static final int MAX_SAFETY = 20;
    private static final int MAX_OTHERS = 10;

    private final List<RxMessage> messages = new ArrayList<>();

    /* =====================================================
     * 追加（RECEIVE）
     * ===================================================== */
    public void add(RxMessage msg) {

        int max = (msg.category == EditAndTxModel.Category.SAFETY)
                ? MAX_SAFETY
                : MAX_OTHERS;

        // 同一 CATEGORY 内での件数制御
        trimByCategory(msg.category, max);

        // 新しいメッセージは先頭
        messages.add(0, msg);
        renumber();
    }

    /**
     * 指定 CATEGORY の件数が max を超える場合、
     * 古い順（末尾側）から削除
     */
    private void trimByCategory(EditAndTxModel.Category category, int max) {

        int count = 0;

        // 現在件数を数える
        for (RxMessage m : messages) {
            if (m.category == category) {
                count++;
            }
        }

        // 超過分を削除
        for (int i = messages.size() - 1; i >= 0 && count >= max; i--) {
            if (messages.get(i).category == category) {
                messages.remove(i);
                count--;
            }
        }
    }

    /* =====================================================
     * 削除
     * ===================================================== */
    public void remove(int index) {
        if (index < 0 || index >= messages.size()) {
            return;
        }
        messages.remove(index);
        renumber();
    }

    /* =====================================================
     * 取得
     * ===================================================== */
    public RxMessage get(int index) {
        if (index < 0 || index >= messages.size()) {
            return null;
        }
        return messages.get(index);
    }

    public List<RxMessage> getAll() {
        return messages;
    }

    public int size() {
        return messages.size();
    }

    /* =====================================================
     * 番号振り直し
     * ===================================================== */
    private void renumber() {
        for (int i = 0; i < messages.size(); i++) {
            messages.get(i).index = i + 1;
        }
    }

    /* =====================================================
    * テスト用ダミーデータ生成
     * ===================================================== */
    /**
     * RX TRAY テスト用ダミーメッセージを生成 起動時やデバッグ用途専用
     */
    public void generateDummyMessages() {

        messages.clear();

        // SAFETY ダミー（5件）
        for (int i = 0; i < 5; i++) {
            RxMessage m = new RxMessage();
            m.category = EditAndTxModel.Category.SAFETY;
            m.utcDateTime = LocalDateTime.now().minusMinutes(i * 3);
            m.source = "12345678" + i;
            m.text = "SAFETY MESSAGE DUMMY " + (i + 1);

            // 状態フラグの設定例
            if (i == 0) {
                m.unread = true;
                m.replyRequired = true;  // "*R"
            } else if (i == 1) {
                m.unread = false;
                m.replyReceived = true; // "A"
            } else {
                m.unread = true;
                m.replyRequired = false;
                m.replyReceived = false; // "*"
            }

            add(m);
        }

        // ROUTINE ダミー（5件）
        for (int i = 0; i < 5; i++) {
            RxMessage m = new RxMessage();
            m.category = EditAndTxModel.Category.ROUTINE;
            m.utcDateTime = LocalDateTime.now().minusMinutes(30 + i * 2);
            m.source = "87654321" + i;
            m.text = "ROUTINE MESSAGE DUMMY " + (i + 1);

            // 状態フラグの設定例
            if (i == 0) {
                m.unread = true;
                m.replyRequired = true;  // "*R"
            } else if (i == 1) {
                m.unread = false;
                m.replyReceived = true; // "A"
            } else {
                m.unread = true;
                m.replyRequired = false;
                m.replyReceived = false; // "*"
            }

            add(m);
        }
    }    

}
