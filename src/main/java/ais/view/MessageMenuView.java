package ais.view;

public class MessageMenuView extends BaseListMenuView {
    public MessageMenuView() {
        super(
                "MESSAGE",
                new String[] {
                    "1. EDIT AND TX",
                    "2. TX  TRAY",
                    "3. RX  TRAY",
                    "4. INTERROGATION",
                    "5. LONG - RANGE"
                },
                40,
                57
        );
    }
}
