package ais.model;



public class ETAInputModel {

    /*
     * 編集対象の 8 桁（数値そのものではなく「桁」）
     *
     * index:
     *  0 1   -> Month (MM)
     *  2 3   -> Day   (DD)
     *  4 5   -> Hour  (HH)
     *  6 7   -> Minute(MM)
     */
    private final int[] digits = {
        0, 1, // Month = 01
        0, 1, // Day   = 01
        0, 0, // Hour  = 00
        0, 0 // Minute= 00
    };

    // 現在編集している桁（0〜7）
    private int cursor = 0;

    // 表示文字列上のカーソル位置変換
    // "MM/DD HH:MM"
    private static final int[] DISPLAY_POS = {
        0, 1, // MM
        3, 4, // DD
        6, 7, // HH
        9, 10 // MM
    };

    /* =====================================================
     * 表示用 API（View からのみ呼ばれる）
     * ===================================================== */
    /**
     * 表示用文字列を返す 例: "01/23 14:05"
     */
    public String getDisplayText() {
        return String.format(
                "%d%d/%d%d %d%d:%d%d",
                digits[0], digits[1],
                digits[2], digits[3],
                digits[4], digits[5],
                digits[6], digits[7]
        );
    }

    public int getCursor() {
        return cursor;
    }

    public int[] getDigits() {
        return digits;
    }


    /* =====================================================
     * カーソル移動
     * ===================================================== */
    public void moveCursorLeft() {
        if (cursor > 0) {
            cursor--;
        }
    }

    public void moveCursorRight() {
        if (cursor < digits.length - 1) {
            cursor++;
        }
    }

    /* =====================================================
     * ▲▼ 入力（桁単位）
     * ===================================================== */
    public void increment() {
        int max = getMaxDigit(cursor);
        digits[cursor]++;
        if (digits[cursor] > max) {
            digits[cursor] = 0;
        }
    }

    public void decrement() {
        int max = getMaxDigit(cursor);
        digits[cursor]--;
        if (digits[cursor] < 0) {
            digits[cursor] = max;
        }
    }

    /* =====================================================
     * 桁ごとの制約
     * ===================================================== */
    private int getMaxDigit(int idx) {

        switch (idx) {

            /* ---- Month ---- */
            case 0: // M tens
                return 1; // 0–1
            case 1: // M ones
                return (digits[0] == 1) ? 2 : 9;

            /* ---- Day ---- */
            case 2: // D tens
                return 3; // 0–3
            case 3: // D ones
                if (digits[2] == 3) {
                    return 1; // 30–31
                }
                return 9;

            /* ---- Hour ---- */
            case 4: // H tens
                return 2; // 0–2
            case 5: // H ones
                return (digits[4] == 2) ? 3 : 9;

            /* ---- Minute ---- */
            case 6: // M tens
                return 5; // 0–5
            case 7: // M ones
                return 9;
        }

        return 9;
    }

    /* =====================================================
     * 入力完了判定
     * ===================================================== */
    /* =====================================================
 * ENTER（確定）
 * ===================================================== */
    public boolean confirm() {
        if (cursor < digits.length - 1) {
            cursor++;
            return false; // まだ途中
        } else {
            return true;  // 最後の桁 → 完了
        }
    }

    public boolean isLastDigit() {
        return cursor == digits.length - 1;
    }

    /* =====================================================
     * 確定値取得（VOYAGE DATA 用）
     * ===================================================== */
    /**
     * ETA 表示用（VOYAGE DATA に渡す）
     */
    public String getEtaString() {
        return getDisplayText();
    }

    public int getMonth() {
        return digits[0] * 10 + digits[1];
    }

    public int getDay() {
        return digits[2] * 10 + digits[3];
    }

    public int getHour() {
        return digits[4] * 10 + digits[5];
    }

    public int getMinute() {
        return digits[6] * 10 + digits[7];
    }


    /* =====================================================
     * 初期化
     * ===================================================== */
    public void reset() {
        digits[0] = 0;
        digits[1] = 1;
        digits[2] = 0;
        digits[3] = 1;
        digits[4] = 0;
        digits[5] = 0;
        digits[6] = 0;
        digits[7] = 0;
        cursor = 0;
    }
}
