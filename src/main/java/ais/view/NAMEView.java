package ais.view;

public class NAMEView extends BaseListMenuView {
    public NAMEView() {
        super(
                "LIST DISP . SET",
                "* NAME SETTING *",
                new String[] {
                    "SHIP NAME",
                    "MMSI"
                },
                30,
                42
        );
    }
}
