package ais.controller;

import ais.app.AISMain2;
import ais.app.ScreenId;
import ais.model.InterrogationModel;

public class InterrogationInputHandler implements ScreenInputHandler {

    private final AISMain2 ui;

    public InterrogationInputHandler(AISMain2 ui) {
        this.ui = ui;
    }

    @Override
    public boolean onSub() {
        switch (ui.getCurrentScreen()) {
            case INTERROGATION:
                ui.showInterrogationSUB();
                ui.getInterrogationSubMenuView().resetCursor();
                return true;
            case INTERROGATION_SUB:
                ui.showInterrogation();
                return true;
            default:
                return false;
        }
    }

    @Override
    public boolean onClr() {
        switch (ui.getCurrentScreen()) {
            case INTERROGATION:
                return clearInterrogation();
            case INTERROGATION_SUB:
                ui.showInterrogation();
                return true;
            default:
                return false;
        }
    }

    @Override
    public boolean onUp() {
        switch (ui.getCurrentScreen()) {
            case INTERROGATION:
                adjustInterrogation(true);
                return true;
            case INTERROGATION_SUB:
                ListSelectionNavigator.moveUp(ui.getInterrogationSubMenuView().getList());
                return true;
            default:
                return false;
        }
    }

    @Override
    public boolean onDown() {
        switch (ui.getCurrentScreen()) {
            case INTERROGATION:
                adjustInterrogation(false);
                return true;
            case INTERROGATION_SUB:
                ListSelectionNavigator.moveDown(ui.getInterrogationSubMenuView().getList());
                return true;
            default:
                return false;
        }
    }

    @Override
    public boolean onEnter() {
        switch (ui.getCurrentScreen()) {
            case INTERROGATION:
                ui.getInterrogationModel().enter();
                ui.getInterrogationView().refresh();
                return true;
            case INTERROGATION_SUB:
                enterSubMenu();
                return true;
            case INTERROGATION_TX_RESULT:
                enterTxResult();
                return true;
            default:
                return false;
        }
    }

    private boolean clearInterrogation() {
        InterrogationModel model = ui.getInterrogationModel();
        boolean cleared = model.onClr();
        if (cleared) {
            ui.showMessageMenu();
            ui.getMenuView().setSelectedIndex(1);
        } else {
            ui.getInterrogationView().refresh();
        }
        return true;
    }

    private void adjustInterrogation(boolean up) {
        InterrogationModel model = ui.getInterrogationModel();
        if (model.getEditMode() == InterrogationModel.EditMode.ROW_SELECT) {
            if (up) {
                model.moveRowUp();
            } else {
                model.moveRowDown();
            }
        } else if (up) {
            model.incrementValue();
        } else {
            model.decrementValue();
        }
        ui.getInterrogationView().refresh();
    }

    private void enterSubMenu() {
        switch (ui.getInterrogationSubMenuView().getSelectedIndex()) {
            case 0:
                ui.executeInterrogationTx();
                break;
            case 2:
                ui.getInterrogationModel().clearCursor();
                ui.showInterrogation();
                break;
            case 3:
                ui.showInterrogation();
                break;
            default:
                break;
        }
    }

    private void enterTxResult() {
        if (ui.getInterrogationModel().getTxState() == InterrogationModel.TxState.OK) {
            ui.showInterrogationResponse();
        } else {
            ui.getInterrogationView().refresh();
            ui.showInterrogation();
        }
    }

}
