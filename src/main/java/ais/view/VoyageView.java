package ais.view;

import ais.model.OwnShipInfo;
import ais.model.settingItem;
import java.awt.*;
import javax.swing.*;



public class VoyageView extends JPanel {

    private JLabel voyageHeader;
    private JList<settingItem> voyageList;
    private DefaultListModel<settingItem> voyageListModel;
    private boolean programmaticSelect = false;
    private JScrollPane scroll;

    public VoyageView(OwnShipInfo myship) {
        setLayout(new BorderLayout());
        initComponents(myship);
    }

    private void initComponents(OwnShipInfo myship) {

        voyageHeader = new JLabel("VOYAGE DATA");
        voyageHeader.setFont(new Font("Meiryo UI", Font.BOLD, 32));
        voyageHeader.setOpaque(true);
        voyageHeader.setBackground(UiTheme.HEADER_BG);
        voyageHeader.setBorder(BorderFactory.createEmptyBorder(8, 12, 8, 12));

        voyageListModel = new DefaultListModel<>();

        voyageListModel.addElement(new settingItem(1, "1. NAV. STATUS", true));
        voyageListModel.addElement(new settingItem(1, "   ：" + myship.getNavStatus().getLabel(), false));

        voyageListModel.addElement(new settingItem(2, "2. DESTINATION", true));
        voyageListModel.addElement(new settingItem(2, "   ：" + myship.destination, false));

        voyageListModel.addElement(new settingItem(3, "3. ETA", true));
        voyageListModel.addElement(new settingItem(3, "   ：" + myship.getEtaMonth() + "/" + myship.getEtaDay() + " " + myship.getEtaHour() + ":" + myship.getEtaMinute(), false));

        voyageListModel.addElement(new settingItem(4, "4. DRAUGHT", true));
        String draughtText;
        if (myship.draught >= 25.5) {
            draughtText = "25.5m or greater";
        } else {
            draughtText = String.format("%.1fm", myship.draught);
        }

        voyageListModel.addElement(
                new settingItem(4, "   ：" + draughtText, false)
        );

        voyageListModel.addElement(new settingItem(5, "5. PERSONS ON BOARD", true));
        voyageListModel.addElement(new settingItem(5, "   ：" + myship.persons, false));

        voyageListModel.addElement(new settingItem(6, "6. SHIP TYPE", true));
        voyageListModel.addElement(new settingItem(6, "   ：" + myship.shipTypeUS, false));

        voyageListModel.addElement(new settingItem(7, "7. TYPE OF SHIP AND CARGO", true));
        voyageListModel.addElement(new settingItem(7, "     TYPE OF SHIP : " + myship.shipType, false));
        voyageListModel.addElement(new settingItem(7, "     CARGO TYPE   : " + myship.CargoType, false));

        voyageList = new JList<>(voyageListModel);
        voyageList.setFont(new Font("Meiryo UI", Font.PLAIN, 30));
        voyageList.setSelectionBackground(UiTheme.SELECTION_BG);
        voyageList.setFixedCellHeight(50);

        voyageList.setAutoscrolls(false);
        scroll = new JScrollPane(voyageList);

        add(voyageHeader, BorderLayout.NORTH);
        add(scroll, BorderLayout.CENTER);


        voyageList.addListSelectionListener(e -> {
            if (programmaticSelect)
                return;
            if (e.getValueIsAdjusting()) return;

            settingItem item = voyageList.getSelectedValue();
            if (item == null) return;

            // 非選択行が選ばれたら上方向の selectable 行に戻す（既存の挙動）
            if (!item.selectable) {
                int index = voyageList.getSelectedIndex();

                // ★ 同じ id のブロック内なら補正しない
                if (index > 0
                        && voyageListModel.get(index - 1).id == item.id) {
                    return;
                }

                voyageList.clearSelection();
                for (int i = index - 1; i >= 0; i--) {
                    if (voyageListModel.get(i).selectable) {
                        voyageList.setSelectedIndex(i);
                        return;
                    }
                }
                return;
            }

            int idx = voyageList.getSelectedIndex();
            Rectangle cell = voyageList.getCellBounds(idx, idx);
            if (cell == null) {
                return;
            }

            JViewport viewport = scroll.getViewport();

            // 選択行をビューポートの一番上に合わせる
            int newY = cell.y;

            // 最下部を超えないように制限
            int maxY = voyageList.getHeight() - viewport.getHeight();
            if (newY > maxY) {
                newY = Math.max(0, maxY);
            }

            viewport.setViewPosition(new Point(0, newY));

        });
    }

