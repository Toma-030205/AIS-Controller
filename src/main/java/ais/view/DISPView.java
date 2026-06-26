package ais.view;

public class DISPView extends BaseListMenuView {
    public DISPView() {
        super(
                "LIST DISP . SET",
                "* DISPLAY SETTING *",
                new String[] {
                    "NORMAL",
                    "TYPE1",
                    "TYPE2"
                },
                30,
                42
        );
    }
}
