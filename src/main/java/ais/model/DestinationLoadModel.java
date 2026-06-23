package ais.model;

import java.util.ArrayList;
import java.util.List;




public class DestinationLoadModel {

    private static final int MAX_HISTORY = 5;

    private final List<String> displayList = new ArrayList<>();

    /* ===============================
     * 表示用リスト構築
     * =============================== */
    public void buildFrom(OwnShipInfo ship) {
        displayList.clear();

        if (ship.destination != null && !ship.destination.isEmpty()) {
            displayList.add(ship.destination);
        }

        for (String h : ship.destinationHistory) {
            if (!displayList.contains(h)) {
                displayList.add(h);
            }
        }
    }

    public List<String> getDisplayList() {
        return displayList;
    }

    /* ===============================
     * ★ 選択確定時の更新ロジック
     * =============================== */
    public void applySelection(OwnShipInfo ship, String selected) {

        if (selected == null || selected.isEmpty()) {
            return;
        }

        // ① 現在の destination を履歴に戻す
        if (ship.destination != null
                && !ship.destination.isEmpty()
                && !ship.destination.equals(selected)) {

            ship.destinationHistory.remove(ship.destination);
            ship.destinationHistory.add(0, ship.destination);
        }

        // ② 選択されたものを最新にする
        ship.destination = selected;

        ship.destinationHistory.remove(selected);
        ship.destinationHistory.add(0, selected);

        // ③ 最大件数制御
        while (ship.destinationHistory.size() > MAX_HISTORY) {
            ship.destinationHistory.remove(ship.destinationHistory.size() - 1);
        }
    }
}