    // VoyageView.java に追加
    public void refreshFrom(OwnShipInfo ship) {

        voyageListModel.clear();

        voyageListModel.addElement(new settingItem(1, "1. NAV. STATUS", true));
        voyageListModel.addElement(new settingItem(1, "   ：" + ship.getNavStatus().getLabel(), false));

        voyageListModel.addElement(new settingItem(2, "2. DESTINATION", true));
        voyageListModel.addElement(new settingItem(2, "   ：" + ship.destination, false));

        voyageListModel.addElement(new settingItem(3, "3. ETA", true));

        String etaText = String.format(
                "%02d/%02d %02d:%02d",
                ship.getEtaMonth(),
                ship.getEtaDay(),
                ship.getEtaHour(),
                ship.getEtaMinute()
        );

        voyageListModel.addElement(
                new settingItem(3, "   ：" + etaText, false)
        );

        voyageListModel.addElement(new settingItem(4, "4. DRAUGHT", true));
        String draughtText;
        if (ship.draught >= 25.5) {
            draughtText = "25.5m or greater";
        } else {
            draughtText = String.format("%.1fm", ship.draught);
        }

        voyageListModel.addElement(
                new settingItem(4, "   ：" + draughtText, false)
        );


        voyageListModel.addElement(new settingItem(5, "5. PERSONS ON BOARD", true));
        voyageListModel.addElement(new settingItem(5, "   ：" + ship.persons, false));

        voyageListModel.addElement(new settingItem(6, "6. SHIP TYPE U.S.", true));
        voyageListModel.addElement(new settingItem(6, "   ：" + ship.shipTypeUS, false));

        voyageListModel.addElement(new settingItem(7, "7. TYPE OF SHIP AND CARGO", true));
        voyageListModel.addElement(new settingItem(7, "     TYPE OF SHIP : " + ship.getShipTypeDisplayText(), false));
        voyageListModel.addElement(new settingItem(7, "     CARGO TYPE   : " + ship.CargoType, false));
    }

    // ===== 外部 API =====

    public JList<settingItem> getList() {
        return voyageList;
    }

    public int getSelectedIndex() {
        return voyageList.getSelectedIndex();
    }

    public void setSelectedIndex(int index) {
        programmaticSelect = true;
        voyageList.setSelectedIndex(index);
        programmaticSelect = false;

        SwingUtilities.invokeLater(() -> {
            Rectangle r = voyageList.getCellBounds(index, index);
            if (r == null) {
                return;
            }

            JViewport viewport = scroll.getViewport();
            int newY = r.y;

            int maxY = voyageList.getHeight() - viewport.getHeight();
            if (newY > maxY) {
                newY = Math.max(0, maxY);
            }

            viewport.setViewPosition(new Point(0, newY));
        });

    }


    public int getItemCount() {
        return voyageListModel.size();
    }

    public settingItem getSelectedItem() {
        return voyageList.getSelectedValue();
    }

    public void requestFocus() {
        voyageList.requestFocusInWindow();
    }

    public void updateNavStatus(String value) {

        for (int i = 0; i < voyageListModel.size(); i++) {
            settingItem item = voyageListModel.get(i);

            // id==1 かつ 非 selectable 行（表示行）
            if (item.id == 1 && !item.selectable) {

                // 新しい settingItem を作って差し替え
                voyageListModel.set(
                        i,
                        new settingItem(1, "   ：" + value, false)
                );
                break;
            }
        }
    }


