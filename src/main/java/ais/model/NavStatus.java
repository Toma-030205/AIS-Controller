package ais.model;


public enum NavStatus {

    UNDER_WAY_USING_ENGINE(0, " UNDER WAY USING ENGINE"),
    AT_ANCHOR(1, " AT ANCHOR"),
    NOT_UNDER_COMMAND(2, " NOT UNDER COMMAND"),
    RESTRICTED_MANOEUVRABILITY(3, " RESTRICTED MANOEUVRABILITY"),
    CONSTRAINED_BY_DRAUGHT(4, " CONSTRAINED BY DRAFT"),
    MOORED(5, " MOORED"),
    AGROUND(6, " AGROUND"),
    ENGAGED_IN_FISHING(7, " ENGAGED IN FISHING"),
    UNDER_WAY_SAILING(8, " UNDER WAY SAILING"),
    RESERVED_FOR_HSC(9, " RESERVED FOR HSC"),
    RESERVED_FOR_WIG(10, " RESERVED FOR WIG"),
    NOT_DEFINED(15, " NOT_DEFINED");

    private final int aisValue;
    private final String label;

    NavStatus(int aisValue, String label) {
        this.aisValue = aisValue;
        this.label = label;
    }

    public int getAisValue() {
        return aisValue;
    }

    public String getLabel() {
        return label;
    }

    /** UI の選択 index → NavStatus */
    public static NavStatus fromIndex(int index) {
        if (index < 0 || index >= values().length - 1) {
            return NOT_DEFINED;
        }
        return values()[index];
    }

    /** 表示文字列 → NavStatus */
    public static NavStatus fromLabel(String label) {
        for (NavStatus s : values()) {
            if (s.label.equals(label)) {
                return s;
            }
        }
        return NOT_DEFINED;
    }
}
