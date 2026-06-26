package ais.app;

import java.awt.Component;
import javax.swing.JPanel;

public class ScreenRegistry {

    private final JPanel cardPanel;

    public ScreenRegistry(JPanel cardPanel) {
        this.cardPanel = cardPanel;
    }

    public void register(ScreenId screenId, Component component) {
        cardPanel.add(component, screenId.cardName());
    }
}
