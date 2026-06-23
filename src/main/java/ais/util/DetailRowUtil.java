package ais.util;

import java.awt.*;
import javax.swing.*;



public class DetailRowUtil {

    private static final Font ROW_FONT =
            new Font("Meiryo UI", Font.PLAIN, 32);

    public static void addRow(JPanel panel, String title, Object value) {

        JPanel row = new JPanel(new BorderLayout());
        row.setBorder(BorderFactory.createEmptyBorder(4, 12, 4, 12));

        JLabel titleLabel = new JLabel(title + " :");
        titleLabel.setFont(ROW_FONT);

        JLabel valueLabel = new JLabel(
                value == null ? "" : value.toString()
        );
        valueLabel.setFont(ROW_FONT);

        row.add(titleLabel, BorderLayout.WEST);
        row.add(valueLabel, BorderLayout.CENTER);

        panel.add(row);
    }

    private DetailRowUtil() {
        // インスタンス化禁止
    }
}
