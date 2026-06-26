package ais.workflow;

import ais.app.AISMain2;
import ais.app.ScreenId;
import ais.model.DraughtInputModel;
import ais.model.ETAInputModel;
import ais.model.NavStatus;
import ais.model.OwnShipInfo;
import ais.model.PersonsInputModel;
import ais.model.ShipTypeCargoInputModel;
import ais.model.ShipTypeUSInputModel;
import ais.model.VoyageEditSession;
import ais.model.settingItem;
import ais.network.AisUdpBroadcaster;
import ais.util.OwnShipJsonUtil;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.SwingUtilities;

public class VoyageWorkflow {

    private static final Logger LOGGER = Logger.getLogger(VoyageWorkflow.class.getName());

    private final AISMain2 ui;
    private VoyageEditSession session;

    public VoyageWorkflow(AISMain2 ui) {
        this.ui = ui;
    }

    public VoyageEditSession getSession() {
        return session;
    }

    public void showVoyage() {
        session = new VoyageEditSession(ui.getOwnShipInfo());
        ui.getVoyageView().refreshFrom(session.getWorking());
        ui.navigateTo(ScreenId.VOYAGE);
    }

    public void redrawVoyage() {
        if (session == null) {
            showVoyage();
            return;
        }
        ui.getVoyageView().refreshFrom(session.getWorking());
        ui.navigateTo(ScreenId.VOYAGE);
    }

    public void showDestinationLoad() {
        if (session == null) {
            showVoyage();
        }

        ui.getDestinationLoadModel().buildFrom(session.getWorking());
        ui.getDestinationLoadView().setDestinations(
                ui.getDestinationLoadModel().getDisplayList()
        );
        ui.navigateTo(ScreenId.DESTINATION_LOAD);
    }

    public void enterVoyage() {
        settingItem selected = ui.getVoyageView().getList().getSelectedValue();
        if (selected == null) {
            return;
        }

        switch (selected.id) {
            case 1:
                ui.navigateTo(ScreenId.NAV_STATUS);
                ui.getNavStatusView().setSelectedIndex(0);
                break;
            case 2:
                ui.navigateTo(ScreenId.DESTINATION);
                ui.getDestinationInputView().resetCursor();
                ui.getDestinationInputView().requestFocusInWindow();
                break;
            case 3:
                ui.navigateTo(ScreenId.ETA);
                break;
            case 4:
                ui.showDraught();
                ui.getDraughtInputView().requestFocusInWindow();
                break;
            case 5:
                ui.showPersons();
                ui.getPersonsInputView().requestFocusInWindow();
                break;
            case 6:
                ui.navigateTo(ScreenId.SHIP_TYPE_US);
                ui.getShipTypeUSInputView().requestFocusInWindow();
                break;
            case 7:
                ui.getShipTypeCargoModel().resetPhase();
                ui.navigateTo(ScreenId.SHIP_TYPE_CARGO);
                ui.getShipTypeCargoView().requestFocusInWindow();
                break;
            default:
                break;
        }
    }

    public void enterNavStatus() {
        if (session == null) {
            return;
        }

        String status = ui.getNavStatusView().getSelectedStatus();
        if (status == null) {
            return;
        }

        session.getWorking().setNavStatus(NavStatus.fromLabel(status));
        ui.getVoyageView().updateNavStatus(status);
        redrawVoyage();
        SwingUtilities.invokeLater(() -> ui.getVoyageView().selectItemById(2));
    }

    public void enterDestination() {
        ui.getDestinationInputView().pressEnter();
        SwingUtilities.invokeLater(() -> ui.getVoyageView().selectItemById(3));
    }

    public void enterDestinationLoad() {
        if (session == null) {
            return;
        }

        String dest = ui.getDestinationLoadView().getSelectedDestination();
        if (dest != null) {
            session.getWorking().destination = dest;
            ui.getVoyageView().setDestination(dest);
        }

        redrawVoyage();
        SwingUtilities.invokeLater(() -> ui.getVoyageView().selectItemById(3));
    }

    public void enterEta() {
        if (session == null) {
            return;
        }

        ETAInputModel model = ui.getETAInputModel();
        if (!model.confirm()) {
            return;
        }

        session.getWorking().setEta(
                model.getMonth(),
                model.getDay(),
                model.getHour(),
                model.getMinute()
        );
        ui.getVoyageView().setEta(model.getDisplayText());
        redrawVoyage();
        SwingUtilities.invokeLater(() -> ui.getVoyageView().selectItemById(4));
    }

