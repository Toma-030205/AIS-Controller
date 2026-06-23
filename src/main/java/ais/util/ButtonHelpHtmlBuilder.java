package ais.util;


public final class ButtonHelpHtmlBuilder {

    private ButtonHelpHtmlBuilder() {
        // インスタンス化禁止
    }

    public static String build(String label, String desc, String extraNote) {
        String css = ""
            + "body{font-family: 'Segoe UI','Hiragino Kaku Gothic ProN','Meiryo',Sans-Serif;color:#222;}"
            + ".wrap{padding:12px;}"
            + "h2{margin:0 0 8px 0;font-size:18px;color:#0B66C3;}"
            + ".muted{color:#666;font-size:12px;}"
            + ".toc{margin-top:8px;padding:8px;border:1px solid #e6eef7;background:#fbfdff;border-radius:6px;}"
            + ".toc strong{color:#0B66C3;display:block;margin-bottom:6px;}"
            + ".toc ul{margin:0 0 0 18px;padding:0;}"
            + ".toc a{color:#0B66C3;text-decoration:none;}"
            + ".section{margin-top:12px;}"
            + ".section strong{display:block;color:#0B66C3;font-size:13px;margin-bottom:6px;}"
            + ".subtitle{font-weight:700;margin-top:6px;color:#227A5B;}"
            + ".kbd{display:inline-block;padding:3px 7px;border-radius:4px;border:1px solid #9aa7b0;background:#fbfdff;font-family:monospace;font-size:12px;}"
            + "ul{margin:6px 0 0 18px;}"
            + ".warn{color:#b72020;font-weight:700;}"
            + ".footer{margin-top:14px;font-size:12px;color:#555;border-top:1px solid #eee;padding-top:8px;}"
            ;

        StringBuilder sb = new StringBuilder();
        sb.append("<html><head><meta charset='utf-8'><style>").append(css).append("</style></head><body>");
        sb.append("<div class='wrap'>");
        sb.append("<h2>").append(label).append("</h2>");
        sb.append("<div class='muted'>").append(desc == null ? "" : desc).append("</div>");

        // ボタン毎に異なるTOCを出力（関連項目のみ）
        sb.append("<div class='toc'><strong>目次</strong><ul>");
        if ("MENU".equals(label)) {
            sb.append("<li><a href=\"#voyage\">航海データ</a></li>");
            sb.append("<li><a href=\"#message\">メッセージ</a></li>");
            sb.append("<li><a href=\"#maintenance\">保守</a></li>");
            sb.append("<li><a href=\"#setup\">設定</a></li>");
        } else if ("DISP".equals(label)) {
            sb.append("<li><a href=\"#listsort\">リストソート</a></li>");
            sb.append("<li><a href=\"#posntime\">位置と時刻</a></li>");
            sb.append("<li><a href=\"#graphic\">グラフィック</a></li>");
            sb.append("<li><a href=\"#ownship\">自船情報</a></li>");
        } else if ("SUB".equals(label)) {
            sb.append("<li><a href=\"#subexamples\">SUB（画面別例）</a></li>");
            sb.append("<li><a href=\"#sublist\">SUB（LIST例）</a></li>");
            sb.append("<li><a href=\"#subposn\">SUB（位置/時刻例）</a></li>");
            sb.append("<li><a href=\"#submsg\">SUB（メッセージ例）</a></li>");
        } else {
            sb.append("<li><a href=\"#overview\">機能概要</a></li>");
            sb.append("<li><a href=\"#howto\">操作手順</a></li>");
            sb.append("<li><a href=\"#notes\">注意事項</a></li>");
        }
        sb.append("</ul></div>");

        // 機能概要
        sb.append("<div class='section'><a name='overview'></a><strong>機能概要</strong>");
        sb.append("<div style='margin-top:6px;'>このボタンは機器の操作において以下のような役割を持ちます。</div></div>");

        // 操作手順
        sb.append("<div class='section'><a name='howto'></a><strong>操作手順</strong>");
        sb.append("<ul>");
        switch (label) {
            case "USER":
                sb.append("<li>短押し：割り当て済みのユーザー画面を呼び出します。</li>");
                sb.append("<li>長押し（設定）：<span class='kbd'>MENU → SET UP → USER KEY ASSIGN</span>で割当を変更。</li>");
                break;
            case "SUB":
                sb.append("<li>短押し：現在表示中の画面に応じたサブメニューを開きます。</li>");
                sb.append("<li>例：GRAPHIC では表示レイヤー、LIST ではソート条件を設定できます。</li>");
                break;
            case "MENU":
                sb.append("<li>短押し：メインメニューを開き、下の各サブメニューへ移動します。</li>");
                sb.append("<li>戻る：<span class='kbd'>CLR</span> または <span class='kbd'>MENU</span> を再押し。</li>");
                break;
            case "PWR":
                sb.append("<li>短押し：電源のオン/オフ（長押し判定の機種あり）。</li>");
                sb.append("<li>電源オン時はコントラスト調整モードになります。</li>");
                break;
            case "DIM":
                sb.append("<li>押すたびにバックライト輝度を段階切替します。</li>");
                break;
            case "DISP":
                sb.append("<li>表示モードを順に切替。詳細は TOC を参照。</li>");
                break;
            case "CLR":
                sb.append("<li>メニュー戻り、入力取消、アラームの一時停止（ACK）に使用。</li>");
                break;
            case "ENTER":
                sb.append("<li>選択/決定（確定）に使用します。</li>");
                break;
            default:
                sb.append("<li>このボタンは選択操作やナビゲーションに使います。</li>");
                break;
        }
        sb.append("</ul></div>");

        // MENU の詳細（日本語）
        if ("MENU".equals(label)) {
            sb.append("<div class='section'><strong>MENU → 主なサブ画面（概要）</strong>");
            sb.append("<a name='voyage'></a>");
            sb.append("<div class='subtitle'>航海データ</div>");
            sb.append("<div style='margin-top:4px;'>航海に関する情報を集約する画面。航路（ウェイポイント）、ETA、対地速度（SOG）、対地針路（COG）などを表示・編集できます。</div>");

            sb.append("<a name='message'></a>");
            sb.append("<div class='subtitle'>メッセージ</div>");
            sb.append("<div style='margin-top:4px;'>受信／送信メッセージの管理。AIS やシステムからの警報、ユーザー送信メッセージの作成と履歴確認ができます。</div>");

            sb.append("<a name='maintenance'></a>");
            sb.append("<div class='subtitle'>保守</div>");
            sb.append("<div style='margin-top:4px;'>機器自己診断、ログ表示、センサー状態（GPS/AIS/電源）確認、ファームウェア情報や較正操作などを行います。</div>");

            sb.append("<a name='setup'></a>");
            sb.append("<div class='subtitle'>設定</div>");
            sb.append("<div style='margin-top:4px;'>言語、日時、表示輝度、アラーム閾値、通信設定、ユーザーキー割当、パスワード設定などのシステム設定を行います。</div>");
            sb.append("</div>");
        }

        // DISP の詳細（日本語）
        if ("DISP".equals(label)) {
            sb.append("<div class='section'><strong>DISP → 表示モード（概要）</strong>");
            sb.append("<a name='listsort'></a>");
            sb.append("<div class='subtitle'>リストソート</div>");
            sb.append("<div style='margin-top:4px;'>ターゲット一覧の表示順やフィルタを設定。距離順、船名・コールサイン順、最終受信時刻などで並べ替えできます。</div>");

            sb.append("<a name='posntime'></a>");
            sb.append("<div class='subtitle'>位置と時刻</div>");
            sb.append("<div style='margin-top:4px;'>現在位置（緯度経度）、UTC 時刻、GPS 受信状態、受信衛星数などを確認する画面です。</div>");

            sb.append("<a name='graphic'></a>");
            sb.append("<div class='subtitle'>グラフィック</div>");
            sb.append("<div style='margin-top:4px;'>地図上に自船と周辺ターゲットを表示。レイヤー切替、ズーム/パン、ターゲット詳細や CPA/TCPA 計算機能があります。</div>");

            sb.append("<a name='ownship'></a>");
            sb.append("<div class='subtitle'>自船情報</div>");
            sb.append("<div style='margin-top:4px;'>自船の詳細（船名、MMSI、コールサイン、船型、喫水、全長/幅 等）を表示・編集する箇所です。</div>");
            sb.append("</div>");
        }

        // SUB の例（日本語）
        if ("SUB".equals(label)) {
            sb.append("<div class='section'><strong>SUB の挙動例（画面ごとの分岐）</strong>");
            sb.append("<a name='subexamples'></a>");
            sb.append("<div class='subtitle'>SUB（画面別例）</div>");
            sb.append("<div style='margin-top:4px;'>表示中のメイン画面に応じて、該当する補助設定を開きます。以下は例です。</div>");

            sb.append("<a name='sublist'></a>");
            sb.append("<div class='subtitle'>SUB（LIST の例）</div>");
            sb.append("<div style='margin-top:4px;'>表示列選択、ソート条件、フィルタ設定（距離レンジ、船種別）などを変更できます。</div>");

            sb.append("<a name='subposn'></a>");
            sb.append("<div class='subtitle'>SUB（位置/時刻 の例）</div>");
            sb.append("<div style='margin-top:4px;'>座標表示形式（度分秒/十進）、時刻表示形式、GPS 詳細（衛星リスト）表示切替など。</div>");

            sb.append("<a name='submsg'></a>");
            sb.append("<div class='subtitle'>SUB（メッセージ の例）</div>");
            sb.append("<div style='margin-top:4px;'>メッセージテンプレートの選択、新規作成、受信履歴のフィルタリングや削除が可能です。</div>");
            sb.append("</div>");
        }

        // 汎用 注意事項
        sb.append("<div class='section'><a name='notes'></a><strong>注意事項</strong>");
        sb.append("<ul>");
        sb.append("<li>表示内容は画面のコンテキストに依存します。現在の表示を確認してから操作してください。</li>");
        sb.append("<li class='warn'>電源操作（PWR）は誤操作に注意。電源OFFにはパスワードが必要な場合があります。</li>");
        sb.append("<li>入力は <span class='kbd'>ENTER</span> で確定、<span class='kbd'>CLR</span> で取り消します。</li>");
        if (extraNote != null && !extraNote.isEmpty()) {
            sb.append("<li>").append(extraNote).append("</li>");
        }
        sb.append("</ul></div>");

        sb.append("<div class='section'><strong>よく使う操作ショートカット</strong>");
        sb.append("<div style='margin-top:6px;'>");
        sb.append("<span class='kbd'>ENTER</span>：決定　");
        sb.append("<span class='kbd'>CLR</span>：戻る/キャンセル　");
        sb.append("<span class='kbd'>↑ ↓ ← →</span>：移動/カーソル操作");
        sb.append("</div></div>");

        sb.append("<div class='footer'>関連：マニュアルの該当節を参照してください。</div>");

        sb.append("</div></body></html>");
        return sb.toString();
    }
}