    public void selectItemById(int id) {
        for (int i = 0; i < voyageListModel.size(); i++) {
            if (voyageListModel.get(i).id == id
                    && voyageListModel.get(i).selectable) {
                setSelectedIndex(i);
                return;
            }
        }
    }

    
    // Destination を設定（表示更新）
    public void setDestination(String destination) {

        for (int i = 0; i < voyageListModel.size(); i++) {
            settingItem item = voyageListModel.get(i);

            // id==2 かつ 非 selectable 行が値行
            if (item.id == 2 && !item.selectable) {
                voyageListModel.set(i,
                        new settingItem(2, "   ：" + destination, false)
                );
                break;
            }
        }
    }

    // 3. ETA の selectable 行を選択
    public void selectEta() {
        for (int i = 0; i < voyageListModel.size(); i++) {
            settingItem item = voyageListModel.get(i);

            if (item.id == 3 && item.selectable) {
                setSelectedIndex(i);
                voyageList.requestFocusInWindow();
                return;
            }
        }
    }

    // ETA を設定（表示更新）
    public void setEta(String eta) {

        for (int i = 0; i < voyageListModel.size(); i++) {
            settingItem item = voyageListModel.get(i);

            // id==3 かつ 非 selectable 行が値行
            if (item.id == 3 && !item.selectable) {
                voyageListModel.set(
                        i,
                        new settingItem(3, "   ：" + eta, false)
                );
                break;
            }
        }
    }

    // DRAUGHT を設定（表示更新）
    public void setDraught(double draught) {

        String text;
        if (draught >= 25.5) {
            text = "25.5m or greater";
        } else {
            text = String.format("%.1fm", draught);
        }

        for (int i = 0; i < voyageListModel.size(); i++) {
            settingItem item = voyageListModel.get(i);

            if (item.id == 4 && !item.selectable) {
                voyageListModel.set(
                        i,
                        new settingItem(4, "   ：" + text, false)
                );
                break;
            }
        }
    }


    // PERSONS ON BOARD を設定（表示更新）
    public void setPersonsOnBoard(String persons) {

        for (int i = 0; i < voyageListModel.size(); i++) {
            settingItem item = voyageListModel.get(i);

            // id==5 かつ 非 selectable 行が値行
            if (item.id == 5 && !item.selectable) {
                voyageListModel.set(
                        i,
                        new settingItem(5, "   ：" + persons, false)
                );
                break;
            }
        }
    }

    // 船種の設定
    public void setShipTypeUS(String value) {
        for (int i = 0; i < voyageListModel.size(); i++) {
            settingItem item = voyageListModel.get(i);
            // id==6 かつ非selectable行
            if (item.id == 6 && !item.selectable) {
                voyageListModel.set(
                        i,
                        new settingItem(6, "   ：" + value, false)
                );
                break;
            }
        }
    }

    public void setShipType(OwnShipInfo ship) {
        String text = ship.getShipTypeDisplayText();

        for (int i = 0; i < voyageListModel.size(); i++) {
            settingItem item = voyageListModel.get(i);

            if (item.id == 7 && !item.selectable
                    && item.text.trim().startsWith("TYPE OF SHIP")) {

                voyageListModel.set(
                        i,
                        new settingItem(
                                7,
                                "     TYPE OF SHIP : " + text,
                                false
                        )
                );
                break;
            }
        }
    }



    public void setCargoType(String cargoType) {

        for (int i = 0; i < voyageListModel.size(); i++) {
            settingItem item = voyageListModel.get(i);

            if (item.id == 7 && !item.selectable
                    && item.text.trim().startsWith("CARGO TYPE")) {

                voyageListModel.set(
                        i,
                        new settingItem(
                                7,
                                "     CARGO TYPE   : " + cargoType,
                                false
                        )
                );
                break;
            }
        }
    }

    private int getLastSelectableIndex() {
        for (int i = voyageListModel.size() - 1; i >= 0; i--) {
            if (voyageListModel.get(i).selectable) {
                return i;
            }
        }
        return -1;
    }
}
