package ais.controller;

import ais.app.AISMain2;
import ais.app.ScreenId;
import java.util.EnumMap;
import java.util.Map;
import java.util.function.Function;

public class InputController {

    private final AISMain2 ui;
    private final Map<ScreenId, ScreenInputHandler> handlers = new EnumMap<>(ScreenId.class);

    public InputController(AISMain2 ui) {
        this.ui = ui;
        registerHandlers();
    }

    public void onSubPressed() {
        dispatch(ScreenInputHandler::onSub);
    }

    public void onMenuPressed() {
        ui.showMenu();
        ui.getMenuView().setSelectedIndex(0);
    }

    public void onDispPressed() {
        dispatch(ScreenInputHandler::onDisp);
    }

    public void onClrPressed() {
        dispatch(ScreenInputHandler::onClr);
    }

    public void onUpPressed() {
        dispatch(ScreenInputHandler::onUp);
    }

    public void onDownPressed() {
        dispatch(ScreenInputHandler::onDown);
    }

    public void onLeftPressed() {
        dispatch(ScreenInputHandler::onLeft);
    }

    public void onRightPressed() {
        dispatch(ScreenInputHandler::onRight);
    }

    public void onEnterPressed() {
        dispatch(ScreenInputHandler::onEnter);
    }

    private void registerHandlers() {
        register(new NavigationInputHandler(ui),
                ScreenId.LIST,
                ScreenId.MENU,
                ScreenId.LIST_SUB,
                ScreenId.BEARING,
                ScreenId.SORT,
                ScreenId.NAME,
                ScreenId.DISP,
                ScreenId.OTHER_DETAIL,
                ScreenId.OTHER_DETAIL_SUB,
                ScreenId.GRAPHIC,
                ScreenId.OWN_DETAIL1,
                ScreenId.OWN_DETAIL2,
                ScreenId.OWN_TRX,
                ScreenId.POSN_TIME);

        register(new MessageInputHandler(ui),
                ScreenId.MESSAGE,
                ScreenId.EDIT_AND_TX,
                ScreenId.EDIT_AND_TX_SUB,
                ScreenId.TEXT_EDIT,
                ScreenId.EDIT_AND_TX_TX_CONFIRM,
                ScreenId.EDIT_AND_TX_TX_RESULT,
                ScreenId.TX_TRAY,
                ScreenId.TX_TRAY_TEXT,
                ScreenId.TX_TRAY_SUB,
                ScreenId.TX_TRAY_DETAIL,
                ScreenId.RX_TRAY,
                ScreenId.RX_TRAY_TEXT,
                ScreenId.RX_TRAY_SUB,
                ScreenId.RX_TRAY_DETAIL);

        register(new InterrogationInputHandler(ui),
                ScreenId.INTERROGATION,
                ScreenId.INTERROGATION_SUB,
                ScreenId.INTERROGATION_TX_RESULT);

        register(new MaintenanceInputHandler(ui),
                ScreenId.MAINTENANCE_MENU,
                ScreenId.SELF_DIAGNOSIS,
                ScreenId.TRANSPONDER_LOG,
                ScreenId.CONTROLLER_LOG,
                ScreenId.CONTROLLER_LAN_LOG,
                ScreenId.COMMUNICATION_TEST,
                ScreenId.COMMUNICATION_ACK_POPUP,
                ScreenId.AIS_ALARM);

        register(new VoyageInputHandler(ui),
                ScreenId.VOYAGE,
                ScreenId.VOYAGE_SUB,
                ScreenId.NAV_STATUS,
                ScreenId.DESTINATION,
                ScreenId.DESTINATION_LOAD,
                ScreenId.ETA,
                ScreenId.DRAUGHT,
                ScreenId.PERSONS,
                ScreenId.SHIP_TYPE_US,
                ScreenId.SHIP_TYPE_CARGO);
    }

    private void register(ScreenInputHandler handler, ScreenId... screenIds) {
        for (ScreenId screenId : screenIds) {
            handlers.put(screenId, handler);
        }
    }

    private boolean dispatch(Function<ScreenInputHandler, Boolean> action) {
        ScreenInputHandler handler = handlers.get(ui.getCurrentScreen());
        return handler != null && Boolean.TRUE.equals(action.apply(handler));
    }

}
