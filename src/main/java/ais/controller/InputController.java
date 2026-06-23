package ais.controller;

import ais.app.AISMain2;
import ais.model.AISAlarmModel;
import ais.model.CommunicationTestModel;
import ais.model.DraughtInputModel;
import ais.model.EditAndTxModel;
import ais.model.ETAInputModel;
import ais.model.InterrogationModel;
import ais.model.NavStatus;
import ais.model.OwnShipInfo;
import ais.model.PersonsInputModel;
import ais.model.SelfDiagnosisModel;
import ais.model.ShipTypeCargoInputModel;
import ais.model.ShipTypeUSInputModel;
import ais.view.CommunicationTestView;
import ais.view.InterrogationView;
import ais.view.VoyageView;
import javax.swing.JList;
import javax.swing.SwingUtilities;



public class InputController {

    private final AISMain2 ui;

    public InputController(AISMain2 ui) {
        this.ui = ui;
    }

    /* ===== SUBボタン ===== */
    public void onSubPressed() {
        switch (ui.getCurrentScreen()) {
            case LIST:
                ui.showListSub();
                ui.getListSubView().setSelectedIndex(0);
                break;

            case OTHER_DETAIL:
                ui.showOtherDetailSub();
                ui.getOtherShipSubMenuView().setSelectedIndex(0);
                break;

            case VOYAGE:
                ui.showVoyageSub();
                ui.getVoyageSubView().setSelectedIndex(0);
                break;
            
            case EDIT_AND_TX:
                ui.showEditAndTxSubMenu();
                ui.getEditAndTxSubMenuView().resetCursor();
                break;
            
            case TEXT_EDIT:
                ui.showEditAndTxSubMenu();
                break;
            
            case TX_TRAY:
            case TX_TRAY_TEXT:
                ui.showTxTraySubMenu();
                ui.getTxTraySubMenuView().setSelectedIndex(0);
                break;

            case RX_TRAY:
            case RX_TRAY_TEXT:
                ui.showRxTraySubMenu();
                ui.getRxTraySubMenuView().setSelectedIndex(0);
                break;

            case INTERROGATION:
                ui.showInterrogationSUB();
                ui.getInterrogationSubMenuView().resetCursor();
                break;
            
            case INTERROGATION_SUB:
                ui.showInterrogation();
                break;
            
            case AIS_ALARM: {
                AISAlarmModel model = ui.getAISAlarmModel();

                if (model.getDisplayMode() == AISAlarmModel.DisplayMode.CURRENT) {
                    model.setDisplayMode(AISAlarmModel.DisplayMode.HISTORY);
                    ui.getAISAlarmCurrentView().setHeaderText("ALARM HISTORY");
                } else {
                    model.setDisplayMode(AISAlarmModel.DisplayMode.CURRENT);
                    ui.getAISAlarmCurrentView().setHeaderText("AIS ALARM");
                }

                ui.getAISAlarmCurrentView().refresh(model);
                // card 切替は不要（同一 View）
                break;
            }


        }
    }

    /* ===== MENUボタン ===== */
    public void onMenuPressed() {
        ui.showMenu();
        ui.getMenuView().setSelectedIndex(0);
    }

    /* ===== DISPボタン ===== */
    public void onDispPressed() {
        switch (ui.getCurrentScreen()) {
            case LIST: {
                int row = ui.getShipListView().getTable().getSelectedRow();
                if (row < 0) {
                    break;
                }

                int mmsi = ui.getShipListView().getSelectedMmsi();
                if (mmsi < 0) {
                    break;
                }

                ui.showGraphic(mmsi);
                break;
            }

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
        }
    }

