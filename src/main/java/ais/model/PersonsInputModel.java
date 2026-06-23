package ais.model;



public class PersonsInputModel {

    /*
     * 搭乗人員数 0 ～ 8191
     *
     * index: 0=千、1=百、2=十、3=一の桁
     */
    private final int[] digits = {0, 0, 0, 0};
    private int cursor = 0;

    private static final int MAX = 8191;

    /* 表示用文字列 */
    public String getDisplayText() {
        if (getValue() >= MAX) {
            return "8191 OR MORE";
        }
        return String.format("%d%d%d%d", digits[0], digits[1], digits[2], digits[3]);
    }

    /* 数値として返す */
    public int getValue() {
        return digits[0] * 1000 + digits[1] * 100 + digits[2] * 10 + digits[3];
    }

    /* カーソル操作 */
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

    /* ▲▼入力 */
    public void increment() {
        digits[cursor]++;
        normalize();
    }

    public void decrement() {
        digits[cursor]--;
        normalize();
    }

    private void normalize() {
        // 桁を0～9に制限
        for (int i = 0; i < digits.length; i++) {
            if (digits[i] < 0) {
                digits[i] = 0;
            }
            if (digits[i] > 9) {
                digits[i] = 9;
            }
        }
        // 8191以上は8191固定
        if (getValue() > MAX) {
            digits[0] = 8;
            digits[1] = 1;
            digits[2] = 9;
            digits[3] = 1;
        }
    }

    public int getCursor() {
        return cursor;
    }

    public int[] getDigits() {
        return digits;
    }

    /* 確定 */
    public boolean confirm() {
        return true; // ここでは常に確定可能
    }

    public void reset() {
        digits[0] = 0;
        digits[1] = 0;
        digits[2] = 0;
        digits[3] = 0;
        cursor = 0;
    }
}
