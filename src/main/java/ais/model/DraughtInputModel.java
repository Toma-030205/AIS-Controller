package ais.model;

import ais.view.VoyageView;


public class DraughtInputModel {

    /*
     * 喫水：0.0 ～ 99.9 m
     *
     * index:
     *  0 -> tens
     *  1 -> ones
     *  2 -> decimal (0.1)
     */
    private final int[] digits = {
        0, 0, 0   // 初期値 0.0
    };

    // 編集中の桁（0～2）
    private int cursor = 0;

    /* =====================================================
     * 表示用 API（View から呼ばれる）
     * ===================================================== */

    /**
     * 表示文字列を返す
     * 例: "12.3 m" / "25.5M OR GREATER"
     */
    public String getDisplayText() {
        if (isOverLimit()) {
            return "25.5M OR GREATER";
        }
        return String.format("%d%d.%d m",
                digits[0], digits[1], digits[2]);
    }

    /**
     * 現在の値を数値として返す（内部用）
     */
    public double getValue() {
        return digits[0] * 10
             + digits[1]
             + digits[2] * 0.1;
    }

    /**
     * 現在の編集桁
     */
    public int getCursor() {
        return cursor;
    }

    /**
     * 桁配列（View が参照する場合用）
     */
    public int[] getDigits() {
        return digits;
    }

    /* =====================================================
     * カーソル操作
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
     * ▲▼ 入力
     * ===================================================== */

    public void increment() {
        digits[cursor]++;
        if (digits[cursor] > 9) {
            digits[cursor] = 0;
        }
    }

    public void decrement() {
        digits[cursor]--;
        if (digits[cursor] < 0) {
            digits[cursor] = 9;
        }
    }

    /* =====================================================
     * 入力完了判定
     * ===================================================== */

    public boolean isLastDigit() {
        return cursor == digits.length - 1;
    }

    /* =====================================================
     * 上限判定（マニュアル仕様）
     * ===================================================== */

    /**
     * 25.5 m を超えているか
     */
    public boolean isOverLimit() {
        return getValue() > 25.5;
    }

    /* =====================================================
     * 確定値取得（VOYAGE DATA 用）
     * ===================================================== */

    /**
     * VoyageView に渡す表示値
     */
    public String getDraughtString() {
        return getDisplayText();
    }

    


    /* =====================================================
     * 初期化
     * ===================================================== */

    public void reset() {
        digits[0] = 0;
        digits[1] = 0;
        digits[2] = 0;
        cursor = 0;
    }
}
