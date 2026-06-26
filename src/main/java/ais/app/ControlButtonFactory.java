package ais.app;

import ais.controller.RightPaneController;
import ais.view.RoundButton;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.SwingUtilities;

public final class ControlButtonFactory {
    private static RoundButton lastButton;

    private ControlButtonFactory() {
    }

    public static RoundButton create(String label, String description, RightPaneController rightPaneController) {
        RoundButton button = new RoundButton(label);
        String desc = (description == null) ? "説明はありません" : description;

        button.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                if (SwingUtilities.isRightMouseButton(e) || e.isPopupTrigger()) {
                    if (lastButton == button) {
                        rightPaneController.clear();
                        lastButton = null;
                        return;
                    }

                    rightPaneController.showButtonHelp(label, desc, extraNote(label));
                    button.requestFocusInWindow();
                    lastButton = button;
                }
            }
        });
        return button;
    }

    private static String extraNote(String label) {
        if ("PWR".equals(label)) {
            return "電源オフにはパスワード入力が必要な機種があります。";
        }
        if ("DIM".equals(label)) {
            return "バックライトを最小にすると夜間で視認しづらい場合があります。";
        }
        return null;
    }
}
