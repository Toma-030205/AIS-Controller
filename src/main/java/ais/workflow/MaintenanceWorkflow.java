package ais.workflow;

import ais.app.AISMain2;
import ais.app.ScreenId;
import ais.model.AISAlarmModel;
import ais.model.CommunicationTestModel;
import ais.model.SelfDiagnosisModel;
import ais.view.CommunicationTestView;

public class MaintenanceWorkflow {

    private final AISMain2 ui;

    public MaintenanceWorkflow(AISMain2 ui) {
        this.ui = ui;
    }

    public void showMaintenance() {
        ui.getMaintenanceMenuView().setSelectedIndex(0);
        ui.navigateTo(ScreenId.MAINTENANCE_MENU);
    }

    public void showSelfDiagnosis() {
        ui.getSelfDiagnosisModel().reset();
        ui.getSelfDiagnosisView().update(ui.getSelfDiagnosisModel());
        ui.navigateTo(ScreenId.SELF_DIAGNOSIS);
    }

    public void showTransponderLog() {
        ui.navigateTo(ScreenId.TRANSPONDER_LOG);
    }

    public void showControllerLog() {
        ui.navigateTo(ScreenId.CONTROLLER_LOG);
    }

    public void showControllerLanLog() {
        ui.navigateTo(ScreenId.CONTROLLER_LAN_LOG);
    }

    public void showCommunicationTest() {
        ui.getCommunicationTestModel().reset();
        ui.navigateTo(ScreenId.COMMUNICATION_TEST);
    }

    public void showCommunicationTestTx() {
        ui.navigateTo(ScreenId.COMMUNICATION_TEST_TX);
    }

    public void showAISAlarmCurrent() {
        ui.navigateTo(ScreenId.AIS_ALARM);
    }

    public void showAISAlarmHistory() {
        ui.navigateTo(ScreenId.AIS_ALARM);
    }

    public void toggleAisAlarmDisplay() {
        AISAlarmModel model = ui.getAISAlarmModel();

        if (model.getDisplayMode() == AISAlarmModel.DisplayMode.CURRENT) {
            model.setDisplayMode(AISAlarmModel.DisplayMode.HISTORY);
            ui.getAISAlarmCurrentView().setHeaderText("ALARM HISTORY");
        } else {
            model.setDisplayMode(AISAlarmModel.DisplayMode.CURRENT);
            ui.getAISAlarmCurrentView().setHeaderText("AIS ALARM");
        }

        ui.getAISAlarmCurrentView().refresh(model);
    }

    public void enterMaintenanceMenu() {
        int selected = ui.getMaintenanceMenuView().getSelectedIndex();
        switch (selected) {
            case 0:
                showSelfDiagnosis();
                break;
            case 1:
                showCommunicationTest();
                refreshCommunicationTestCursor();
                break;
            case 2:
                showAisAlarmWithDummyData();
                break;
            default:
                break;
        }
    }

    public void enterSelfDiagnosis() {
        SelfDiagnosisModel model = ui.getSelfDiagnosisModel();
        model.enter();
        ui.getSelfDiagnosisView().update(model);

        SelfDiagnosisModel.Row log = model.consumeRequestedLogRow();
        if (log == null) {
            return;
        }

        switch (log) {
            case TRANSPONDER_LOG:
                showTransponderLog();
                break;
            case CONTROLLER_LOG:
                showControllerLog();
                break;
            case CONTROLLER_LAN_LOG:
                showControllerLanLog();
                break;
            default:
                break;
        }
    }

    public void enterCommunicationTest() {
        CommunicationTestModel model = ui.getCommunicationTestModel();
        if (model.getCurrentRow() != CommunicationTestModel.Row.ACTION) {
            return;
        }

        if (model.getAction() == CommunicationTestModel.Action.TX) {
            executeCommunicationTest();
        } else {
            showMaintenance();
        }
    }

    public void enterCommunicationAckPopup() {
        CommunicationTestModel model = ui.getCommunicationTestModel();
        showCommunicationTest();
        ui.getCommunicationTestView().refresh(model);
        syncCommunicationTestActionCursor(model, ui.getCommunicationTestView());
        ui.getCommunicationTestView().requestFocus();
    }

    public void executeCommunicationTest() {
        ui.getCommunicationTestModel().startTest();
        ui.navigateTo(ScreenId.COMMUNICATION_TEST_TX);

        javax.swing.Timer timer = new javax.swing.Timer(1500, e -> {
            boolean ok = pseudoAck();
            ui.getCommunicationTestModel().setResult(ok);
            ui.getCommunicationAckPopupView().setAckResult(ok);
            ui.navigateTo(ScreenId.COMMUNICATION_ACK_POPUP);
        });
        timer.setRepeats(false);
        timer.start();
    }

    private void showAisAlarmWithDummyData() {
        ui.getAISAlarmModel().setDisplayMode(AISAlarmModel.DisplayMode.CURRENT);

        AISAlarmModel dummy = AISAlarmModel.createWithDummyData();
        for (AISAlarmModel.AlarmEntry entry : dummy.getDisplayEntries()) {
            ui.getAISAlarmModel().add(entry);
        }

        ui.getAISAlarmCurrentView().setHeaderText("AIS ALARM");
        ui.getAISAlarmCurrentView().refresh(ui.getAISAlarmModel());
        showAISAlarmCurrent();
    }

    private void refreshCommunicationTestCursor() {
        CommunicationTestModel model = ui.getCommunicationTestModel();
        CommunicationTestView view = ui.getCommunicationTestView();
        syncCommunicationTestActionCursor(model, view);
        view.refresh(model);
        view.requestFocus();
    }

    private void syncCommunicationTestActionCursor(
            CommunicationTestModel model,
            CommunicationTestView view
    ) {
        if (model.getAction() == CommunicationTestModel.Action.TX) {
            view.setSelectedAction(0);
        } else {
            view.setSelectedAction(1);
        }
    }

    private boolean pseudoAck() {
        return Math.random() > 0.2;
    }
}
