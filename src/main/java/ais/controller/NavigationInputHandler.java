package ais.controller;

import ais.app.AISMain2;

public class NavigationInputHandler implements ScreenInputHandler {
    private final AISMain2 ui;

    public NavigationInputHandler(AISMain2 ui) {
        this.ui = ui;
    }

    @Override
    public boolean onSub() {
        switch (ui.getCurrentScreen()) {
            case LIST:
                ui.showListSub();
                ui.getListSubView().setSelectedIndex(0);
                return true;
            case OTHER_DETAIL:
                ui.showOtherDetailSub();
                ui.getOtherShipSubMenuView().setSelectedIndex(0);
                return true;
            default:
                return false;
        }
    }

    @Override
    public boolean onClr() {
        switch (ui.getCurrentScreen()) {
            case LIST_SUB:
            case MENU:
                ui.showList();
                ui.clearCurrentShip();
                return true;
            case OTHER_DETAIL_SUB:
                ui.showOtherDetail();
                ui.clearCurrentShip();
                return true;
            default:
                return false;
        }
    }

    @Override
    public boolean onUp() {
        switch (ui.getCurrentScreen()) {
            case LIST:
                ui.selectPrevShip();
                return true;
            case MENU:
                ui.selectPrevMenu();
                return true;
            case LIST_SUB:
                ui.selectPrevListSub();
                return true;
            case BEARING:
                ListSelectionNavigator.moveUp(ui.getBearingView().getList());
                return true;
            case SORT:
                ListSelectionNavigator.moveUp(ui.getSortView().getList());
                return true;
            case NAME:
                ListSelectionNavigator.moveUp(ui.getNameView().getList());
                return true;
            case DISP:
                ListSelectionNavigator.moveUp(ui.getDispView().getList());
                return true;
            case OTHER_DETAIL_SUB:
                ListSelectionNavigator.moveUp(ui.getOtherShipSubMenuView().getList());
                return true;
            case OTHER_DETAIL:
                ui.prevOtherDetailPage();
                return true;
            case OWN_DETAIL1:
                ui.prevOwnDetail1Page();
                return true;
            case OWN_DETAIL2:
                ui.prevOwnDetail2Page();
                return true;
            case OWN_TRX:
                ui.prevOwnTRXPage();
                return true;
            default:
                return false;
        }
    }

    @Override
    public boolean onDown() {
        switch (ui.getCurrentScreen()) {
            case LIST:
                ui.selectNextShip();
                return true;
            case MENU:
                ui.selectNextMenu();
                return true;
            case LIST_SUB:
                ui.selectNextListSub();
                return true;
            case BEARING:
                ListSelectionNavigator.moveDown(ui.getBearingView().getList());
                return true;
            case SORT:
                ListSelectionNavigator.moveDown(ui.getSortView().getList());
                return true;
            case NAME:
                ListSelectionNavigator.moveDown(ui.getNameView().getList());
                return true;
            case DISP:
                ListSelectionNavigator.moveDown(ui.getDispView().getList());
                return true;
            case OTHER_DETAIL_SUB:
                ListSelectionNavigator.moveDown(ui.getOtherShipSubMenuView().getList());
                return true;
            case OTHER_DETAIL:
                ui.nextOtherDetailPage();
                return true;
            case OWN_DETAIL1:
                ui.nextOwnDetail1Page();
                return true;
            case OWN_DETAIL2:
                ui.nextOwnDetail2Page();
                return true;
            case OWN_TRX:
                ui.nextOwnTRXPage();
                return true;
            default:
                return false;
        }
    }

    @Override
    public boolean onDisp() {
        switch (ui.getCurrentScreen()) {
            case LIST:
                showSelectedShipGraphic();
                return true;
            case GRAPHIC:
                ui.showOwnDetail1();
                return true;
            case OWN_DETAIL1:
                ui.showOwnDetail2();
                return true;
            case OWN_DETAIL2:
                ui.showOwnTRX();
                return true;
            case OWN_TRX:
                ui.showPosnTime();
                return true;
            case POSN_TIME:
                ui.showList();
                return true;
            default:
                return false;
        }
    }

    @Override
    public boolean onEnter() {
        switch (ui.getCurrentScreen()) {
            case MENU:
                ui.enterMenu();
                return true;
            case LIST_SUB:
                ui.enterListSub();
                return true;
            case LIST:
                ui.enterShipList();
                return true;
            case OTHER_DETAIL_SUB:
                ui.enterOtherDetailSub();
                return true;
            default:
                return false;
        }
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