    /* ===== CLRボタン ===== */
    public void onClrPressed() {
        switch (ui.getCurrentScreen()) {
            case LIST_SUB:
            case MENU:
            case VOYAGE:
                ui.showList();
                break;

            case OTHER_DETAIL_SUB:
                ui.showOtherDetail();
                break;

            case VOYAGE_SUB:
                ui.discardVoyageSession();
                ui.redrawVoyage();
                break;
            
            case DESTINATION_LOAD:
                // 何もせず戻る（キャンセル）
                ui.redrawVoyage();
                break;

            case MESSAGE:
                ui.showMenu();
                ui.getMenuView().setSelectedIndex(1); // MESSAGE に戻る位置
                break;
            
            case EDIT_AND_TX:
                boolean exit = ui.getEditAndTxModel().onClr();
                ui.getEditAndTxView().refresh();

                if (exit) {
                    ui.showMessageMenu();
                }
                break;

            case EDIT_AND_TX_SUB:
                ui.showEditAndTx();
                break;
            
            case TEXT_EDIT:
                ui.showEditAndTx();
                break;
            
            case TX_TRAY_SUB:
                ui.showTxTray();
                break;

            case TX_TRAY_TEXT:
                ui.showTxTray();
                break;

            case TX_TRAY_DETAIL:
                ui.showTxTraySubMenu();
                break;

            case RX_TRAY_SUB:
                ui.showRxTray();
                break;

            case RX_TRAY_TEXT:
                ui.showRxTray();
                break;

            case RX_TRAY_DETAIL:
                ui.showRxTraySubMenu();
                break;

            case INTERROGATION:
                InterrogationModel interrogationModel = ui.getInterrogationModel();
                InterrogationView interrogationView = ui.getInterrogationView();
                boolean cleared = interrogationModel.onClr();
                if (cleared) {
                    ui.showMessageMenu();
                    ui.getMenuView().setSelectedIndex(1); // MESSAGE
                } else {
                    interrogationView.refresh(); // 編集状態を維持
                }
                break;

            case INTERROGATION_SUB:
                ui.showInterrogation();
                break;
             
            case MAINTENANCE_MENU:
                ui.showMenu();
                break;

            case SELF_DIAGNOSIS: {
                SelfDiagnosisModel model = ui.getSelfDiagnosisModel();

                boolean exitself = model.onClr();
                ui.getSelfDiagnosisView().update(model);

                if (exitself) {
                    ui.showMaintenance();
                }
                break;
            }

            case TRANSPONDER_LOG:
            case CONTROLLER_LOG:
            case CONTROLLER_LAN_LOG:
                ui.showSelfDiagnosis();
                break;
            
            case COMMUNICATION_TEST:
                ui.showMaintenance();
                break;
            
            case AIS_ALARM:
                ui.showMaintenance();
                break;

        }
        ui.clearCurrentShip();
    }

