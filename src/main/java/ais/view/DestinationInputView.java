package ais.view;

import ais.app.AISMain2;
import ais.model.DestinationInputModel;
import java.awt.*;
import javax.swing.*;
import javax.swing.border.*;





public class DestinationInputView extends JPanel {

    private final DestinationInputModel model = new DestinationInputModel();
    // 表示用（※ 今回はダミー表示）
    private JLabel destinationValueLabel;
    private JLabel countLabel;

    // 現在選択中のキー位置
    private int cursorRow = 0;
    private int cursorCol = 0;
    private JButton[][] buttonMap;
    // 文字パッド定義（行列）
    private String[][] keyMap = {
        {"A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U"},
        {"V", "W", "X", "Y", "Z", ".", "0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "[", "\"",
            "]", "_", "”"},
        {"#", "$", "%", "&", "'", "(", ")", "?", "@", "+", "-", "*", "/", "^", ",", ":", ";", "<", "=",
            ">", "!"},
        {"[SPACE]", "[C]", "[AC]", "[OK]", "[EXIT]"}
    };


    public DestinationInputView() {
        setLayout(new BorderLayout());
        initComponents();
    }

    private void initComponents() {

        /* =========================
         * 上部：DESTINATION ヘッダ
         * ========================= */
        JLabel header = new JLabel("DESTINATION");
        header.setFont(new Font("Meiryo UI", Font.BOLD, 32));
        header.setOpaque(true);
        header.setBackground(UiTheme.HEADER_BG);
        header.setBorder(BorderFactory.createEmptyBorder(8, 12, 8, 12));

        add(header, BorderLayout.NORTH);

        /* =========================
         * 中央全体（縦積み）
         * ========================= */
        JPanel centerPanel = new JPanel();
        centerPanel.setLayout(new BoxLayout(centerPanel, BoxLayout.Y_AXIS));

        /* ---- 入力文字表示 ---- */
        destinationValueLabel = new JLabel("■");
        destinationValueLabel.setFont(new Font("Consolas", Font.PLAIN, 28));
        destinationValueLabel.setBorder(
                BorderFactory.createEmptyBorder(10, 12, 10, 12)
        );
        destinationValueLabel.setPreferredSize(new Dimension(600, 48));
        destinationValueLabel.setMinimumSize(new Dimension(600, 48));
        destinationValueLabel.setMaximumSize(new Dimension(Short.MAX_VALUE, 48));
        destinationValueLabel.setAlignmentX(Component.LEFT_ALIGNMENT);

        centerPanel.add(Box.createVerticalStrut(2));
        centerPanel.add(destinationValueLabel);

        /* ---- 文字パッド ---- */
        JPanel charPadPanel = createCharacterPadPanel();
        charPadPanel.setAlignmentX(Component.LEFT_ALIGNMENT);

        centerPanel.add(charPadPanel);
        centerPanel.add(Box.createVerticalStrut(8));

        /* ---- 文字数カウンタ ---- */
        countLabel = new JLabel("0 / 20");
        countLabel.setFont(new Font("Meiryo UI", Font.BOLD, 25));
        countLabel.setBorder(BorderFactory.createEmptyBorder(4, 4, 4, 4));
        countLabel.setAlignmentX(Component.LEFT_ALIGNMENT);

        centerPanel.add(countLabel);

        add(centerPanel, BorderLayout.CENTER);
    }

    /* =========================
     * 文字パッド（見た目のみ）
     * 4行構成
     * ========================= */
    private JPanel createCharacterPadPanel() {

        JPanel outer = new JPanel();
        outer.setLayout(new BoxLayout(outer, BoxLayout.Y_AXIS));
        outer.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));

        // keyMap に対応した buttonMap を生成
        buttonMap = new JButton[keyMap.length][];

        /* =========================
     * 上3行：通常キー
     * ========================= */
        JPanel alphaPanel = new JPanel(new GridLayout(3, 21, 4, 4));

        for (int row = 0; row < 3; row++) {

            buttonMap[row] = new JButton[keyMap[row].length];

            for (int col = 0; col < keyMap[row].length; col++) {
                JButton btn = createCharButton(keyMap[row][col]);
                buttonMap[row][col] = btn;
                alphaPanel.add(btn);
            }
        }

