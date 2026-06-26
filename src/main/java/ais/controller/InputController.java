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
        if (dispatch(ScreenInputHandler::onSub)) {
            return;
        }

        switch (ui.getCurrentScreen()) {
            case LIST:
                ui.showListSub();
                ui.getListSubView().setSelectedIndex(0);
                break;
            case OTHER_DETAIL:
                ui.showOtherDetailSub();
                ui.getOtherShipSubMenuView().setSelectedIndex(0);
                break;
            default:
                break;
        }
    }

    public void onMenuPressed() {
        ui.showMenu();
        ui.getMenuView().setSelectedIndex(0);
    }

    public void onDispPressed() {
        switch (ui.getCurrentScreen()) {
            case LIST:
                showSelectedShipGraphic();
                break;
            case GRAPHIC:
                ui.showOwnDetail1();
                break;
            case OWN_DETAIL1:
                ui.showOwnDetail2();
                break;
            case OWN_DETAIL2:
                ui.showOwnTRX();
                break;
            case OWN_TRX:
                ui.showPosnTime();
                break;
            case POSN_TIME:
                ui.showList();
                break;
            default:
                break;
        }
    }

    public void onClrPressed() {
        if (dispatch(ScreenInputHandler::onClr)) {
            ui.clearCurrentShip();
            return;
        }

        switch (ui.getCurrentScreen()) {
            case LIST_SUB:
            case MENU:
                ui.showList();
                break;
            case OTHER_DETAIL_SUB:
                ui.showOtherDetail();
                break;
            default:
                break;
        }
        ui.clearCurrentShip();
    }

    public void onUpPressed() {
        if (dispatch(ScreenInputHandler::onUp)) {
            return;
        }

        switch (ui.getCurrentScreen()) {
            case LIST:
                ui.selectPrevShip();
                break;
            case MENU:
                ui.selectPrevMenu();
                break;
            case LIST_SUB:
                ui.selectPrevListSub();
                break;
            case BEARING:
                ListSelectionNavigator.moveUp(ui.getBearingView().getList());
                break;
            case SORT:
                ListSelectionNavigator.moveUp(ui.getSortView().getList());
                break;
            case NAME:
                ListSelectionNavigator.moveUp(ui.getNameView().getList());
                break;
            case DISP:
                ListSelectionNavigator.moveUp(ui.getDispView().getList());
                break;
            case OTHER_DETAIL_SUB:
                ListSelectionNavigator.moveUp(ui.getOtherShipSubMenuView().getList());
                break;
            case OTHER_DETAIL:
                ui.prevOtherDetailPage();
                break;
            case OWN_DETAIL1:
                ui.prevOwnDetail1Page();
                break;
            case OWN_DETAIL2:
                ui.prevOwnDetail2Page();
                break;
            case OWN_TRX:
                ui.prevOwnTRXPage();
                break;
            default:
                break;
        }
    }

    public void onDownPressed() {
        if (dispatch(ScreenInputHandler::onDown)) {
            return;
        }

        switch (ui.getCurrentScreen()) {
            case LIST:
                ui.selectNextShip();
                break;
            case MENU:
                ui.selectNextMenu();
                break;
            case LIST_SUB:
                ui.selectNextListSub();
                break;
            case BEARING:
                ListSelectionNavigator.moveDown(ui.getBearingView().getList());
                break;
            case SORT:
                ListSelectionNavigator.moveDown(ui.getSortView().getList());
                break;
            case NAME:
                ListSelectionNavigator.moveDown(ui.getNameView().getList());
                break;
            case DISP:
                ListSelectionNavigator.moveDown(ui.getDispView().getList());
                break;
            case OTHER_DETAIL_SUB:
                ListSelectionNavigator.moveDown(ui.getOtherShipSubMenuView().getList());
                break;
            case OTHER_DETAIL:
                ui.nextOtherDetailPage();
                break;
            case OWN_DETAIL1:
                ui.nextOwnDetail1Page();
                break;
            case OWN_DETAIL2:
                ui.nextOwnDetail2Page();
                break;
            case OWN_TRX:
                ui.nextOwnTRXPage();
                break;
            default:
                break;
        }
    }

    public void onLeftPressed() {
        dispatch(ScreenInputHandler::onLeft);
    }

    public void onRightPressed() {
        dispatch(ScreenInputHandler::onRight);
    }

    public void onEnterPressed() {
        if (dispatch(ScreenInputHandler::onEnter)) {
            return;
        }

        switch (ui.getCurrentScreen()) {
            case MENU:
                ui.enterMenu();
                break;
            case LIST_SUB:
                ui.enterListSub();
                break;
            case LIST:
                ui.enterShipList();
                break;
            case OTHER_DETAIL_SUB:
                ui.enterOtherDetailSub();
                break;
            default:
                break;
        }
    }

    private void registerHandlers() {
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

    private void showSelectedShipGraphic() {
        int row = ui.getShipListView().getTable().getSelectedRow();
        if (row < 0) {
            return;
        }

        int mmsi = ui.getShipListView().getSelectedMmsi();
        if (mmsi >= 0) {
            ui.showGraphic(mmsi);
        }
    }

}
