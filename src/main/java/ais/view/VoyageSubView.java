package ais.view;

public class VoyageSubView extends BaseListMenuView {
    public VoyageSubView() {
        super(
                "VOYAGE DATA",
                "* SUB MENU *",
                new String[] {
                    "[ SET ]",
                    "[ DEST LOAD ]",
                    "[ EXIT ]"
                },
                35,
                60
        );
    }
}
