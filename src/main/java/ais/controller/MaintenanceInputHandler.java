package ais.controller;

import ais.app.AISMain2;
import ais.app.ScreenId;
import ais.model.AISAlarmModel;
import ais.model.CommunicationTestModel;
import ais.model.SelfDiagnosisModel;

public class MaintenanceInputHandler implements ScreenInputHandler {

    private final AISMain2 ui;

    public MaintenanceInputHandler(AISMain2 ui) {
        this.ui = ui;
    }

    @Override
    public boolean onSub() {
        if (ui.getCurrentScreen() != ScreenId.AIS_ALARM) {
            return false;
        }

        ui.getMaintenanceWorkflow().toggleAisAlarmDisplay();
        return true;
    }

    @Override
    public boolean onClr() {
        switch (ui.getCurrentScreen()) {
            case MAINTENANCE_MENU:
                ui.showMenu();
                return true;
            case SELF_DIAGNOSIS:
                return clearSelfDiagnosis();
            case TRANSPONDER_LOG:
            case CONTROLLER_LOG:
            case CONTROLLER_LAN_LOG:
                ui.showSelfDiagnosis();
                return true;
            case COMMUNICATION_TEST:
            case AIS_ALARM:
                ui.showMaintenance();
                return true;
            default:
                return false;
        }
    }

    @Override
    public boolean onUp() {
        switch (ui.getCurrentScreen()) {
            case MAINTENANCE_MENU:
                ListSelectionNavigator.moveUp(ui.getMaintenanceMenuView().getList());
                return true;
            case SELF_DIAGNOSIS:
                ui.getSelfDiagnosisModel().moveUp();
                ui.getSelfDiagnosisView().update(ui.getSelfDiagnosisModel());
                return true;
            case TRANSPONDER_LOG:
            case CONTROLLER_LOG:
            case CONTROLLER_LAN_LOG:
                ui.getDiagnosisLogModel().prevPage();
                return true;
            case COMMUNICATION_TEST:
                adjustCommunicationTest(true);
                return true;
            case AIS_ALARM:
                AISAlarmModel alarmModel = ui.getAISAlarmModel();
                alarmModel.prevPage();
                ui.getAISAlarmCurrentView().refresh(alarmModel);
                return true;
            default:
                return false;
        }
    }

    @Override
    public boolean onDown() {
        switch (ui.getCurrentScreen()) {
            case MAINTENANCE_MENU:
                ListSelectionNavigator.moveDown(ui.getMaintenanceMenuView().getList());
                return true;
            case SELF_DIAGNOSIS:
                ui.getSelfDiagnosisModel().moveDown();
                ui.getSelfDiagnosisView().update(ui.getSelfDiagnosisModel());
                return true;
            case TRANSPONDER_LOG:
            case CONTROLLER_LOG:
            case CONTROLLER_LAN_LOG:
                ui.getDiagnosisLogModel().nextPage();
                return true;
            case COMMUNICATION_TEST:
                adjustCommunicationTest(false);
                ui.getCommunicationTestView().requestFocus();
                return true;
            case AIS_ALARM:
                AISAlarmModel alarmModel = ui.getAISAlarmModel();
                alarmModel.nextPage();
                ui.getAISAlarmCurrentView().refresh(alarmModel);
                return true;
            default:
                return false;
        }
    }

    @Override
    public boolean onEnter() {
        switch (ui.getCurrentScreen()) {
            case MAINTENANCE_MENU:
                ui.getMaintenanceWorkflow().enterMaintenanceMenu();
                return true;
            case SELF_DIAGNOSIS:
                ui.getMaintenanceWorkflow().enterSelfDiagnosis();
                return true;
            case COMMUNICATION_TEST:
                ui.getMaintenanceWorkflow().enterCommunicationTest();
                return true;
            case COMMUNICATION_ACK_POPUP:
                ui.getMaintenanceWorkflow().enterCommunicationAckPopup();
                return true;
            default:
                return false;
        }
    }

    private boolean clearSelfDiagnosis() {
        SelfDiagnosisModel model = ui.getSelfDiagnosisModel();
        boolean exit = model.onClr();
        ui.getSelfDiagnosisView().update(model);
        if (exit) {
            ui.showMaintenance();
        }
        return true;
    }

    private void adjustCommunicationTest(boolean up) {
        CommunicationTestModel model = ui.getCommunicationTestModel();
        if (model.getCurrentRow() == CommunicationTestModel.Row.ACTION) {
            if (up) {
                model.incrementValue();
            } else {
                model.decrementValue();
            }
        } else if (up) {
            model.moveRowUp();
        } else {
            model.moveRowDown();
        }
        ui.getCommunicationTestView().refresh(model);
    }

}
