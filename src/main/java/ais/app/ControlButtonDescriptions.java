package ais.app;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;

public final class ControlButtonDescriptions {
    private ControlButtonDescriptions() {
    }

    public static Map<String, String> create() {
        Map<String, String> descriptions = new LinkedHashMap<>();
        descriptions.put("USER", "USER: ユーザーキー — よく使う画面をワンキーで呼び出せます。");
        descriptions.put("SUB", "SUB: サブメニュー表示 — 現在の画面に応じた設定や補助操作を開きます。");
        descriptions.put("MENU", "MENU: メインメニュー表示 — 各種サブメニューへ移動します。");
        descriptions.put("‥", "（省略）: 任意機能の割当位置です。");
        descriptions.put("PWR", "PWR: 電源/コントラスト — 電源操作やコントラスト調整を行います。");
        descriptions.put("DIM", "DIM: バックライト（輝度）調整を行います。");
        descriptions.put("DISP", "DISP: 表示モード切替 — リスト/位置/グラフィック/自船情報などを切替。");
        descriptions.put("CLR", "CLR: クリア／戻る — メニュー戻りや入力取り消し、アラーム一時停止等。");
        descriptions.put("ENTER", "ENTER: 決定／確定 — メニューや入力の確定に使用します。");
        descriptions.put("↑", "↑: カーソル上移動");
        descriptions.put("↓", "↓: カーソル下移動");
        descriptions.put("←", "←: 左移動/スクロール");
        descriptions.put("→", "→: 右移動/スクロール");
        return Collections.unmodifiableMap(descriptions);
    }
}
