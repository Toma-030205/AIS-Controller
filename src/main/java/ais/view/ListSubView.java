package ais.view;

public class ListSubView extends BaseListMenuView {
    public ListSubView() {
        super(
                "LIST DISP . SET",
                "* SUB MENU *",
                new String[] {
                    "1.[ BEARING ]",
                    "2.[ SORT ]",
                    "3.[ NAME ]",
                    "4.[ DISP ]",
                    "5.[ EXIT ]"
                },
                30,
                42
        );
    }

    public String getSelectedItem() {
        return getSelectedValue();
    }
}