    //===== ↑ボタン =====
    public void onUpPressed() {
        switch (ui.getCurrentScreen()) {
            case LIST:
                ui.selectPrevShip();
                break;

            case MENU:
                ui.selectPrevMenu();
                break;
            
            case MESSAGE:
                moveUp(ui.getMessageMenuView().getList());
                break;
            
            case EDIT_AND_TX: {
                EditAndTxModel m = ui.getEditAndTxModel();

                if (m.getEditMode() == EditAndTxModel.EditMode.ROW_SELECT) {
                    m.moveRowUp();          // ← 左カーソル移動
                } else {
                    m.incrementValue();    // ← 値 or MMSI 桁
                }

                ui.getEditAndTxView().refresh();
                break;
            }

            
            case EDIT_AND_TX_SUB:
                moveUp(ui.getEditAndTxSubMenuView().getList());
                break;
            
            case TEXT_EDIT:
                ui.getTextEditView().moveUp();
                break;
            
            case TX_TRAY:
                moveUp(ui.getTxTrayListView().getList());
                break;

            case TX_TRAY_SUB:
                moveUp(ui.getTxTraySubMenuView().getList());
                break;

            case RX_TRAY:
                moveUp(ui.getRxTrayListView().getList());
                break;

            case RX_TRAY_SUB:
                moveUp(ui.getRxTraySubMenuView().getList());
                break;

            case INTERROGATION_SUB:
                moveUp(ui.getInterrogationSubMenuView().getList());
                break;
            
            case INTERROGATION: {
                InterrogationModel m = ui.getInterrogationModel();

                if (m.getEditMode() == InterrogationModel.EditMode.ROW_SELECT) {
                    m.moveRowUp();
                } else {
                    m.incrementValue();
                }

                ui.getInterrogationView().refresh();
                break;
            }

            case MAINTENANCE_MENU:
                moveUp(ui.getMaintenanceMenuView().getList());
                break;
            
            case SELF_DIAGNOSIS: {
                SelfDiagnosisModel model = ui.getSelfDiagnosisModel();
                model.moveUp();
                ui.getSelfDiagnosisView().update(model);
                break;
            }

            case TRANSPONDER_LOG:
                ui.getDiagnosisLogModel().prevPage();
                break;

            case CONTROLLER_LOG:
                ui.getDiagnosisLogModel().prevPage();
                break;

            case CONTROLLER_LAN_LOG:
                ui.getDiagnosisLogModel().prevPage();
                break;
            
            case COMMUNICATION_TEST: {
                CommunicationTestModel m = ui.getCommunicationTestModel();

                if (m.getCurrentRow() == CommunicationTestModel.Row.ACTION) {
                    m.incrementValue();   // TX ⇔ CANCEL
                } else {
                    m.moveRowUp();
                }

                ui.getCommunicationTestView().refresh(m);
                break;
            }

            case AIS_ALARM: {
                AISAlarmModel model = ui.getAISAlarmModel();
                model.prevPage();   // or nextPage()
                ui.getAISAlarmCurrentView().refresh(model);
                break;
            }



            case LIST_SUB:
                ui.selectPrevListSub();
                break;
            
            case VOYAGE:
                ui.selectPrevVoyageItem();
                break;

            case BEARING:
                moveUp(ui.getBearingView().getList());
                break;
            
            case SORT:
                moveUp(ui.getSortView().getList());
                break;
            
            case NAME:
                moveUp(ui.getNameView().getList());
                break;

            case DISP:
                moveUp(ui.getDispView().getList());
                break;
            
            case OTHER_DETAIL_SUB:
                moveUp(ui.getOtherShipSubMenuView().getList());
                break;

            case VOYAGE_SUB:
                moveUp(ui.getVoyageSubView().getList());
                break;

            case NAV_STATUS:
                moveUp(ui.getNavStatusView().getList());
                break;
            
            case ETA:
                ui.getETAInputView().increment();
                break;

            case DESTINATION:
                ui.getDestinationInputView().moveUp();
                break;
            
            case DESTINATION_LOAD:
                ui.getDestinationLoadView().selectPrev();
                break;

            case DRAUGHT:
                ui.getDraughtInputView().increment();
                break;

            case PERSONS:
                ui.getPersonsInputView().increment(); // ▲で値を増やす
                break;

            case SHIP_TYPE_US:
                ui.getShipTypeUSInputView().toggle();
                break;
            
            case SHIP_TYPE_CARGO:
                ui.getShipTypeCargoView().increment();
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
        }
    }
    
