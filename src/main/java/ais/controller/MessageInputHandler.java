package ais.controller;

import ais.app.AISMain2;
import ais.app.ScreenId;
import ais.model.EditAndTxModel;

public class MessageInputHandler implements ScreenInputHandler {

    private final AISMain2 ui;

    public MessageInputHandler(AISMain2 ui) {
        this.ui = ui;
    }

    @Override
    public boolean onSub() {
        switch (ui.getCurrentScreen()) {
            case EDIT_AND_TX:
                ui.showEditAndTxSubMenu();
                ui.getEditAndTxSubMenuView().resetCursor();
                return true;
            case TEXT_EDIT:
                ui.showEditAndTxSubMenu();
                return true;
            case TX_TRAY:
            case TX_TRAY_TEXT:
                ui.showTxTraySubMenu();
                ui.getTxTraySubMenuView().setSelectedIndex(0);
                return true;
            case RX_TRAY:
            case RX_TRAY_TEXT:
                ui.showRxTraySubMenu();
                ui.getRxTraySubMenuView().setSelectedIndex(0);
                return true;
            default:
                return false;
        }
    }

    @Override
    public boolean onClr() {
        switch (ui.getCurrentScreen()) {
            case MESSAGE:
                ui.showMenu();
                ui.getMenuView().setSelectedIndex(1);
                return true;
            case EDIT_AND_TX:
                boolean exit = ui.getEditAndTxModel().onClr();
                ui.getEditAndTxView().refresh();
                if (exit) {
                    ui.showMessageMenu();
                }
                return true;
            case EDIT_AND_TX_SUB:
            case TEXT_EDIT:
                ui.showEditAndTx();
                return true;
            case TX_TRAY_SUB:
            case TX_TRAY_TEXT:
                ui.showTxTray();
                return true;
            case TX_TRAY_DETAIL:
                ui.showTxTraySubMenu();
                return true;
            case RX_TRAY_SUB:
            case RX_TRAY_TEXT:
                ui.showRxTray();
                return true;
            case RX_TRAY_DETAIL:
                ui.showRxTraySubMenu();
                return true;
            default:
                return false;
        }
    }

    @Override
    public boolean onUp() {
        switch (ui.getCurrentScreen()) {
            case MESSAGE:
                ListSelectionNavigator.moveUp(ui.getMessageMenuView().getList());
                return true;
            case EDIT_AND_TX:
                adjustEditAndTx(true);
                return true;
            case EDIT_AND_TX_SUB:
                ListSelectionNavigator.moveUp(ui.getEditAndTxSubMenuView().getList());
                return true;
            case TEXT_EDIT:
                ui.getTextEditView().moveUp();
                return true;
            case TX_TRAY:
                ListSelectionNavigator.moveUp(ui.getTxTrayListView().getList());
                return true;
            case TX_TRAY_SUB:
                ListSelectionNavigator.moveUp(ui.getTxTraySubMenuView().getList());
                return true;
            case RX_TRAY:
                ListSelectionNavigator.moveUp(ui.getRxTrayListView().getList());
                return true;
            case RX_TRAY_SUB:
                ListSelectionNavigator.moveUp(ui.getRxTraySubMenuView().getList());
                return true;
            default:
                return false;
        }
    }

    @Override
    public boolean onDown() {
        switch (ui.getCurrentScreen()) {
            case MESSAGE:
                ListSelectionNavigator.moveDown(ui.getMessageMenuView().getList());
                return true;
            case EDIT_AND_TX:
                adjustEditAndTx(false);
                return true;
            case EDIT_AND_TX_SUB:
                ListSelectionNavigator.moveDown(ui.getEditAndTxSubMenuView().getList());
                return true;
            case TEXT_EDIT:
                ui.getTextEditView().moveDown();
                return true;
            case TX_TRAY:
                ListSelectionNavigator.moveDown(ui.getTxTrayListView().getList());
                return true;
            case TX_TRAY_SUB:
                ListSelectionNavigator.moveDown(ui.getTxTraySubMenuView().getList());
                return true;
            case RX_TRAY:
                ListSelectionNavigator.moveDown(ui.getRxTrayListView().getList());
                return true;
            case RX_TRAY_SUB:
                ListSelectionNavigator.moveDown(ui.getRxTraySubMenuView().getList());
                return true;
            default:
                return false;
        }
    }

    @Override
    public boolean onLeft() {
        switch (ui.getCurrentScreen()) {
            case EDIT_AND_TX:
                ui.getEditAndTxModel().moveMmsiLeft();
                ui.getEditAndTxView().refresh();
                return true;
            case TEXT_EDIT:
                ui.getTextEditView().moveLeft();
                return true;
            case EDIT_AND_TX_TX_CONFIRM:
                ui.getTxConfirmView().selectPrev();
                return true;
            default:
                return false;
        }
    }

    @Override
    public boolean onRight() {
        switch (ui.getCurrentScreen()) {
            case EDIT_AND_TX:
                ui.getEditAndTxModel().moveMmsiRight();
                ui.getEditAndTxView().refresh();
                return true;
            case TEXT_EDIT:
                ui.getTextEditView().moveRight();
                return true;
            case EDIT_AND_TX_TX_CONFIRM:
                ui.getTxConfirmView().selectNext();
                return true;
            default:
                return false;
        }
    }

    @Override
    public boolean onEnter() {
        switch (ui.getCurrentScreen()) {
            case MESSAGE:
                enterMessageMenu();
                return true;
            case EDIT_AND_TX:
                ui.getEditAndTxModel().enter();
                ui.getEditAndTxView().refresh();
                return true;
            case EDIT_AND_TX_SUB:
                enterEditAndTxSubMenu();
                return true;
            case TEXT_EDIT:
                ui.getTextEditView().pressEnter();
                return true;
            case EDIT_AND_TX_TX_RESULT:
                ui.showEditAndTx();
                return true;
            case EDIT_AND_TX_TX_CONFIRM:
                if (ui.getTxConfirmView().getSelectedIndex() == 0) {
                    ui.executePseudoTx();
                } else {
                    ui.showEditAndTx();
                }
                return true;
            case TX_TRAY:
                ui.getMessageWorkflow().enterTxTrayList();
                return true;
            case TX_TRAY_SUB:
                ui.getMessageWorkflow().enterTxTraySubMenu();
                return true;
            case RX_TRAY:
                ui.getMessageWorkflow().enterRxTrayList();
                return true;
            case RX_TRAY_SUB:
                ui.getMessageWorkflow().enterRxTraySubMenu();
                return true;
            default:
                return false;
        }
    }

    private void enterMessageMenu() {
        switch (ui.getMessageMenuView().getSelectedIndex()) {
            case 0:
                ui.showEditAndTx();
                break;
            case 1:
                ui.showTxTray();
                break;
            case 2:
                ui.showRxTray();
                break;
            case 3:
                ui.showInterrogationNew();
                break;
            case 4:
                ui.showLongRange();
                break;
            default:
                break;
        }
    }

    private void enterEditAndTxSubMenu() {
        switch (ui.getEditAndTxSubMenuView().getSelectedIndex()) {
            case 0:
                ui.showTxConfirm();
                break;
            case 1:
                ui.showTextEdit();
                break;
            case 2:
                ui.saveCurrentTxMessage();
                break;
            case 3:
                ui.showEditAndTx();
                break;
            default:
                break;
        }
    }

    private void adjustEditAndTx(boolean up) {
        EditAndTxModel model = ui.getEditAndTxModel();
        if (model.getEditMode() == EditAndTxModel.EditMode.ROW_SELECT) {
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
        ui.getEditAndTxView().refresh();
    }

}
