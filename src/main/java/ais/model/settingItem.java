package ais.model;


public class settingItem {
    public final String text;
    public final boolean selectable;
    public final int id;

    public settingItem(int id, String text, boolean selectable) {
        this.id = id;
        this.text = text;
        this.selectable = selectable;
    }

    @Override
    public String toString() {
        return text;
    }
}
