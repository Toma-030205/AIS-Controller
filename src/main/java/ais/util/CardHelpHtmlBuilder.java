package ais.util;


public final class CardHelpHtmlBuilder {

    private CardHelpHtmlBuilder() {
    }

    public static String build(String cardId) {

        switch (cardId) {
            case "voyage":
                return wrap(
                    "航海情報設定画面",
                    "自船の航海計画および AIS に送信される航海関連情報を設定します。",
                    buildVoyageBody()
                );

            case "list":
                return wrap(
                    "周辺船舶一覧",
                    "受信した AIS 情報から周辺の船舶を一覧表示します。",
                    buildListBody()
                );

            case "menuList":
                return wrap(
                    "メニュー",
                    "各種設定や詳細画面へ移動します。",
                    ""
                );

            default:
                return wrap(
                    cardId,
                    "この画面の説明は未定義です。",
                    ""
                );
        }
    }

    /* =========================
     * 共通 HTML フレーム
     * ========================= */
    private static String wrap(String title, String desc, String body) {

        String css = ""
            + "body{font-family:'Segoe UI','Hiragino Kaku Gothic ProN','Meiryo',Sans-Serif;color:#222;}"
            + ".wrap{padding:12px;}"
            + "h2{margin:0 0 6px 0;font-size:18px;color:#0B66C3;}"
            + ".muted{color:#666;font-size:12px;}"
            + ".section{margin-top:12px;}"
            + ".section strong{display:block;color:#0B66C3;font-size:13px;margin-bottom:6px;}"
            + "ul{margin:6px 0 0 18px;}"
            + ".footer{margin-top:14px;font-size:12px;color:#555;border-top:1px solid #eee;padding-top:8px;}";

        StringBuilder sb = new StringBuilder();
        sb.append("<html><head><meta charset='utf-8'><style>")
          .append(css)
          .append("</style></head><body>");

        sb.append("<div class='wrap'>");
        sb.append("<h2>").append(title).append("</h2>");
        sb.append("<div class='muted'>").append(desc).append("</div>");

        if (body != null && !body.isEmpty()) {
            sb.append(body);
        }

        sb.append("<div class='footer'>関連：AIS 航海情報設定</div>");
        sb.append("</div></body></html>");

        return sb.toString();
    }

    /* =========================
 * 航海情報設定画面用 本文
 * ========================= */
    private static String buildVoyageBody() {

    return ""
        + "<div class='section'>"
        + "<strong>設定項目一覧</strong>"

        + "<ul>"

        + "<li><strong>航行状態（Navigation Status）</strong><br>"
        + "現在の航行状態（航行中、停泊中、操船不能など）を設定します。</li>"

        + "<li><strong>目的地（Destination）</strong><br>"
        + "自船が向かう港や地点を文字列として入力します。</li>"

        + "<li><strong>到着予定時刻（ETA）</strong><br>"
        + "目的地への到着予定日時を設定します。</li>"

        + "<li><strong>喫水（Draught）</strong><br>"
        + "船体が水面下に沈んでいる深さをメートル単位で設定します。</li>"

        + "<li><strong>乗船者数（Persons on Board）</strong><br>"
        + "乗組員および乗客を含めた総人数を入力します。</li>"

        + "<li><strong>船種コード（Ship Type US）</strong><br>"
        + "船種コードを米国基準にするかを設定します。</li>"

        + "<li><strong>船種および貨物種別（Type of Ship and Cargo）</strong><br>"
        + "船舶の種別および積載している貨物の分類を設定します。</li>"

        + "</ul>"
        + "</div>"

        // 注意事項
        + "<div class='section'>"
        + "<strong>注意事項</strong>"
        + "<ul>"
        + "<li class='warn'>"
        + "各項目を入力しただけでは設定は反映されません。"
        + "</li>"
        + "<li>"
        + "入力内容を確定するには、"
        + "<span class='kbd'>SUB</span> メニューから "
        + "<span class='kbd'>SET</span> を選択してください。"
        + "</li>"
        + "</ul>"
        + "</div>"
        // 操作方法
    + "<div class='section'>"
    + "<strong>操作方法</strong>"
    + "<ul>"
    + "<li>上下ボタンを押して設定する項目を選択します。</li>"
    + "<li><span class='kbd'>ENTER</span> を押すと、選択した項目の設定内容を変更または入力する状態になります。</li>"
    + "<li><span class='kbd'>CLR</span> ボタンを押すと、メニュー画面へ戻ります。</li>"
    + "<li><span class='kbd'>SUB</span> ボタンを押すと、サブメニュー画面を表示します。</li>"
    + "</ul>"
    + "</div>";
}

/* =========================
 * 周辺船舶一覧画面用 本文
 * ========================= */
private static String buildListBody() {

    return ""
        + "<div class='section'>"
        + "<strong>表示項目の説明</strong>"

        + "<ul>"

        + "<li><strong>BRG°（Bearing）</strong><br>"
        + "自船から見た他船の方位を度（°）で表示します。</li>"

        + "<li><strong>RNG nm（Range）</strong><br>"
        + "自船から他船までの距離を海里（nautical mile）で表示します。</li>"

        + "<li><strong>ET min</strong><br>"
        + "最後に他船の AIS データを受信してからの経過時間を分単位で表示します。"
        + "受信から <strong>7 分以上経過した船舶</strong> は、"
        + "情報の鮮度低下を考慮して一覧表示から除外されます。</li>"

        + "<li><strong>NAME</strong><br>"
        + "他船の船名を表示します。</li>"

        + "</ul>"
        + "</div>"

        + "<div class='section'>"
        + "<strong>操作方法</strong>"
        + "<ul>"
        + "<li>上下ボタンでカーソルを上下に移動し、表示対象の他船を選択します。</li>"
        + "<li><span class='kbd'>ENTER</span> を押すと、選択中の他船の詳細情報を表示します。</li>"
        + "<li><span class='kbd'>DISP</span> を押すと、グラフィック画面を表示します。</li>"
        + "</ul>"
        + "</div>";
}

}
