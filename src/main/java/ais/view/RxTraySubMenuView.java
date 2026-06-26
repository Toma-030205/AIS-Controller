package ais.view;

public class RxTraySubMenuView extends BaseListMenuView {
    public RxTraySubMenuView() {
        super(
                "OTHERS MESSAGES TRAY",
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
