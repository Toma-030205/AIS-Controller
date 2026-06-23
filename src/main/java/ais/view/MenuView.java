package ais.view;

public class MenuView extends BaseListMenuView {
    public MenuView() {
        super(
                "MAIN MENU",
                new String[] {
                    "1. VOYAGE DATA",
                    "2. MESSAGE",
                    "3. MAINTENANCE",
                    "4. SET UP",
                    "5. LINE MONITOR",
                    "      [ EXIT ]"
                },
                32,
                44
        );
    }
}
