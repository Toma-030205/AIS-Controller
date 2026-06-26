package ais.app;

import ais.controller.RightPaneController;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import javax.swing.BorderFactory;
import javax.swing.JEditorPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.event.HyperlinkEvent;

public class RightPanePanel extends JPanel {
    private final JEditorPane infoPane;
    private final RightPaneController controller;

    public RightPanePanel() {
        super(new BorderLayout());
        infoPane = createInfoPane();
        controller = new RightPaneController(infoPane);

        JScrollPane infoScroll = new JScrollPane(infoPane);
        infoScroll.setBorder(BorderFactory.createTitledBorder("詳細説明"));
        infoScroll.setPreferredSize(new Dimension(300, 300));
        add(infoScroll, BorderLayout.CENTER);
    }

    public RightPaneController getController() {
        return controller;
    }

    private JEditorPane createInfoPane() {
        JEditorPane pane = new JEditorPane();
        pane.setContentType("text/html");
        pane.setEditable(false);
        pane.setText("<html><body style='padding:12px;color:#404040;font-family:Sans-Serif;'></body></html>");
        pane.setBackground(new Color(0xF9F9F9));
        pane.addHyperlinkListener(e -> {
            if (e.getEventType() == HyperlinkEvent.EventType.ACTIVATED) {
                scrollToReference(pane, e.getDescription());
            }
        });
        return pane;
    }

    private void scrollToReference(JEditorPane pane, String description) {
        if (description == null) {
            return;
        }
        String reference = description;
        if (reference.startsWith("#")) {
            reference = reference.substring(1);
        } else if (reference.contains("#")) {
            reference = reference.substring(reference.indexOf('#') + 1);
        }
        pane.scrollToReference(reference);
    }
}
