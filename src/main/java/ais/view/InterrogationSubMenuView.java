package ais.view;

public class InterrogationSubMenuView extends BaseListMenuView {
    public InterrogationSubMenuView() {
        super(
                "INTERROGATION",
                "* SUB MENU *",
                new String[] {
                    "[ TX ]",
                    "[ CHECK ]",
                    "[ CLEAR ]",
                    "[ EXIT ]"
                },
                35,
                55
        );
    }
}
