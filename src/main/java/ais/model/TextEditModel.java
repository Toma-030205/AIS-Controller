package ais.model;



public class TextEditModel {

    private final StringBuilder buffer = new StringBuilder();
    private int maxLength;

    public TextEditModel(int maxLength) {
        this.maxLength = maxLength;
    }

    /* =========================
     * 最大長制御
     * ========================= */
    public void setMaxLength(int maxLength) {
        this.maxLength = maxLength;

        // 既存文字列が超過していた場合は切り詰め
        if (buffer.length() > maxLength) {
            buffer.setLength(maxLength);
        }
    }

    public int getMaxLength() {
        return maxLength;
    }

    /* =========================
     * 編集操作
     * ========================= */
    public boolean append(String s) {
        if (buffer.length() + s.length() > maxLength) {
            return false; // 追加不可
        }
        buffer.append(s);
        return true;
    }

    public void backspace() {
        if (buffer.length() > 0) {
            buffer.deleteCharAt(buffer.length() - 1);
        }
    }

    public void clear() {
        buffer.setLength(0);
    }

    /* =========================
     * 表示用
     * ========================= */
    public String getText() {
        return buffer.toString();
    }

    public int length() {
        return buffer.length();
    }
}
