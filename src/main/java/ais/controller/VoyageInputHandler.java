package ais.controller;

import ais.app.AISMain2;
import ais.app.ScreenId;

public class VoyageInputHandler implements ScreenInputHandler {

    private final AISMain2 ui;

    public VoyageInputHandler(AISMain2 ui) {
        this.ui = ui;
    }

    @Override
    public boolean onSub() {
        if (ui.getCurrentScreen() != ScreenId.VOYAGE) {
            return false;
        }

        ui.showVoyageSub();
        ui.getVoyageSubView().setSelectedIndex(0);
        return true;
    }

    @Override
    public boolean onClr() {
        switch (ui.getCurrentScreen()) {
            case VOYAGE:
                ui.showList();
                return true;
            case VOYAGE_SUB:
                ui.discardVoyageSession();
                ui.redrawVoyage();
                return true;
            case DESTINATION_LOAD:
                ui.redrawVoyage();
                return true;
            default:
                return false;
        }
    }

    @Override
    public boolean onUp() {
        switch (ui.getCurrentScreen()) {
            case VOYAGE:
                ui.selectPrevVoyageItem();
                return true;
            case VOYAGE_SUB:
                ListSelectionNavigator.moveUp(ui.getVoyageSubView().getList());
                return true;
            case NAV_STATUS:
                ListSelectionNavigator.moveUp(ui.getNavStatusView().getList());
                return true;
            case ETA:
                ui.getETAInputView().increment();
                return true;
            case DESTINATION:
                ui.getDestinationInputView().moveUp();
                return true;
            case DESTINATION_LOAD:
                ui.getDestinationLoadView().selectPrev();
                return true;
            case DRAUGHT:
                ui.getDraughtInputView().increment();
                return true;
            case PERSONS:
                ui.getPersonsInputView().increment();
                return true;
            case SHIP_TYPE_US:
                ui.getShipTypeUSInputView().toggle();
                return true;
            case SHIP_TYPE_CARGO:
                ui.getShipTypeCargoView().increment();
                return true;
            default:
                return false;
        }
    }

    @Override
    public boolean onDown() {
        switch (ui.getCurrentScreen()) {
            case VOYAGE:
                ui.selectNextVoyageItem();
                return true;
            case VOYAGE_SUB:
                ListSelectionNavigator.moveDown(ui.getVoyageSubView().getList());
                return true;
            case NAV_STATUS:
                ListSelectionNavigator.moveDown(ui.getNavStatusView().getList());
                return true;
            case DESTINATION:
                ui.getDestinationInputView().moveDown();
                return true;
            case DESTINATION_LOAD:
                ui.getDestinationLoadView().selectNext();
                return true;
            case DRAUGHT:
                ui.getDraughtInputView().decrement();
                return true;
            case ETA:
                ui.getETAInputView().decrement();
                return true;
            case PERSONS:
                ui.getPersonsInputView().decrement();
                return true;
            case SHIP_TYPE_US:
                ui.getShipTypeUSInputView().toggle();
                return true;
            case SHIP_TYPE_CARGO:
                ui.getShipTypeCargoView().decrement();
                return true;
            default:
                return false;
        }
    }

    @Override
    public boolean onLeft() {
        switch (ui.getCurrentScreen()) {
            case DESTINATION:
                ui.getDestinationInputView().moveLeft();
                return true;
            case ETA:
                ui.getETAInputView().moveCursorLeft();
                return true;
            case DRAUGHT:
                ui.getDraughtInputView().moveCursorLeft();
                return true;
            case PERSONS:
                ui.getPersonsInputView().moveCursorLeft();
                return true;
            default:
                return false;
        }
    }

    @Override
    public boolean onRight() {
        switch (ui.getCurrentScreen()) {
            case DESTINATION:
                ui.getDestinationInputView().moveRight();
                return true;
            case ETA:
                ui.getETAInputView().moveCursorRight();
                return true;
            case DRAUGHT:
                ui.getDraughtInputView().moveCursorRight();
                return true;
            case PERSONS:
                ui.getPersonsInputView().moveCursorRight();
                return true;
            default:
                return false;
        }
    }

    @Override
    public boolean onEnter() {
        switch (ui.getCurrentScreen()) {
            case VOYAGE:
                ui.getVoyageWorkflow().enterVoyage();
                return true;
            case NAV_STATUS:
                ui.getVoyageWorkflow().enterNavStatus();
                return true;
            case DESTINATION:
                ui.getVoyageWorkflow().enterDestination();
                return true;
            case DESTINATION_LOAD:
                ui.getVoyageWorkflow().enterDestinationLoad();
                return true;
            case ETA:
                ui.getVoyageWorkflow().enterEta();
                return true;
            case DRAUGHT:
                ui.getVoyageWorkflow().enterDraught();
                return true;
            case PERSONS:
                ui.getVoyageWorkflow().enterPersons();
                return true;
            case SHIP_TYPE_US:
                ui.getVoyageWorkflow().enterShipTypeUS();
                return true;
            case SHIP_TYPE_CARGO:
                ui.getVoyageWorkflow().enterShipTypeCargo();
                return true;
            case VOYAGE_SUB:
                ui.getVoyageWorkflow().enterVoyageSub();
                return true;
            default:
                return false;
        }
    }

}
