package ais.view;

import java.awt.Color;
import java.awt.Font;
import javax.swing.BorderFactory;
import javax.swing.border.Border;

public final class UiTheme {
    public static final String FONT_FAMILY = "Meiryo UI";
    public static final Color HEADER_BG = UiTheme.HEADER_BG;
    public static final Color SUB_HEADER_BG = new Color(230, 230, 230);
    public static final Color SELECTION_BG = UiTheme.SELECTION_BG;
    public static final Color INPUT_CURSOR_BG = UiTheme.INPUT_CURSOR_BG;
    public static final Color PANEL_BG = UiTheme.PANEL_BG;

    private UiTheme() {
    }

    public static Font headerFont() {
        return new Font(FONT_FAMILY, Font.BOLD, 32);
    }

    public static Font plainFont(int size) {
        return new Font(FONT_FAMILY, Font.PLAIN, size);
    }

    public static Font boldFont(int size) {
        return new Font(FONT_FAMILY, Font.BOLD, size);
    }

    public static Border headerBorder() {
        return BorderFactory.createEmptyBorder(8, 12, 8, 12);
    }
}
