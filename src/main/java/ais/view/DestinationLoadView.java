package ais.view;

import java.awt.*;
import java.util.List;
import javax.swing.*;



public class DestinationLoadView extends JPanel {

    /* ===============================
     * 内部コンポーネント
     * =============================== */
    private JList<String> destList;
    private DefaultListModel<String> listModel;
    
    /* ===============================
     * コールバック
     * =============================== */
    private DestinationLoadListener listener;

    /* ===============================
     * コンストラクタ
     * =============================== */
    public DestinationLoadView() {
        setLayout(new BorderLayout());
        setBackground(UiTheme.HEADER_BG);

        JLabel header = new JLabel("DEST LOAD");
        header.setFont(new Font("SansSerif", Font.BOLD, 24));
        header.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        add(header, BorderLayout.NORTH);

        listModel = new DefaultListModel<>();
        destList = new JList<>(listModel);
        destList.setFont(new Font("Meiryo", Font.PLAIN, 18));
        destList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        destList.setVisibleRowCount(5);

        JScrollPane sp = new JScrollPane(destList);
        sp.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        add(sp, BorderLayout.CENTER);
    }

    /* ===============================
     * 初期化（表示内容設定）
     * =============================== */
    public void setDestinations(List<String> destinations) {
        listModel.clear();

        for (String d : destinations) {
            listModel.addElement(d);
        }

        if (!listModel.isEmpty()) {
            destList.setSelectedIndex(0);
        }
    }

    public void setSelectedIndex(int index) {
        destList.setSelectedIndex(index);
        destList.ensureIndexIsVisible(index);
    }


    /* ===============================
     * 選択取得
     * =============================== */
    public String getSelectedDestination() {
        return destList.getSelectedValue();
    }

    /* ===============================
     * カーソル操作
     * =============================== */
    public void selectNext() {
        int idx = destList.getSelectedIndex();
        if (idx < listModel.size() - 1) {
            destList.setSelectedIndex(idx + 1);
            destList.ensureIndexIsVisible(idx + 1);
        }
    }

    public void selectPrev() {
        int idx = destList.getSelectedIndex();
        if (idx > 0) {
            destList.setSelectedIndex(idx - 1);
            destList.ensureIndexIsVisible(idx - 1);
        }
    }

    /* ===============================
     * Listener
     * =============================== */
    public void setListener(DestinationLoadListener listener) {
        this.listener = listener;
    }

    public void fireEnter() {
        if (listener != null) {
            listener.onSelect(getSelectedDestination());
        }
    }

    public void fireCancel() {
        if (listener != null) {
            listener.onCancel();
        }
    }

    /* ===============================
     * Listener IF
     * =============================== */
    public interface DestinationLoadListener {
        void onSelect(String destination);
        void onCancel();
    }
}
