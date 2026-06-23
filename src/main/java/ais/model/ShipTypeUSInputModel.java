package ais.model;


public class ShipTypeUSInputModel {

    private boolean isOn = false; // false = OFF, true = ON

    /* 表示文字列取得 */
    public String getValue() {
        return isOn ? "ON" : "OFF";
    }

    /* ▲▼キーで切り替え */
    public void toggle() {
        isOn = !isOn;
    }

    /* 確定可能か（常にOK） */
    public boolean confirm() {
        return true;
    }

    /* 初期化 */
    public void reset() {
        isOn = false;
    }
}
