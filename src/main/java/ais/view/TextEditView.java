package ais.view;

import ais.model.TextEditModel;
import java.awt.*;
import javax.swing.*;
import javax.swing.border.Border;




public class TextEditView extends JPanel {

    private  TextEditModel model;

    /* =========================
     * 表示部品
     * ========================= */
    private JLabel headerLabel;
    private JTextArea textArea;
    private JLabel countLabel;

    private JButton[][] buttonMap;

    /* =========================
     * カーソル状態
     * ========================= */
    private int cursorRow = 0;
    private int cursorCol = 0;
    private static final int COLS = 54;
    private static final int ROWS = 3;
    private static final int DISPLAY_LEN = COLS * ROWS;

    /* =========================
     * 文字パッド定義
     * ========================= */
    private static final String[][] KEY_MAP = {
        {"A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U"},
        {"V", "W", "X", "Y", "Z", ".", "0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "[", "\"",
            "]", "_", "”"},
        {"#", "$", "%", "&", "'", "(", ")", "?", "@", "+", "-", "*", "/", "^", ",", ":", ";", "<",
            "=", ">", "!"},
        {"[SPACE]", "[C]", "[AC]", "[OK]", "[EXIT]"}
    };

    private static final int KEY_HEIGHT = 42;
    private static final int CONTROL_KEY_HEIGHT = 32;

    /* =========================
     * 枠線定義
     * ========================= */
    private static final Border CURSOR_BORDER
            = BorderFactory.createLineBorder(Color.BLACK, 2);

    private static final Border NORMAL_BORDER
            = BorderFactory.createEmptyBorder(2, 2, 2, 2);

    /* =========================
     * リスナ
     * ========================= */
    public interface Listener {

        void onOk(String text);

        void onExit();
    }

    private Listener listener;

    public void setListener(Listener listener) {
        this.listener = listener;
    }

    /* =========================
     * コンストラクタ
     * ========================= */
    public TextEditView(TextEditModel model) {
        this.model = model;
        setLayout(new BorderLayout());
        initComponents();
        resetCursor();
    }

    /* =========================
     * 初期化
     * ========================= */
    private void initComponents() {

        headerLabel = new JLabel("TEXT EDIT", SwingConstants.LEFT);
        headerLabel.setFont(new Font("Meiryo UI", Font.BOLD, 32));
        headerLabel.setOpaque(true);
        headerLabel.setBackground(UiTheme.HEADER_BG);
        headerLabel.setBorder(BorderFactory.createEmptyBorder(8, 12, 8, 12));
        add(headerLabel, BorderLayout.NORTH);

        JPanel center = new JPanel();
        center.setLayout(new BoxLayout(center, BoxLayout.Y_AXIS));
        add(center, BorderLayout.CENTER);

        textArea = new JTextArea(ROWS, COLS);
        textArea.setFont(new Font("Consolas", Font.PLAIN, 26));
        textArea.setEditable(false);
        textArea.setLineWrap(false);      // 自動改行しない
        textArea.setWrapStyleWord(false);
        textArea.setFocusable(false);
        textArea.setOpaque(false);        // 背景を JPanel に合わせる
        textArea.setBorder(
                BorderFactory.createEmptyBorder(2, 15, 2, 8)
        );
        textArea.setAlignmentX(Component.LEFT_ALIGNMENT);

        center.add(textArea);


        JPanel pad = createCharacterPad();
        pad.setAlignmentX(Component.LEFT_ALIGNMENT);
        pad.setMaximumSize(pad.getPreferredSize());
        center.add(pad);

        countLabel = new JLabel();
        countLabel.setFont(new Font("Meiryo UI", Font.BOLD, 20));
        countLabel.setBorder(BorderFactory.createEmptyBorder(3, 12, 3, 12));
        countLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        center.add(countLabel);
    }

    /* =========================
     * 文字パッド生成
     * ========================= */
    private JPanel createCharacterPad() {

        JPanel outer = new JPanel();
        outer.setLayout(new BoxLayout(outer, BoxLayout.Y_AXIS));
        outer.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));

        buttonMap = new JButton[KEY_MAP.length][];

