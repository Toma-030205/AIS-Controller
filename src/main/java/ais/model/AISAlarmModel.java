package ais.model;

import java.time.format.DateTimeFormatter;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;



public class AISAlarmModel {

    /* =====================================================
     * 内部データ定義
     * ===================================================== */
    public static class AlarmEntry {

        public enum Condition {
            A, // Occurring
            V   // Restored
        }

        private final int alarmNo;
        private final Condition condition;
        private final LocalDateTime utcTime;
        private final String description;

        public AlarmEntry(int alarmNo,
                Condition condition,
                LocalDateTime utcTime,
                String description) {
            this.alarmNo = alarmNo;
            this.condition = condition;
            this.utcTime = utcTime;
            this.description = description;
        }
    }

    /* =====================================================
     * 表示モード
     * ===================================================== */
    public enum DisplayMode {
        CURRENT, // 発生中のみ
        HISTORY    // 履歴（A + V）
    }

    /* =====================================================
     * 定数
     * ===================================================== */
    private static final int ITEMS_PER_PAGE = 4;
    private static final int MAX_HISTORY = 50;

    private static final DateTimeFormatter TIME_FMT
            = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm");

    /* =====================================================
     * 状態
     * ===================================================== */
    private final List<AlarmEntry> allEntries = new ArrayList<>();
    private DisplayMode displayMode = DisplayMode.CURRENT;
    private int pageIndex = 0;

    /* =====================================================
     * モード制御
     * ===================================================== */
    public void setDisplayMode(DisplayMode mode) {
        this.displayMode = mode;
        this.pageIndex = 0;
    }

    public DisplayMode getDisplayMode() {
        return displayMode;
    }

    /* =====================================================
     * データ投入
     * ===================================================== */
    public void add(AlarmEntry entry) {
        allEntries.add(entry);

        // 履歴は最大50件
        if (allEntries.size() > MAX_HISTORY) {
            allEntries.remove(0);
        }
    }

    /* =====================================================
     * ページング制御
     * ===================================================== */
    public boolean hasPrev() {
        return pageIndex > 0;
    }

    public boolean hasNext() {
        return (pageIndex + 1) * ITEMS_PER_PAGE < getDisplayEntries().size();
    }

    public void prevPage() {
        if (hasPrev()) {
            pageIndex--;
        }
    }

    public void nextPage() {
        if (hasNext()) {
            pageIndex++;
        }
    }

    public int getPageStartIndex() {
        return pageIndex * ITEMS_PER_PAGE;
    }

    /* =====================================================
     * 表示対象抽出
     * ===================================================== */
    public List<AlarmEntry> getDisplayEntries() {

        if (displayMode == DisplayMode.CURRENT) {
            List<AlarmEntry> list = new ArrayList<>();
            for (AlarmEntry e : allEntries) {
                if (e.condition == AlarmEntry.Condition.A) {
                    list.add(e);
                }
            }
            return list;
        }

        // HISTORY：古い順で最大50件
        return new ArrayList<>(allEntries);
    }

    /* =====================================================
     * 表示判定
     * ===================================================== */
    public boolean isNoData() {
        return getDisplayEntries().isEmpty();
    }

    /* =====================================================
     * View 用 API
     * ===================================================== */
    public List<String[]> getCurrentPageLines() {

        List<AlarmEntry> src = getDisplayEntries();
        if (src.isEmpty()) {
            return Collections.emptyList();
        }

        int from = pageIndex * ITEMS_PER_PAGE;
        int to = Math.min(from + ITEMS_PER_PAGE, src.size());

        List<String[]> lines = new ArrayList<>();

        for (AlarmEntry e : src.subList(from, to)) {

            String time = (e.utcTime == null)
                    ? "--/--/-- --:--"
                    : e.utcTime.format(TIME_FMT);

            String content = String.format(
                    "%03d, %s, %s",
                    e.alarmNo,
                    e.condition.name(),
                    e.description
            );

            lines.add(new String[]{time, content});
        }
        return lines;
    }

    /* =====================================================
     * ダミーデータ生成
     * ===================================================== */
    public static AISAlarmModel createWithDummyData() {

        AISAlarmModel model = new AISAlarmModel();

        Object[][] defs = {
            {1, "TX MALFUNCTION"},
            {2, "ANTENNA VSWR EXCEEDS LIMIT"},
            {3, "RX CHANNEL 1 MALFUNCTION"},
            {4, "RX CHANNEL 2 MALFUNCTION"},
            {5, "RX CHANNEL 70 MALFUNCTION"},
            {6, "GENERAL FAILURE"},
            {8, "MKD CONNECTION LOST"},
            {10, "NAVSTATUS INCORRECT"},
            {14, "ACTIVE AIS-SART"},
            {25, "EXTERNAL EPFS LOST"},
            {26, "NO SENSOR POSITION IN USE"},
            {29, "NO VALID SOG INFORMATION"},
            {30, "NO VALID COG INFORMATION"},
            {32, "HEADING LOST/INVALID"},
            {35, "NO VALID ROT INFORMATION"},
            {51, "TX POWER DOWN"},
            {52, "TX POWER SUPPLY ERROR"},
            {53, "POWER SUPPLY ERROR"},
            {54, "PA CURRENT ERROR"},
            {55, "PA TEMP ERROR"}
        };

        LocalDateTime base
                = LocalDateTime.of(2026, 2, 3, 19, 21);

        int i = 0;
        for (Object[] d : defs) {

            AlarmEntry.Condition cond
                    = (i % 4 == 0)
                            ? AlarmEntry.Condition.V
                            : AlarmEntry.Condition.A;

            LocalDateTime time
                    = (i % 6 == 0)
                            ? null
                            : base.minusMinutes(i * 5);

            model.add(new AlarmEntry(
                    (Integer) d[0],
                    cond,
                    time,
                    (String) d[1]
            ));
            i++;
        }

        return model;
    }
}
