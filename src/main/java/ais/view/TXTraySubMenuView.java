package ais.view;

public class TXTraySubMenuView extends BaseListMenuView {
    public TXTraySubMenuView() {
        super(
                "TX TRAY",
                "* SUB MENU *",
                new String[] {
                    "[ DETAIL VIEW ]",
                    "[ EDIT ]",
                    "[ DELETE ]",
                    "[ EXIT ]"
                },
                40,
                50
        );
        getList().setSelectedIndex(0);
        getList().setFocusable(false);
    }
}
