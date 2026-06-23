package ais.view;

public class MaintenanceMenuView extends BaseListMenuView {
    public MaintenanceMenuView() {
        super(
                "MAINTENANCE",
                new String[] {
                    "1. SELF DIAGNOSIS",
                    "2. COMMUNICATION TEST",
                    "3. AIS ALARM",
                    "4. SENSOR STATUS",
                    "5. EVENT LOG",
                    "6. SOFTWARE VERSION"
                },
                32,
                44
        );
    }
}
