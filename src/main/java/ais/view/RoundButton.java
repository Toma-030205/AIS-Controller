package ais.view;

import java.awt.*;
import java.awt.geom.Ellipse2D;
import javax.swing.*;



/**
 * 丸い見た目のカスタムボタンを生成するクラス
 */
public class RoundButton extends JButton {
    private static final int DEFAULT_SIZE = 56;

    public RoundButton(String label) {
        super(label);
        setFocusPainted(false);
        setBorderPainted(false);
        setContentAreaFilled(false);
        setOpaque(false);
        setForeground(Color.BLACK);
        setBackground(new Color(0xE6E6E6));
        setFont(getFont().deriveFont(Font.BOLD, 13f));
        setFocusable(true);
    }

    @Override
    public Dimension getPreferredSize() {
        Dimension d = super.getPreferredSize();
        int size = Math.max(Math.max(d.width, d.height), DEFAULT_SIZE);
        return new Dimension(size, size);
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();
        try {
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

            int w = getWidth();
            int h = getHeight();
            int pad = 4;
            int diameter = Math.min(w, h) - pad * 2;
            int x = (w - diameter) / 2;
            int y = (h - diameter) / 2;

            ButtonModel m = getModel();
            Color fill;
            if (!isEnabled()) {
                fill = new Color(0xD3D3D3);
            } else if (m.isPressed()) {
                fill = new Color(0xBFBFBF);
            } else if (m.isRollover()) {
                fill = new Color(0xF5F5F5);
            } else {
                fill = getBackground();
            }

            g2.setColor(fill);
            g2.fillOval(x, y, diameter, diameter);
            g2.setColor(new Color(0x606060));
            g2.drawOval(x, y, diameter, diameter);

            // テキスト描画
            String text = getText();
            if (text != null && !text.isEmpty()) {
                g2.setColor(getForeground());
                FontMetrics fm = g2.getFontMetrics();
                int strW = fm.stringWidth(text);
                int strH = fm.getAscent();
                g2.drawString(text, (w - strW) / 2, (h + strH) / 2 - 2);
            }

            // フォーカスリング
            if (isFocusOwner()) {
                g2.setColor(new Color(0, 120, 215, 90));
                g2.setStroke(new BasicStroke(2f));
                g2.drawOval(x - 3, y - 3, diameter + 6, diameter + 6);
            }
        } finally {
            g2.dispose();
        }
    }

    @Override
    public boolean contains(int x, int y) {
        int w = getWidth();
        int h = getHeight();
        int pad = 4;
        int diameter = Math.min(w, h) - pad * 2;
        Ellipse2D e = new Ellipse2D.Double((w - diameter) / 2.0, (h - diameter) / 2.0, diameter, diameter);
        return e.contains(x, y);
    }
}
