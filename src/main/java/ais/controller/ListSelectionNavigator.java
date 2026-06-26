package ais.controller;

import javax.swing.JList;

public final class ListSelectionNavigator {

    private ListSelectionNavigator() {
    }

    public static void moveUp(JList<?> list) {
        int idx = list.getSelectedIndex();
        if (idx > 0) {
            list.setSelectedIndex(idx - 1);
            list.ensureIndexIsVisible(idx - 1);
        }
    }

    public static void moveDown(JList<?> list) {
        int idx = list.getSelectedIndex();
        int max = list.getModel().getSize() - 1;
        if (idx < max) {
            list.setSelectedIndex(idx + 1);
            list.ensureIndexIsVisible(idx + 1);
        }
    }
}
