package ais.workflow;

import ais.app.AISMain2;
import ais.app.ScreenId;
import ais.model.EditAndTxModel;
import ais.model.RxMessage;
import ais.model.TextEditModel;
import ais.model.TxMessage;
import ais.view.TxResultView;

public class MessageWorkflow {

    private final AISMain2 ui;

    public MessageWorkflow(AISMain2 ui) {
        this.ui = ui;
    }

    public void showMessageMenu() {
        ui.navigateTo(ScreenId.MESSAGE);
        ui.getMessageMenuView().setSelectedIndex(0);
        ui.getMessageMenuView().requestFocus();
    }

    public void showEditAndTxSubMenu() {
        ui.navigateTo(ScreenId.EDIT_AND_TX_SUB);
    }

    public void showEditAndTx() {
        ui.navigateTo(ScreenId.EDIT_AND_TX);
        ui.getEditAndTxView().refresh();
    }

    public void showTextEdit() {
        TextEditModel model = new TextEditModel(calcTextMaxLength());
        ui.setTextEditModel(model);
        ui.getTextEditView().setModel(model);
        ui.getTextEditView().resetCursor();
        ui.getTextEditView().refresh();
        ui.navigateTo(ScreenId.TEXT_EDIT);
    }

    public void confirmTextEdit(String text) {
        ui.getEditAndTxModel().setText(text);
        showEditAndTxSubMenu();
    }

    public void exitTextEdit() {
        showEditAndTxSubMenu();
    }

    public void showTxConfirm() {
        ui.navigateTo(ScreenId.EDIT_AND_TX_TX_CONFIRM);
    }

    public void showTxTransmitting() {
        ui.navigateTo(ScreenId.EDIT_AND_TX_TX_TRANSMITTING);
    }

    public void showTxTray() {
        ui.getTxTrayListView().updateFromModel(ui.getTxTrayModel());
        ui.navigateTo(ScreenId.TX_TRAY);
    }

    public void showTxTraySubMenu() {
        ui.navigateTo(ScreenId.TX_TRAY_SUB);
    }

    public void showTxResult(TxMessage msg) {
        TxResultView view = ui.getTxResultView();

        if (msg.isAddressed()) {
            view.setResultText("RESULT : ACT OK");
        } else {
            view.setResultText("TRANSMIT : OK");
        }

        ui.navigateTo(ScreenId.EDIT_AND_TX_TX_RESULT);
    }

    public void showRxTray() {
        if (ui.getRxTrayModel().size() == 0) {
            ui.getRxTrayModel().generateDummyMessages();
        }

        ui.getRxTrayListView().updateFromModel(ui.getRxTrayModel());
        ui.navigateTo(ScreenId.RX_TRAY);
    }

    public void showRxTraySubMenu() {
        ui.getRxTraySubMenuView().setSelectedIndex(0);
        ui.navigateTo(ScreenId.RX_TRAY_SUB);
    }

    public void showRxTrayText() {
        int idx = ui.getRxTrayListView().getSelectedIndex();
        if (idx < 0) {
            return;
        }

        RxMessage msg = ui.getRxTrayModel().get(idx);
        if (msg == null) {
            return;
        }

        msg.markRead();
        ui.getRxTrayListView().updateFromModel(ui.getRxTrayModel());
        ui.getRxTrayTextView().setMessage(msg);
        ui.navigateTo(ScreenId.RX_TRAY_TEXT);
    }

    public void showRxTrayDetail() {
        int idx = ui.getRxTrayListView().getSelectedIndex();
        if (idx < 0) {
            return;
        }

        RxMessage msg = ui.getRxTrayModel().get(idx);
        ui.getRxTrayDetailView().setMessage(msg);
        ui.navigateTo(ScreenId.RX_TRAY_DETAIL);
    }

    public void enterTxTrayList() {
        int idx = ui.getTxTrayListView().getSelectedIndex();
        if (idx < 0) {
            return;
        }

        TxMessage msg = ui.getTxTrayModel().get(idx);
        ui.getTxTextView().setMessage(msg);
        ui.navigateTo(ScreenId.TX_TRAY_TEXT);
    }

    public void enterTxTraySubMenu() {
        int idx = ui.getTxTrayListView().getSelectedIndex();
        if (idx < 0) {
            return;
        }

        TxMessage msg = ui.getTxTrayModel().get(idx);

        switch (ui.getTxTraySubMenuView().getSelectedIndex()) {
            case 0:
                ui.getTxMessageDetailView().setMessage(msg);
                ui.navigateTo(ScreenId.TX_TRAY_DETAIL);
                break;
            case 1:
                ui.getEditAndTxModel().loadFromTxMessage(msg);
                showEditAndTx();
                break;
            case 2:
                ui.getTxTrayModel().remove(idx);
                showTxTray();
                break;
            case 3:
                showTxTray();
                break;
            default:
                break;
        }
    }

    public void enterRxTrayList() {
        showRxTrayText();
    }

    public void enterRxTraySubMenu() {
        int idx = ui.getRxTrayListView().getSelectedIndex();
        if (idx < 0) {
            return;
        }

        RxMessage msg = ui.getRxTrayModel().get(idx);

        switch (ui.getRxTraySubMenuView().getSelectedIndex()) {
            case 0:
                ui.getRxTrayDetailView().setMessage(msg);
                ui.navigateTo(ScreenId.RX_TRAY_DETAIL);
                break;
            case 1:
                ui.getEditAndTxModel().loadFromRxMessage(msg);
                showEditAndTx();
                break;
            case 2:
                ui.getRxTrayModel().remove(idx);
                showRxTray();
                break;
            case 3:
                showRxTray();
                break;
            default:
                break;
        }
    }

    public int calcTextMaxLength() {
        EditAndTxModel.Format format = ui.getEditAndTxModel().getFormat();
        EditAndTxModel.Category category = ui.getEditAndTxModel().getCategory();

        if (format == EditAndTxModel.Format.ADDRESSED) {
            return (category == EditAndTxModel.Category.SAFETY) ? 156 : 151;
        }
        return (category == EditAndTxModel.Category.SAFETY) ? 161 : 156;
    }

    public TxMessage buildTxMessageFromEdit() {
        TxMessage msg = new TxMessage();
        EditAndTxModel editModel = ui.getEditAndTxModel();
        String destination = editModel.getFormat() == EditAndTxModel.Format.ADDRESSED
                ? editModel.getMmsiText()
                : "BROADCAST";
        msg.configure(
                editModel.getFormat(),
                editModel.getCategory(),
                editModel.getReply() == EditAndTxModel.Reply.ON,
                editModel.getFunction().name(),
                editModel.getChannel().ordinal(),
                destination,
                editModel.getText());
        return msg;
    }

    public void saveCurrentTxMessage() {
        ui.getTxTrayModel().add(buildTxMessageFromEdit());
        showTxTray();
    }

    public void executePseudoTx() {
        TxMessage msg = ui.getEditAndTxModel().buildTxMessage();
        ui.getTxTrayModel().add(msg);
        executePseudoTx(msg);
    }

    public void executePseudoTx(TxMessage msg) {
        msg.markTransmitting();
        showTxTransmitting();

        javax.swing.Timer timer = new javax.swing.Timer(1500, e -> {
            msg.markAckOk();
            showTxResult(msg);
        });
        timer.setRepeats(false);
        timer.start();
    }
}
