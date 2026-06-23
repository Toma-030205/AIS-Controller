package ais.app;

import java.util.HashMap;
import java.util.Map;

public enum ScreenId {
    TEXT("text"),
    LIST("list"),
    MENU("menu"),
    MESSAGE("message"),
    EDIT_AND_TX("editAndTx"),
    EDIT_AND_TX_SUB("EDIT_AND_TX_SUB"),
    TEXT_EDIT("textEdit"),
    EDIT_AND_TX_TX_CONFIRM("EDIT_AND_TX_TX_CONFIRM"),
    EDIT_AND_TX_TX_TRANSMITTING("EDIT_AND_TX_TX_TRANSMITTING"),
    EDIT_AND_TX_TX_RESULT("EDIT_AND_TX_TX_RESULT"),
    TX_TRAY("TX_TRAY"),
    TX_TRAY_TEXT("TX_TRAY_TEXT"),
    TX_TRAY_SUB("TX_TRAY_SUB"),
    TX_TRAY_DETAIL("TX_TRAY_DETAIL"),
    RX_TRAY("RX_TRAY"),
    RX_TRAY_SUB("RX_TRAY_SUB"),
    RX_TRAY_TEXT("RX_TRAY_TEXT"),
    RX_TRAY_DETAIL("RX_TRAY_DETAIL"),
    INTERROGATION("INTERROGATION"),
    INTERROGATION_SUB("INTERROGATION_SUB"),
    INTERROGATION_TX("INTERROGATION_TX"),
    INTERROGATION_TX_RESULT("INTERROGATION_TX_RESULT"),
    MAINTENANCE_MENU("MAINTENANCE_MENU"),
    SELF_DIAGNOSIS("SELF_DIAGNOSIS"),
    TRANSPONDER_LOG("TRANSPONDER_LOG"),
    CONTROLLER_LOG("CONTROLLER_LOG"),
    CONTROLLER_LAN_LOG("CONTROLLER_LAN_LOG"),
    COMMUNICATION_ACK_POPUP("COMMUNICATION_ACK_POPUP"),
    COMMUNICATION_TEST("COMMUNICATION_TEST"),
    COMMUNICATION_TEST_TX("COMMUNICATION_TEST_TX"),
    AIS_ALARM("AIS_ALARM"),
    VOYAGE("voyage"),
    VOYAGE_SUB("voyageSub"),
    NAV_STATUS("navStatus"),
    OTHER_DETAIL("otherDetail"),
    OTHER_DETAIL_SUB("otherDetailsub"),
    GRAPHIC("GRAPHIC"),
    OWN_DETAIL1("ownDetail1"),
    OWN_DETAIL2("ownDetail2"),
    OWN_TRX("ownTRX"),
    POSN_TIME("POSN&TIME"),
    LIST_SUB("listSub"),
    BEARING("bearing"),
    SORT("sort"),
    NAME("name"),
    DISP("disp"),
    DESTINATION("destination"),
    ETA("eta"),
    DRAUGHT("draught"),
    PERSONS("persons"),
    SHIP_TYPE_US("shipTypeUS"),
    SHIP_TYPE_CARGO("shipTypeCargo"),
    DESTINATION_LOAD("destinationLoad");

    private static final Map<String, ScreenId> BY_CARD_NAME = new HashMap<>();

    static {
        for (ScreenId id : values()) {
            BY_CARD_NAME.put(id.cardName, id);
        }
    }

    private final String cardName;

    ScreenId(String cardName) {
        this.cardName = cardName;
    }

    public String cardName() {
        return cardName;
    }

    public static ScreenId fromCardName(String cardName) {
        ScreenId id = BY_CARD_NAME.get(cardName);
        if (id == null) {
            throw new IllegalArgumentException("Unknown screen card: " + cardName);
        }
        return id;
    }
}