    //===== ↓ボタン =====
    public void onDownPressed() {
        switch (ui.getCurrentScreen()) {
            case LIST:
                ui.selectNextShip();
                break;

            case MENU:
                ui.selectNextMenu();
                break;
            
            case MESSAGE:
                moveDown(ui.getMessageMenuView().getList());
                break;

            case EDIT_AND_TX: {
                EditAndTxModel m = ui.getEditAndTxModel();

                if (m.getEditMode() == EditAndTxModel.EditMode.ROW_SELECT) {
                    m.moveRowDown();       // ← 左カーソル移動
                } else {
                    m.decrementValue();    // ← 値 or MMSI 桁
                }

                ui.getEditAndTxView().refresh();
                break;
            }
            
            case EDIT_AND_TX_SUB:
                moveDown(ui.getEditAndTxSubMenuView().getList());
                break;

            case TEXT_EDIT:
                ui.getTextEditView().moveDown();
                break;

            case TX_TRAY:
                moveDown(ui.getTxTrayListView().getList());
                break;

            case TX_TRAY_SUB:
                moveDown(ui.getTxTraySubMenuView().getList());
                break;
            
            case RX_TRAY:
                moveDown(ui.getRxTrayListView().getList());
                break;

            case RX_TRAY_SUB:
                moveDown(ui.getRxTraySubMenuView().getList());
                break;
            
            case INTERROGATION: {
                InterrogationModel m = ui.getInterrogationModel();

                if (m.getEditMode() == InterrogationModel.EditMode.ROW_SELECT) {
                    m.moveRowDown();
                } else {
                    m.decrementValue();
                }

                ui.getInterrogationView().refresh();
                break;
            }


            case INTERROGATION_SUB:
                moveDown(ui.getInterrogationSubMenuView().getList());
                break;

            case MAINTENANCE_MENU:
                moveDown(ui.getMaintenanceMenuView().getList());
                break;
            
            case SELF_DIAGNOSIS: {
                SelfDiagnosisModel model = ui.getSelfDiagnosisModel();
                model.moveDown();
                ui.getSelfDiagnosisView().update(model);
                break;
            }

            case TRANSPONDER_LOG:
                ui.getDiagnosisLogModel().nextPage();
                break;
            
            case CONTROLLER_LOG:
                ui.getDiagnosisLogModel().nextPage();
                break;
            
            case CONTROLLER_LAN_LOG:
                ui.getDiagnosisLogModel().nextPage();
                break;
            
            case COMMUNICATION_TEST: {
                CommunicationTestModel m = ui.getCommunicationTestModel();

                if (m.getCurrentRow() == CommunicationTestModel.Row.ACTION) {
                    m.decrementValue();   // TX ⇔ CANCEL
                } else {
                    m.moveRowDown();
                }

                ui.getCommunicationTestView().refresh(m);
                ui.getCommunicationTestView().requestFocus();
                break;
            }

            case AIS_ALARM: {
                AISAlarmModel model = ui.getAISAlarmModel();
                model.nextPage();   
                ui.getAISAlarmCurrentView().refresh(model);
                break;
            }


            case LIST_SUB:
                ui.selectNextListSub();
                break;

            case VOYAGE:
                ui.selectNextVoyageItem();
                break;
            
            case BEARING:
                moveDown(ui.getBearingView().getList());
                break;
            
            case SORT:
                moveDown(ui.getSortView().getList());
                break;
            
            case NAME:
                moveDown(ui.getNameView().getList());
                break;
            
            case DISP:
                moveDown(ui.getDispView().getList());
                break;

            case OTHER_DETAIL_SUB:
                moveDown(ui.getOtherShipSubMenuView().getList());
                break;

            case VOYAGE_SUB:
                moveDown(ui.getVoyageSubView().getList());
                break;

            case NAV_STATUS:
                moveDown(ui.getNavStatusView().getList());
                break;

            case DESTINATION:
                ui.getDestinationInputView().moveDown();
                break;

            case DESTINATION_LOAD:
                ui.getDestinationLoadView().selectNext();
                break;

            case DRAUGHT:
                ui.getDraughtInputView().decrement();
                break;

            case ETA:
                ui.getETAInputView().decrement();
                break;
            
            case PERSONS:
                ui.getPersonsInputView().decrement(); // ▼で値を減らす
                break;
            
            case SHIP_TYPE_US:
                ui.getShipTypeUSInputView().toggle();
                break;
            
            case SHIP_TYPE_CARGO:
                ui.getShipTypeCargoView().decrement();
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
        }
    }

    //===== ←ボタン =====
    public void onLeftPressed() {
        switch (ui.getCurrentScreen()) {
            case EDIT_AND_TX:
                ui.getEditAndTxModel().moveMmsiLeft();
                ui.getEditAndTxView().refresh();
                break;
            
            case TEXT_EDIT:
                ui.getTextEditView().moveLeft();
                break;

            case EDIT_AND_TX_TX_CONFIRM:
                ui.getTxConfirmView().selectPrev();
                break;

            case DESTINATION:
                ui.getDestinationInputView().moveLeft();
                break;
            
            case ETA:
                ui.getETAInputView().moveCursorLeft();
                break;
            
            case DRAUGHT:
                ui.getDraughtInputView().moveCursorLeft();
                break;

            case PERSONS:
                ui.getPersonsInputView().moveCursorLeft();
                break;
        }
                
    }

    //===== →ボタン =====
    public void onRightPressed() {
        switch (ui.getCurrentScreen()) {
            case EDIT_AND_TX:
                ui.getEditAndTxModel().moveMmsiRight();
                ui.getEditAndTxView().refresh();
                break;
            
            case TEXT_EDIT:
                ui.getTextEditView().moveRight();
                break;

            case EDIT_AND_TX_TX_CONFIRM:
                ui.getTxConfirmView().selectNext();
                break;

            case DESTINATION:
                ui.getDestinationInputView().moveRight();
                break;
            
            case ETA:
                ui.getETAInputView().moveCursorRight();
                break;
            
            case DRAUGHT:
                ui.getDraughtInputView().moveCursorRight();
                break;
            
            case PERSONS:
                ui.getPersonsInputView().moveCursorRight();
                break;
        }
    }