        JPanel alphaPanel = new JPanel(new GridLayout(3, 21, 4, 1));
        for (int r = 0; r < 3; r++) {
            buttonMap[r] = new JButton[KEY_MAP[r].length];
            for (int c = 0; c < KEY_MAP[r].length; c++) {
                JButton btn = createButton(KEY_MAP[r][c], 42, KEY_HEIGHT);
                buttonMap[r][c] = btn;
                alphaPanel.add(btn);
            }
        }

        JPanel controlPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 12, 1));
        int r = 3;
        buttonMap[r] = new JButton[KEY_MAP[r].length];
        for (int c = 0; c < KEY_MAP[r].length; c++) {
            JButton btn = createButton(KEY_MAP[r][c], 90, CONTROL_KEY_HEIGHT);
            buttonMap[r][c] = btn;
            controlPanel.add(btn);
        }

        outer.add(alphaPanel);
        outer.add(controlPanel);
        return outer;
    }

    private JButton createButton(String text, int width, int height) {
        JButton btn = new JButton(text);
        btn.setFont(new Font("Meiryo UI", Font.BOLD, 18));
        btn.setPreferredSize(new Dimension(width, height));
        btn.setMinimumSize(new Dimension(width, height));
        btn.setMaximumSize(new Dimension(width, height));
        btn.setFocusable(false);
        btn.setOpaque(false);
        btn.setContentAreaFilled(false);
        btn.setBorder(NORMAL_BORDER);
        return btn;
    }


    /* =========================
     * カーソル操作
     * ========================= */
    public void moveUp() {
        if (cursorRow > 0) {
            cursorRow--;
            clampCursorCol();
            updateCursorVisual();
        }
    }

    public void moveDown() {
        if (cursorRow < KEY_MAP.length - 1) {
            cursorRow++;
            clampCursorCol();
            updateCursorVisual();
        }
    }

    public void moveLeft() {
        if (cursorCol > 0) {
            cursorCol--;
            updateCursorVisual();
        }
    }

    public void moveRight() {
        if (cursorCol < KEY_MAP[cursorRow].length - 1) {
            cursorCol++;
            updateCursorVisual();
        }
    }

    /* =========================
     * ENTER 押下
     * ========================= */
    public void pressEnter() {

        String key = KEY_MAP[cursorRow][cursorCol];

        switch (key) {
            case "[C]":
                model.backspace();
                break;

            case "[AC]":
                model.clear();
                break;

            case "[OK]":
                if (listener != null) {
                    listener.onOk(model.getText());
                }
                return;

            case "[EXIT]":
                if (listener != null) {
                    listener.onExit();
                }
                return;

            case "[SPACE]":
                model.append(" ");
                break;

            default:
                model.append(key);
                break;
        }
        refresh();
    }

    /* =========================
     * 表示更新
     * ========================= */
    public void refresh() {

        String text = model.getText();

        // 表示対象文字列（末尾 DISPLAY_LEN 文字）
        String visible;
        if (text.length() <= DISPLAY_LEN) {
            visible = text;
        } else {
            visible = text.substring(text.length() - DISPLAY_LEN);
        }

        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < visible.length(); i++) {
            sb.append(visible.charAt(i));

            // 54文字ごとに改行（最後は除く）
            if ((i + 1) % COLS == 0 && i + 1 < visible.length()) {
                sb.append('\n');
            }
        }

        sb.append('■');

        textArea.setText(sb.toString());
        countLabel.setText(model.length() + " / " + model.getMaxLength());

        revalidate();
        repaint();
    }


    // TextEditView に追加
    public void setModel(TextEditModel model) {
        this.model = model;
        refresh();
    }


    /* =========================
     * カーソル表示
     * ========================= */
    private void updateCursorVisual() {

        if (buttonMap == null) {
            return;
        }

        for (int r = 0; r < buttonMap.length; r++) {
            for (int c = 0; c < buttonMap[r].length; c++) {
                buttonMap[r][c].setBorder(NORMAL_BORDER);
            }
        }
        buttonMap[cursorRow][cursorCol].setBorder(CURSOR_BORDER);
    }

    private void clampCursorCol() {
        int max = KEY_MAP[cursorRow].length - 1;
        if (cursorCol > max) {
            cursorCol = max;
        }
    }

    /* =========================
     * 初期化
     * ========================= */
    public void resetCursor() {
        cursorRow = 0;
        cursorCol = 0;
        updateCursorVisual();
    }
}
