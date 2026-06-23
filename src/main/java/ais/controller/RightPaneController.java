package ais.controller;

import ais.app.ScreenId;
import ais.util.ButtonHelpHtmlBuilder;
import ais.util.CardHelpHtmlBuilder;
import javax.swing.JEditorPane;



public class RightPaneController {

    private final JEditorPane infoPane;
    private String currentCardId;

    public RightPaneController(JEditorPane infoPane) {
        this.infoPane = infoPane;
    }

    /* =========================
     * ボタン右クリック説明
     * ========================= */
    public void showButtonHelp(String label, String desc, String extraNote) {
        String html = ButtonHelpHtmlBuilder.build(label, desc, extraNote);
        setHtml(html);
    }

    /* =========================
     * （将来用）カード説明
     * ========================= */
    public void showCardHelp(String cardId) {
        this.currentCardId = cardId;
        
        String html = CardHelpHtmlBuilder.build(cardId);
        if (html == null) {
            html = emptyHtml();
        }
        setHtml(html);
    }

    public void showCardHelp(ScreenId screenId) {
        showCardHelp(screenId.cardName());
    }

    /* =========================
     * クリア
     * ========================= */
    public void clear() {
        if (currentCardId != null) {
            showCardHelp(currentCardId);
        } else {
            setHtml("<html><body></body></html>");
    }
    }

    /* =========================
     * 内部共通処理
     * ========================= */
    private void setHtml(String html) {
        infoPane.setText(html);
        infoPane.setCaretPosition(0);
    }

    private String emptyHtml() {
        return "<html><body style='padding:12px;'></body></html>";
    }
}
