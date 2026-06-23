package ais.view;

import java.awt.*;
import javax.swing.*;
import javax.swing.table.*;



public class ShipListView extends JPanel {

    private JTable table;
    private DefaultTableModel tableModel;
    private JLabel countLabel;

    public ShipListView() {
        setLayout(new BorderLayout());
        initComponents();
    }

    private void initComponents() {

        // ===== ヘッダ =====
        JLabel header = new JLabel("LIST  SORT : RANGE");
        header.setFont(new Font("Meiryo UI", Font.BOLD, 32));
        header.setOpaque(true);
        header.setBackground(UiTheme.HEADER_BG);
        header.setBorder(BorderFactory.createEmptyBorder(8, 12, 8, 12));
        add(header, BorderLayout.NORTH);

        // ===== テーブル =====
        String[] colNames = {"BRG°", "RNGnm", "ETmin", "NAME", "MMSI"};

        tableModel = new DefaultTableModel(colNames, 0) {
            @Override public boolean isCellEditable(int r, int c) {
                return false;
            }
        };

        table = new JTable(tableModel);

        // MMSI 列非表示
        TableColumn mmsiCol = table.getColumnModel().getColumn(4);
        mmsiCol.setMinWidth(0);
        mmsiCol.setMaxWidth(0);
        mmsiCol.setPreferredWidth(0);

        table.setFont(new Font("Meiryo UI", Font.PLAIN, 22));
        table.setRowHeight(43);
        table.setShowGrid(true);
        table.setGridColor(Color.GRAY);
        table.setSelectionBackground(UiTheme.SELECTION_BG);

        // 列幅固定
        table.getColumnModel().getColumn(0).setPreferredWidth(50);
        table.getColumnModel().getColumn(1).setPreferredWidth(80);
        table.getColumnModel().getColumn(2).setPreferredWidth(80);
        table.getColumnModel().getColumn(3).setPreferredWidth(240);

        DefaultTableCellRenderer center = new DefaultTableCellRenderer();
        center.setHorizontalAlignment(JLabel.CENTER);
        for (int i = 0; i < table.getColumnCount(); i++) {
            table.getColumnModel().getColumn(i).setCellRenderer(center);
        }

        JTableHeader th = table.getTableHeader();
        th.setFont(new Font("Meiryo UI", Font.BOLD, 22));
        th.setBackground(new Color(230, 230, 230));
        th.setForeground(Color.DARK_GRAY);

        add(new JScrollPane(table), BorderLayout.CENTER);

        // ===== 件数表示 =====
        countLabel = new JLabel("0 / 0", SwingConstants.LEFT);
        countLabel.setFont(new Font("Meiryo UI", Font.BOLD, 24));
        countLabel.setOpaque(true);
        countLabel.setBackground(UiTheme.HEADER_BG);
        countLabel.setBorder(BorderFactory.createEmptyBorder(8, 12, 8, 12));
        add(countLabel, BorderLayout.SOUTH);
    }

    // ===== 外部操作用 API =====

    public JTable getTable() {
        return table;
    }

    public DefaultTableModel getTableModel() {
        return tableModel;
    }

    private void updateCountLabel() {
        int total = tableModel.getRowCount();
        int selected = table.getSelectedRow();

        int current = (selected >= 0) ? selected + 1 : 0;

        countLabel.setText(current + " / " + total);
    }


    public void updateTable(
            java.util.List<Object[]> rows,
            int selectedRow
    ) {
        DefaultTableModel model = tableModel;
        JTable table = this.table;

        model.setRowCount(0);

        for (Object[] row : rows) {
            model.addRow(row);
        }

        if (selectedRow >= 0 && selectedRow < model.getRowCount()) {
            table.setRowSelectionInterval(selectedRow, selectedRow);

            Rectangle rect = table.getCellRect(selectedRow, 0, true);
            table.scrollRectToVisible(rect);
        }

        updateCountLabel(); // ← 最後にまとめて更新
    }


    public int getSelectedMmsi() {
        int row = table.getSelectedRow();
        if (row < 0) {
            return -1;
        }

        Object v = table.getValueAt(row, 4); // MMSI 列
        return (v instanceof Number) ? ((Number) v).intValue()
                : Integer.parseInt(v.toString());
    }

}