package ais.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;



public class DiagnosisLogModel {

    /* =====================================================
     * 定数
     * ===================================================== */
    public static final int MAX_LOG_COUNT = 20;
    public static final int PAGE_SIZE = 1; // 1結果 = 1ページ（実機仕様）

    /* =====================================================
     * LOG 本体（FIFO）
     * index 0 = 最新
     * ===================================================== */
    private final List<DiagnosisLogEntry> logs = new ArrayList<>();

    /* =====================================================
     * 現在ページ
     * ===================================================== */
    private int currentPage = 0;

    /* =====================================================
     * LOG 追加（FIFO）
     * ===================================================== */
    public void addLog(DiagnosisLogEntry entry) {
        logs.add(0, entry); // 先頭に追加（最新）

        if (logs.size() > MAX_LOG_COUNT) {
            logs.remove(logs.size() - 1); // 最古削除
        }

        currentPage = 0; // 新規追加時は常に最新ページ
    }

    /* =====================================================
     * ページ操作（▲ / ▼）
     * ===================================================== */
    public void nextPage() {
        if (currentPage < getMaxPage()) {
            currentPage++;
        }
    }

    public void prevPage() {
        if (currentPage > 0) {
            currentPage--;
        }
    }

    /* =====================================================
     * 現在表示用ログ取得
     * ===================================================== */
    public List<DiagnosisLogEntry> getCurrentPageEntries() {

        if (logs.isEmpty()) {
            return Collections.emptyList();
        }

        int start = currentPage * PAGE_SIZE;
        int end = Math.min(start + PAGE_SIZE, logs.size());

        if (start >= logs.size()) {
            return Collections.emptyList();
        }

        return logs.subList(start, end);
    }

    /* =====================================================
     * ページ情報
     * ===================================================== */
    public int getCurrentPage() {
        return currentPage;
    }

    public int getMaxPage() {
        if (logs.isEmpty()) {
            return 0;
        }
        return (logs.size() - 1) / PAGE_SIZE;
    }

    public int getLogCount() {
        return logs.size();
    }

    /* =====================================================
     * 全消去（CLR 用など）
     * ===================================================== */
    public void clear() {
        logs.clear();
        currentPage = 0;
    }
}
