package ais.model;

import java.util.ArrayList;
import java.util.List;



public class TxTrayModel {

    private static final int MAX = 10;
    private final List<TxMessage> messages = new ArrayList<>();

    /* ===== 追加（SAVE） ===== */
    public void add(TxMessage msg) {

        // 10 件超過時は先頭削除（実機準拠）
        if (messages.size() >= MAX) {
            messages.remove(0);
        }

        messages.add(0, msg);
        renumber();
    }

    /* ===== 削除 ===== */
    public void remove(int index) {
        if (index < 0 || index >= messages.size()) {
            return;
        }
        messages.remove(index);
        renumber();
    }

    /* ===== 取得 ===== */
    public TxMessage get(int index) {
        if (index < 0 || index >= messages.size()) {
            return null;
        }
        return messages.get(index);
    }

    public List<TxMessage> getAll() {
        return messages;
    }

    public int size() {
        return messages.size();
    }

    /* ===== 番号振り直し ===== */
    private void renumber() {
        for (int i = 0; i < messages.size(); i++) {
            messages.get(i).setIndex(i + 1);
        }
    }
}
