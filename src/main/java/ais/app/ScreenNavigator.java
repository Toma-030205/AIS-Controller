package ais.app;

import ais.controller.RightPaneController;
import java.awt.CardLayout;
import java.util.function.Consumer;
import javax.swing.JPanel;

public class ScreenNavigator {

    private final JPanel cardPanel;
    private final Consumer<ScreenId> focusHandler;
    private RightPaneController rightPaneController;
    private ScreenId currentScreen;

    public ScreenNavigator(JPanel cardPanel, Consumer<ScreenId> focusHandler) {
        this.cardPanel = cardPanel;
        this.focusHandler = focusHandler;
    }

    public void setRightPaneController(RightPaneController rightPaneController) {
        this.rightPaneController = rightPaneController;
    }

    public ScreenId getCurrentScreen() {
        return currentScreen;
    }

    public String getCurrentCard() {
        return currentScreen == null ? null : currentScreen.cardName();
    }

    public void show(ScreenId screenId) {
        CardLayout layout = (CardLayout) cardPanel.getLayout();
        layout.show(cardPanel, screenId.cardName());
        currentScreen = screenId;

        if (rightPaneController != null) {
            rightPaneController.showCardHelp(screenId);
        }

        if (focusHandler != null) {
            focusHandler.accept(screenId);
        }
    }
}