    public void enterDraught() {
        if (session == null) {
            return;
        }

        DraughtInputModel model = ui.getDraughtInputModel();
        if (!model.isLastDigit()) {
            return;
        }

        double value = model.getValue();
        session.getWorking().draught = value;
        ui.getVoyageView().setDraught(value);
        redrawVoyage();
        SwingUtilities.invokeLater(() -> ui.getVoyageView().selectItemById(5));
    }

    public void enterPersons() {
        if (session == null) {
            return;
        }

        PersonsInputModel model = ui.getPersonsInputModel();
        if (!model.confirm()) {
            return;
        }

        int value = model.getValue();
        String text;
        if (value >= 8191) {
            value = 8191;
            text = "8191 OR MORE";
        } else {
            text = String.valueOf(value);
        }

        session.getWorking().persons = value;
        ui.getVoyageView().setPersonsOnBoard(text);
        redrawVoyage();
        SwingUtilities.invokeLater(() -> ui.getVoyageView().selectItemById(6));
    }

    public void enterShipTypeUS() {
        if (session == null) {
            return;
        }

        ShipTypeUSInputModel model = ui.getShipTypeUSInputModel();
        if (!model.confirm()) {
            return;
        }

        boolean isUS = "ON".equals(model.getValue());
        ui.getShipTypeCargoModel().setShipTypeUS(isUS);
        session.getWorking().shipTypeUS = isUS ? "ON" : "OFF";
        ui.getVoyageView().setShipTypeUS(isUS ? "ON : US" : "OFF : International");
        redrawVoyage();
        SwingUtilities.invokeLater(() -> ui.getVoyageView().selectItemById(7));
    }

    public void enterShipTypeCargo() {
        if (session == null) {
            return;
        }

        ShipTypeCargoInputModel model = ui.getShipTypeCargoModel();
        OwnShipInfo working = session.getWorking();

        boolean isUS = "ON".equals(working.shipTypeUS);
        model.setShipTypeUS(isUS);

        if (model.getPhase() == ShipTypeCargoInputModel.Phase.SHIP_TYPE) {
            model.nextPhase();
            ui.getShipTypeCargoView().refresh();
            return;
        }

        working.shipType = model.getShipTypeCode();
        working.CargoType = model.getCargoTypeDisplayText();
        ui.getVoyageView().setShipType(working);
        ui.getVoyageView().setCargoType(model.getCargoTypeDisplayText());
        redrawVoyage();
        SwingUtilities.invokeLater(() -> ui.getVoyageView().selectItemById(7));
    }

    public void enterVoyageSub() {
        String selected = ui.getVoyageSubView().getSelectedItem();
        if (selected == null) {
            return;
        }

        if ("[ SET ]".equals(selected)) {
            commitAndBroadcast();
            session = null;
            ui.navigateTo(ScreenId.MENU);
            return;
        }

        if ("[ DEST LOAD ]".equals(selected)) {
            showDestinationLoad();
            return;
        }

        if ("[ EXIT ]".equals(selected)) {
            session = null;
            ui.navigateTo(ScreenId.MENU);
        }
    }

    public void discardSession() {
        session = null;
    }

    public void selectNextVoyageItem() {
        int selected = ui.getVoyageView().getSelectedIndex();
        int target = (selected == -1) ? 0 : selected + 1;
        int size = ui.getVoyageView().getItemCount();

        while (target < size) {
            settingItem item = ui.getVoyageView().getList().getModel().getElementAt(target);
            if (item.selectable) {
                ui.getVoyageView().setSelectedIndex(target);
                ui.getVoyageView().getList().ensureIndexIsVisible(target);
                break;
            }
            target++;
        }
    }

    public void selectPrevVoyageItem() {
        int selected = ui.getVoyageView().getSelectedIndex();
        int target = (selected == -1) ? ui.getVoyageView().getItemCount() - 1 : selected - 1;

        while (target >= 0) {
            settingItem item = ui.getVoyageView().getList().getModel().getElementAt(target);
            if (item.selectable) {
                ui.getVoyageView().setSelectedIndex(target);
                ui.getVoyageView().getList().ensureIndexIsVisible(target);
                break;
            }
            target--;
        }
    }

    private void commitAndBroadcast() {
        if (session == null) {
            return;
        }

        session.commit();
        String json = OwnShipJsonUtil.toVoyageJson(ui.getShipManager().getOwnShip());

        try {
            AisUdpBroadcaster broadcaster = new AisUdpBroadcaster();
            broadcaster.sendJson(json);
            broadcaster.close();
        } catch (Exception e) {
            LOGGER.log(Level.WARNING, "Failed to send voyage update", e);
        }

        LOGGER.fine(() -> "JSON sent: " + json);
    }
}
