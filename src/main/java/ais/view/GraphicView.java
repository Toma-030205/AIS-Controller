package ais.view;

import ais.model.OwnShipInfo;
import ais.model.ShipInfo;
import ais.util.NavigationUtil;
import java.awt.*;
import javax.swing.*;




public class GraphicView extends JPanel {

    private JLabel header;

    // 左：他船情報
    private JLabel lblName;
    private JLabel lblBrg;
    private JLabel lblRng;
    private JLabel lblHdg;
    private JLabel lblSog;
    private JLabel lblCog;

    // 右：レーダ描画
    private JPanel canvasPanel;

    public GraphicView() {
        setLayout(new BorderLayout());

        /* ===== ヘッダ ===== */
        header = new JLabel("GRAPHIC RNG:3");
        header.setFont(new Font("Meiryo UI", Font.BOLD, 32));
        header.setOpaque(true);
        header.setBackground(UiTheme.HEADER_BG);
        header.setBorder(BorderFactory.createEmptyBorder(8, 12, 8, 12));
        add(header, BorderLayout.NORTH);

        /* ===== 左：情報パネル ===== */
        JPanel infoPanel = new JPanel();
        infoPanel.setPreferredSize(new Dimension(450, 0));
        infoPanel.setLayout(new BoxLayout(infoPanel, BoxLayout.Y_AXIS));
        infoPanel.setBorder(BorderFactory.createEmptyBorder(12, 12, 12, 12));

        lblName = createInfoLabel("----");
        lblBrg = createInfoLabel("BRG°  : ----");
        lblRng = createInfoLabel("RNG   : ----");
        lblHdg = createInfoLabel("HDG   : ----");
        lblSog = createInfoLabel("SOG   : ----");
        lblCog = createInfoLabel("COG   : ----");

        infoPanel.add(lblName);
        infoPanel.add(Box.createVerticalStrut(10));
        infoPanel.add(lblBrg);
        infoPanel.add(lblRng);
        infoPanel.add(lblHdg);
        infoPanel.add(lblSog);
        infoPanel.add(lblCog);

        /* ===== 右：レーダ画面 ===== */
        canvasPanel = new JPanel();
        canvasPanel.setBackground(Color.BLACK);
        canvasPanel.setFocusable(true);

        /* ===== 中央：左右合体 ===== */
        JPanel center = new JPanel(new BorderLayout());
        center.add(infoPanel, BorderLayout.WEST);
        center.add(canvasPanel, BorderLayout.CENTER);

        add(center, BorderLayout.CENTER);
    }

    private JLabel createInfoLabel(String text) {
        JLabel l = new JLabel(text);
        l.setFont(new Font("Meiryo UI", Font.BOLD, 32));
        return l;
    }

    /* ===== Controller 用 API ===== */
    public void setRange(int rangeNm) {
        header.setText("GRAPHIC RNG:" + rangeNm);
    }

    public void setTargetShip(OwnShipInfo own, ShipInfo ship) {
        if (own == null || ship == null) {
            lblName.setText("----");
            lblBrg.setText("BRG°  : ----");
            lblRng.setText("RNG   : ----");
            lblHdg.setText("HDG   : ----");
            lblSog.setText("SOG   : ----");
            lblCog.setText("COG   : ----");
            return;
        }

        // --- NavigationUtil を使用 ---
        double brg = NavigationUtil.calcBearingDeg(
                own.lat, own.lon,
                ship.lat, ship.lon
        );

        double rng = NavigationUtil.calcRangeNm(
                own.lat, own.lon,
                ship.lat, ship.lon
        );

        // --- 表示 ---
        lblName.setText(ship.vesselName);
        lblBrg.setText(String.format("BRG°  : %05.1f°", brg));
        lblRng.setText(String.format("RNG   : %6.2f NM", rng));
        lblHdg.setText(String.format("HDG   : %03.1f°", ship.trueHeading));
        lblSog.setText(String.format("SOG   : %.1f kn ", ship.sog));
        lblCog.setText(String.format("COG   : %.1f°", ship.cog));
    }


    public JPanel getCanvas() {
        return canvasPanel;
    }

    public void requestFocus() {
        canvasPanel.requestFocusInWindow();
    }
}
