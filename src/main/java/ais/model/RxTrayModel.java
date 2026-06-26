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

        int max = (msg.getCategoryValue() == EditAndTxModel.Category.SAFETY)
                ? MAX_SAFETY
                : MAX_OTHERS;

        // 同一 CATEGORY 内での件数制御
        trimByCategory(msg.getCategoryValue(), max);

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
            if (m.getCategoryValue() == category) {
                count++;
            }
        }

        // 超過分を削除
        for (int i = messages.size() - 1; i >= 0 && count >= max; i--) {
            if (messages.get(i).getCategoryValue() == category) {
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
            messages.get(i).setIndex(i + 1);
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

        for (int i = 0; i < 5; i++) {
            add(createDummyMessage(
                    EditAndTxModel.Category.SAFETY,
                    LocalDateTime.now().minusMinutes(i * 3),
                    "12345678" + i,
                    "SAFETY MESSAGE DUMMY " + (i + 1),
                    i));
        }

        for (int i = 0; i < 5; i++) {
            add(createDummyMessage(
                    EditAndTxModel.Category.ROUTINE,
                    LocalDateTime.now().minusMinutes(30 + i * 2),
                    "87654321" + i,
                    "ROUTINE MESSAGE DUMMY " + (i + 1),
                    i));
        }
    }

    private RxMessage createDummyMessage(
            EditAndTxModel.Category category,
            LocalDateTime utcDateTime,
            String source,
            String text,
            int index) {

        RxMessage message = new RxMessage();
        message.configure(category, utcDateTime, source, text);
        if (index == 0) {
            message.setFlags(true, true, false);
        } else if (index == 1) {
            message.setFlags(false, false, true);
        } else {
            message.setFlags(true, false, false);
        }
        return message;
    }


}