        /* =========================
     * 下段：制御キー
     * ========================= */
        JPanel controlPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 12, 6));
        controlPanel.setOpaque(false);

        int controlRow = 3;
        buttonMap[controlRow] = new JButton[keyMap[controlRow].length];

        for (int col = 0; col < keyMap[controlRow].length; col++) {
            JButton btn = createControlButton(keyMap[controlRow][col]);
            buttonMap[controlRow][col] = btn;
            controlPanel.add(btn);
        }

        outer.add(alphaPanel);
        outer.add(controlPanel);

        // ★ 初期カーソル表示
        updateCursorVisual();

        return outer;
    }


    private JButton createCharButton(String text) {
        JButton btn = new JButton(text);
        btn.setFont(new Font("Meiryo UI", Font.BOLD, 18));
        btn.setPreferredSize(new Dimension(42, 42));
        btn.setMargin(new Insets(2, 2, 2, 2));
        btn.setFocusPainted(false);

        btn.setOpaque(false);
        btn.setContentAreaFilled(false);
        btn.setBorderPainted(true);

        btn.addActionListener(e -> {
            model.append(text);
            refreshDisplay();
        });

        return btn;
    }

    public void moveUp() {
        if (cursorRow > 0) {
            cursorRow--;
            clampCursorCol();
            updateCursorVisual();
        }
    }

    public void moveDown() {
        if (cursorRow < keyMap.length - 1) {
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
        int maxCol = keyMap[cursorRow].length - 1;
        if (cursorCol < maxCol) {
            cursorCol++;
            updateCursorVisual();
        }
    }


    private void normalizeCursor() {
        if (cursorCol >= keyMap[cursorRow].length) {
            cursorCol = keyMap[cursorRow].length - 1;
        }
    }

    private void clampCursorCol() {
        int maxCol = keyMap[cursorRow].length - 1;
        if (cursorCol > maxCol) {
            cursorCol = maxCol;
        }
    }


    public void pressEnter() {

        String key = keyMap[cursorRow][cursorCol];

        switch (key) {
            case "[C]":
                model.backspace();
                refreshDisplay();
                break;

            case "[AC]":
                model.clear();
                refreshDisplay();
                break;

            case "[OK]":
                if (listener != null) {
                    listener.onOk(model.getText());
                }
                break;

            case "[EXIT]":
                if (listener != null) {
                    listener.onExit();
                }
                break;

            case "[SPACE]":
                model.append(" ");
                break;

            default:
                model.append(key);
                refreshDisplay();
                break;
        }
    }

    private static final Border CURSOR_BORDER
            = BorderFactory.createLineBorder(Color.BLACK, 2);

    private static final Border NO_BORDER
            = BorderFactory.createEmptyBorder(2, 2, 2, 2);

    private void updateCursorVisual() {

        for (int r = 0; r < buttonMap.length; r++) {
            for (int c = 0; c < buttonMap[r].length; c++) {
                buttonMap[r][c].setBorder(NO_BORDER);
            }
        }

        buttonMap[cursorRow][cursorCol].setBorder(CURSOR_BORDER);
    }



    private JButton createControlButton(String text) {
        JButton btn = new JButton(text);
        btn.setFont(new Font("Meiryo UI", Font.BOLD, 18));
        btn.setPreferredSize(new Dimension(90, 42));
        btn.setMargin(new Insets(2, 2, 2, 2));
        btn.setFocusPainted(false);

        // 透過設定
        btn.setOpaque(false);
        btn.setContentAreaFilled(false);
        btn.setBorderPainted(true);

        return btn;
    }

    
    
    private void refreshDisplay() {
        destinationValueLabel.setText(model.getText() + "■");
        countLabel.setText(model.length() + " / 20");
    }

    public interface DestinationInputListener {

        void onOk(String destination);

        void onExit();
    }

    private DestinationInputListener listener;

    public void setListener(DestinationInputListener listener) {
        this.listener = listener;
    }

    public void resetCursor() {
        cursorRow = 0;
        cursorCol = 0;
        updateCursorVisual();
    }


    /* =========================
     * AISMain2 から呼ぶ最小API
     * ========================= */
    @Override
    public void requestFocus() {
        requestFocusInWindow();
    }
}