    //===== ENTERボタン =====

    public void onEnterPressed() {
        switch (ui.getCurrentScreen()) {

            case MENU:
                ui.enterMenu();
                break;
            
            case MESSAGE:
                int idx = ui.getMessageMenuView().getSelectedIndex();
                switch (idx) {
                    case 0:// EDIT AND TX
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
                }
                break;
            
            case EDIT_AND_TX:
                ui.getEditAndTxModel().enter();
                ui.getEditAndTxView().refresh();
                break;
            
            case EDIT_AND_TX_SUB:
                int idtx = ui.getEditAndTxSubMenuView().getSelectedIndex();

                switch (idtx) {
                    case 0: // TX
                        ui.showTxConfirm();
                        break;

                    case 1: // EDIT
                        ui.showTextEdit();
                        break;

                    case 2: // SAVE
                        ui.saveCurrentTxMessage();
                        break;

                    case 3: // EXIT
                        ui.showEditAndTx();
                        break;
                }
                break;
            
            case TEXT_EDIT:
                ui.getTextEditView().pressEnter();
                break;
            
            case EDIT_AND_TX_TX_RESULT:
                // OK しかない
                ui.showEditAndTx();
                break;

            case EDIT_AND_TX_TX_CONFIRM:
                if (ui.getTxConfirmView().getSelectedIndex() == 0) { // OK
                    ui.executePseudoTx();
                } else {
                    ui.showEditAndTx();
                }
                break;

            
            case TX_TRAY:
                ui.enterTxTrayList();
                break;
            
            case TX_TRAY_SUB:
                ui.enterTxTraySubMenu();
                break;
            
            case RX_TRAY:
                ui.enterRxTrayList();
                break;

            case RX_TRAY_SUB:
                ui.enterRxTraySubMenu();
                break;
            
            case INTERROGATION:
                ui.getInterrogationModel().enter();
                ui.getInterrogationView().refresh();
                break;

            case INTERROGATION_SUB:
                int idxInt = ui.getInterrogationSubMenuView().getSelectedIndex();
                switch (idxInt) {
                    case 0: // TX
                        ui.executeInterrogationTx();
                        break;

                    case 1: // CHECK
                        
                        break;

                    case 2: // CLEAR
                        ui.getInterrogationModel().clearCursor();
                        ui.showInterrogation();
                        break;

                    case 3: // EXIT
                        ui.showInterrogation();
                        break;
                }
                break;
            
            case INTERROGATION_TX_RESULT:
                if (ui.getInterrogationModel().getTxState()
                        == InterrogationModel.TxState.OK) {
                    ui.showInterrogationResponse();
                } else {
                    ui.getInterrogationView().refresh();
                    ui.showInterrogation();
                }
                break;
            
            case MAINTENANCE_MENU:
                int mIdx = ui.getMaintenanceMenuView().getSelectedIndex();
                switch (mIdx) {
                    case 0: // SELF DIAGNOSIS
                        ui.showSelfDiagnosis();
                        break;
                    
                    case 1: // COMMUNICATION TEST
                        // 画面切替
                        ui.showCommunicationTest();

                        // Model と View を同期
                        CommunicationTestModel model = ui.getCommunicationTestModel();
                        CommunicationTestView view = ui.getCommunicationTestView();

                        // 初期状態のカーソル
                        if (model.getAction() == CommunicationTestModel.Action.TX) {
                            view.setSelectedAction(0);
                        } else {
                            view.setSelectedAction(1);
                        }

                        // DESTINATION/MMSI/RESULT 描画
                        view.refresh(model);
                        view.requestFocus();
                        break;
                    
                    case 2: // AIS ALARM

                        // モード設定
                        ui.getAISAlarmModel().setDisplayMode(AISAlarmModel.DisplayMode.CURRENT);

                        // 既存モデルにダミーデータを直接追加
                        AISAlarmModel dummy = AISAlarmModel.createWithDummyData();
                        for (AISAlarmModel.AlarmEntry entry : dummy.getDisplayEntries()) {
                            ui.getAISAlarmModel().add(entry);
                        }

                        // View 更新
                        ui.getAISAlarmCurrentView().setHeaderText("AIS ALARM");
                        ui.getAISAlarmCurrentView().refresh(ui.getAISAlarmModel());
                        ui.showAISAlarmCurrent();
                        break;


                    
                    case 3: // SENSOR STATUS
                        
                        break;
                    
                    case 4: // EVENT LOG
                        
                        break;
                    
                    case 5: // SOFTWARE VERSION
                        
                        break;
                }
                break;
            
            case SELF_DIAGNOSIS: {
                SelfDiagnosisModel model = ui.getSelfDiagnosisModel();

                model.enter();
                ui.getSelfDiagnosisView().update(model);

                SelfDiagnosisModel.Row log = model.consumeRequestedLogRow();
                if (log != null) {
                    switch (log) {
                        case TRANSPONDER_LOG:
                            ui.showTransponderLog();
                            break;

                        case CONTROLLER_LOG:
                            ui.showControllerLog();
                            break;

                        case CONTROLLER_LAN_LOG:
                            ui.showControllerLog();
                            break;

                        default:
                            break;
                    }
                }
                break;
            }

            case COMMUNICATION_TEST: {
                CommunicationTestModel m = ui.getCommunicationTestModel();

                if (m.getCurrentRow() == CommunicationTestModel.Row.ACTION) {

                    if (m.getAction() == CommunicationTestModel.Action.TX) {
                        ui.executeCommunicationtest();
                    } else { // CANCEL
                        ui.showMaintenance();
                    }
                }
                break;
            }

            case COMMUNICATION_ACK_POPUP:
                // ACK 表示後、TEST 画面へ戻る

                // ACK 画面で OK/NG を押した結果を Model に反映済みと仮定
                CommunicationTestModel testModel = ui.getCommunicationTestModel();
                // 例: testModel.setResult(true); // OKの場合

                ui.showCommunicationTest();

                CommunicationTestView testView = ui.getCommunicationTestView();
                testView.refresh(testModel);

                // カーソル初期化
                if (testModel.getAction() == CommunicationTestModel.Action.TX) {
                    testView.setSelectedAction(0);
                } else {
                    testView.setSelectedAction(1);
                }
                testView.requestFocus();
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

            case VOYAGE:
                ui.enterVoyage();
                break;

            case NAV_STATUS:
                ui.enterNavStatus();
                break;
            
            case DESTINATION:
                ui.getDestinationInputView().pressEnter();
                SwingUtilities.invokeLater(()
                        -> ui.getVoyageView().selectItemById(3)
                );
                break;
            
            case DESTINATION_LOAD: {
                String dest = ui.getDestinationLoadView().getSelectedDestination();
                if (dest != null) {
                    // working に反映（まだ保存しない）
                    ui.getVoyageSession().getWorking().destination = dest;

                    // VoyageView 表示更新
                    ui.getVoyageView().setDestination(dest);
                }

                // VOYAGE DATA に戻る
                ui.redrawVoyage();
                SwingUtilities.invokeLater(
                        () -> ui.getVoyageView().selectItemById(3) // ETA
                );
                break;
            }


            case ETA:
                ETAInputModel model = ui.getETAInputModel();

                if (model.confirm()) {

                    // ① ETA 数値取得（UIモデル → 数値）
                    int month = model.getMonth();
                    int day = model.getDay();
                    int hour = model.getHour();
                    int minute = model.getMinute();

                    // ② OwnShipInfo 更新（通信モデル）
                    ui.getVoyageSession()
                            .getWorking()
                            .setEta(month, day, hour, minute);

                    // ③ VoyageView 更新（表示専用）
                    ui.getVoyageView().setEta(model.getDisplayText());

                    // ④ Voyage 画面へ戻る
                    ui.redrawVoyage();
                    SwingUtilities.invokeLater(
                            () -> ui.getVoyageView().selectItemById(4)
                    );
                }
                break;

            case DRAUGHT:
                DraughtInputModel draughtModel = ui.getDraughtInputModel();

                if (draughtModel.isLastDigit()) {

                    // 数値取得
                    double draughtValue = draughtModel.getValue();

                    // Model：数値で保持
                    ui.getVoyageSession().getWorking().draught = draughtValue;

                    // View：数値だけ渡す
                    ui.getVoyageView().setDraught(draughtValue);

                    // 画面更新
                    ui.redrawVoyage();
                    SwingUtilities.invokeLater(
                            () -> ui.getVoyageView().selectItemById(5)
                    );
                }
                break;

            
            case PERSONS:
                PersonsInputModel personsModel = ui.getPersonsInputModel();

                if (personsModel.confirm()) {
                    // ① 搭乗人員数取得
                    int personsValue = personsModel.getValue();
                    String personsText;

                    if (personsValue >= 8191) {
                        personsValue = 8191;
                        personsText = "8191 OR MORE";
                    } else {
                        personsText = String.valueOf(personsValue);
                    }

                    // ② OwnShipInfo 更新
                    ui.getVoyageSession().getWorking().persons = personsValue;

                    // ③ VoyageView 表示更新
                    ui.getVoyageView().setPersonsOnBoard(personsText);

                    // ④ VOYAGE DATA 画面へ戻る
                    ui.redrawVoyage();

                    // ⑤ 次項目「6.SHIP TYPE U.S.」へ移動
                    SwingUtilities.invokeLater(()
                            -> ui.getVoyageView().selectItemById(6)
                    );
                }
                break;

            case SHIP_TYPE_US:
                ShipTypeUSInputModel shipTypeModel = ui.getShipTypeUSInputModel();

                if (shipTypeModel.confirm()) {

                    // ① 値取得（"ON" / "OFF"）
                    String value = shipTypeModel.getValue();
                    boolean isUS = "ON".equals(value); // boolean に変換して条件分岐用に使用
                    ui.getShipTypeCargoModel().setShipTypeUS(isUS);

                    // ② OwnShipInfo に反映（String 型）
                    ui.getVoyageSession().getWorking().shipTypeUS = isUS ? "ON" : "OFF";

                    // ③ 表示文字列を作成
                    String displayText = isUS ? "ON : US" : "OFF : International";

                    // ④ VoyageView 更新
                    ui.getVoyageView().setShipTypeUS(displayText);

                    // ⑤ VOYAGE DATA 画面へ戻す
                    ui.redrawVoyage();

                    // ⑥ 次項目「7. TYPE OF CARGO」にカーソル移動
                    SwingUtilities.invokeLater(()
                            -> ui.getVoyageView().selectItemById(7)
                    );
                }
                break;

            case SHIP_TYPE_CARGO: {

                ShipTypeCargoInputModel ShipTypeCargomodel = ui.getShipTypeCargoModel();

                // ★ working copy を使用
                OwnShipInfo working = ui.getVoyageSession().getWorking();

                // US / INT 判定も working から
                boolean isUS = "ON".equals(working.shipTypeUS);
                ShipTypeCargomodel.setShipTypeUS(isUS);

                // === SHIP TYPE → CARGO へ遷移 ===
                if (ShipTypeCargomodel.getPhase() == ShipTypeCargoInputModel.Phase.SHIP_TYPE) {
                    ShipTypeCargomodel.nextPhase();
                    ui.getShipTypeCargoView().refresh();
                    break;
                }

                // === CARGO 確定（まだ保存しない） ===
                working.shipType = ShipTypeCargomodel.getShipTypeCode();
                working.CargoType = ShipTypeCargomodel.getCargoTypeDisplayText();

                // 表示更新のみ
                working.shipType = ShipTypeCargomodel.getShipTypeCode();
                ui.getVoyageView().setShipType(working);

                ui.getVoyageView().setCargoType(ShipTypeCargomodel.getCargoTypeDisplayText());

                // VOYAGE DATA に戻る
                ui.redrawVoyage();
                SwingUtilities.invokeLater(()
                        -> ui.getVoyageView().selectItemById(7)
                );
                break;
            }

            case VOYAGE_SUB:
                ui.enterVoyageSub();
                break;

            default:
                break;
        }
    }

    private void moveUp(JList<?> list) {
        int idx = list.getSelectedIndex();
        if (idx > 0) {
            list.setSelectedIndex(idx - 1);
            list.ensureIndexIsVisible(idx - 1);
        }
    }

    private void moveDown(JList<?> list) {
        int idx = list.getSelectedIndex();
        int max = list.getModel().getSize() - 1;
        if (idx < max) {
            list.setSelectedIndex(idx + 1);
            list.ensureIndexIsVisible(idx + 1);
        }
    }

    

}
