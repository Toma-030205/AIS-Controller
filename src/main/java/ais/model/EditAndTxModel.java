package ais.model;


public class EditAndTxModel implements CursorEditableModel {

    /* ===== 行定義 ===== */
    public enum Row {
        FORMAT,
        MMSI,
        CATEGORY,
        FUNCTION,
        REPLY,
        CHANNEL,
        RETRY
    }

    /* ===== 編集モード ===== */
    public enum EditMode {
        ROW_SELECT, // 左カーソル（行選択）
        VALUE_EDIT, // 右カーソル（通常値編集）
        MMSI_EDIT       // MMSI 桁編集（専用）
    }

    /* ===== 各種設定値 ===== */
    public enum Format {
        BROADCAST, ADDRESSED
    }

    public enum Category {
        SAFETY, ROUTINE
    }

    public enum Function {
        TEXT, CAPABILITY_INTERROGATE
    }

    public enum Reply {
        ON, OFF
    }

    public enum Channel {
        AUTO, A, B, A_B
    }

    /* ===== 状態 ===== */
    private Row currentRow = Row.FORMAT;
    private EditMode editMode = EditMode.ROW_SELECT;

    private Format format = Format.BROADCAST;
    private Category category = Category.SAFETY;
    private Function function = Function.TEXT;
    private Reply reply = Reply.OFF;
    private Channel channel = Channel.AUTO;
    private int retry = 0;
    /* ===== TEXT ===== */
    private String text = "";

    /* ===== MMSI ===== */
    private final char[] mmsi = new char[9];
    private int mmsiCursor = 0;

    public EditAndTxModel() {
        for (int i = 0; i < mmsi.length; i++) {
            mmsi[i] = '0';
        }
    }

    /* =====================================================
     * 行移動（ROW_SELECT）
     * ===================================================== */
    public void moveRowUp() {
        if (editMode != EditMode.ROW_SELECT) return;

        Row[] rows = Row.values();
        int idx = currentRow.ordinal();

        do {
            idx = (idx - 1 + rows.length) % rows.length;
        } while (!isRowSelectable(rows[idx]));


        currentRow = rows[idx];
    }

    public void moveRowDown() {
        if (editMode != EditMode.ROW_SELECT) return;

        Row[] rows = Row.values();
        int idx = currentRow.ordinal();

        do {
            idx = (idx + 1 + rows.length) % rows.length;
        } while (!isRowSelectable(rows[idx]));


        currentRow = rows[idx];
    }

    /* =====================================================
     * 値変更（VALUE_EDIT）
     * ===================================================== */
    public void incrementValue() {
        if (editMode == EditMode.MMSI_EDIT) {
            if (mmsi[mmsiCursor] < '9') {
                mmsi[mmsiCursor]++;
            }
            return;
        }

        if (editMode != EditMode.VALUE_EDIT) return;

        switch (currentRow) {
            case FORMAT:
                format = (format == Format.BROADCAST) ? Format.ADDRESSED : Format.BROADCAST;
                break;

            case CATEGORY:
                category = (category == Category.SAFETY) ? Category.ROUTINE : Category.SAFETY;
                break;

            case FUNCTION:
                toggleFunction();
                break;

            case REPLY:
                reply = (reply == Reply.ON) ? Reply.OFF : Reply.ON;
                break;

            case CHANNEL:
                channel = Channel.values()[(channel.ordinal() + 1) % Channel.values().length];
                break;

            case RETRY:
                retry = (retry + 1) % 4;
                break;


        }
    }

    public void decrementValue() {
        if (editMode == EditMode.MMSI_EDIT) {
            if (mmsi[mmsiCursor] > '0') {
                mmsi[mmsiCursor]--;
            }
            return;
        }

        if (editMode != EditMode.VALUE_EDIT) return;

        switch (currentRow) {
            case FORMAT:
                incrementValue();
                break;

            case CATEGORY:
                incrementValue();
                break;

            case FUNCTION:
                toggleFunction();
                break;

            case REPLY:
                incrementValue();
                break;

            case CHANNEL:
                int idx = channel.ordinal() - 1;
                if (idx < 0) idx = Channel.values().length - 1;
                channel = Channel.values()[idx];
                break;

            case RETRY:
                retry = (retry + 3) % 4;
                break;

            
        }
    }

    public int getMaxTextLength() {
        if (format == Format.ADDRESSED) {
            return (category == Category.SAFETY) ? 156 : 151;
        } else {
            return (category == Category.SAFETY) ? 161 : 156;
        }
    }

    public Format getFormat() {
        return format;
    }

    public Category getCategory() {
        return category;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getText() {
        return text;
    }

    /* =====================================================
     * ENTER / CLR
     * ===================================================== */
    public void enter() {
        if (editMode == EditMode.MMSI_EDIT) {
            if (mmsiCursor < mmsi.length - 1) {
                mmsiCursor++;
            } else {
                // ★ 最終桁 → 確定
                editMode = EditMode.ROW_SELECT;
                currentRow = Row.CATEGORY;
            }
            return;
        }

        if (editMode == EditMode.ROW_SELECT) {
            if (currentRow == Row.FORMAT) {
                editMode = EditMode.VALUE_EDIT;
            } else if (currentRow == Row.MMSI) {
                editMode = EditMode.MMSI_EDIT;
                mmsiCursor = 0;
            } else {
                editMode = EditMode.VALUE_EDIT;
            }
            return;
        }

        if (editMode == EditMode.VALUE_EDIT) {
            // ★ FORMAT=ADDRESSED になった瞬間
            if (currentRow == Row.FORMAT && format == Format.ADDRESSED) {
                currentRow = Row.MMSI;
                editMode = EditMode.MMSI_EDIT;
                mmsiCursor = 0;
                return;
            }
            editMode = EditMode.ROW_SELECT;
        }
    }


    /**
     * CLR 押下時の処理
     *
     * @return true = 画面を抜けてよい / false = 画面に留まる
     */
    public boolean onClr() {

        // MMSI 桁編集中 → 一桁戻る
        if (editMode == EditMode.MMSI_EDIT) {
            if (mmsiCursor > 0) {
                mmsiCursor--;
            }
            return false; // 画面遷移しない
        }

        // 値編集モード → 行選択へ戻る
        if (editMode == EditMode.VALUE_EDIT) {
            editMode = EditMode.ROW_SELECT;
            return false;
        }

        // 行選択モード → 画面終了
        reset();
        return true;
    }


    public void reset() {
        // 行・モード
        currentRow = Row.FORMAT;
        editMode = EditMode.ROW_SELECT;

        // 各設定を初期値へ
        format = Format.BROADCAST;
        category = Category.SAFETY;
        function = Function.TEXT;
        reply = Reply.OFF;
        channel = Channel.AUTO;
        retry = 0;

        // MMSI 初期化
        for (int i = 0; i < mmsi.length; i++) {
            mmsi[i] = '0';
        }
        mmsiCursor = 0;
    }


    /* =====================================================
     * MMSI カーソル
     * ===================================================== */
    public void moveMmsiRight() {
        if (currentRow == Row.MMSI && mmsiCursor < mmsi.length - 1) {
            mmsiCursor++;
        }
    }

    public void moveMmsiLeft() {
        if (currentRow == Row.MMSI && mmsiCursor > 0) {
            mmsiCursor--;
        }
    }

    /* =====================================================
     * 内部制御  TX
     * ===================================================== */
    private void toggleFunction() {
        if (format == Format.ADDRESSED && category == Category.ROUTINE) {
            function = (function == Function.TEXT)
                    ? Function.CAPABILITY_INTERROGATE
                    : Function.TEXT;
        } else {
            function = Function.TEXT;
        }
    }

    private boolean isRowSelectable(Row row) {
        if (row == Row.MMSI) {
            return false; // ★ 重要

                }if (row == Row.REPLY || row == Row.RETRY) {
            return format == Format.ADDRESSED;
        }
        return true;
    }

    public void loadFromTxMessage(TxMessage msg) {
        reset();

        // FORMAT
        format = msg.isBroadcast()
                ? Format.BROADCAST
                : Format.ADDRESSED;

        // MMSI
        if (!msg.isBroadcast()) {
            char[] src = msg.getMmsi().toCharArray();
            for (int i = 0; i < mmsi.length && i < src.length; i++) {
                mmsi[i] = src[i];
            }
        }

        // CATEGORY / FUNCTION / REPLY / CHANNEL
        category = Category.valueOf(msg.getCategory());
        function = Function.valueOf(msg.getFunction());
        reply = Reply.valueOf(msg.getReply());
        channel = Channel.valueOf(msg.getChannel());

        text = msg.getText();
    }

    public TxMessage buildTxMessage() {

        TxMessage msg = new TxMessage();

        /* ===== 種別 ===== */
        msg.format = (format == Format.BROADCAST)
                ? EditAndTxModel.Format.BROADCAST
                : EditAndTxModel.Format.ADDRESSED;

        msg.category = (category == Category.SAFETY)
                ? EditAndTxModel.Category.SAFETY
                : EditAndTxModel.Category.ROUTINE;

        msg.function = function.name();
        msg.reply = (reply == Reply.ON);
        msg.channel = channel.ordinal();

        /* ===== 宛先 ===== */
        if (format == Format.BROADCAST) {
            msg.destination = "BROADCAST";
        } else {
            msg.destination = new String(mmsi);
        }

        /* ===== 本文 ===== */
        msg.text = text;

        /* ===== SAVE 状態として確定 ===== */
        msg.markSaved();

        return msg;
    }

    /* =====================================================
     * 内部制御   RX
     * ===================================================== */
    public void loadFromRxMessage(RxMessage msg) {
        reset();

        // ===== FORMAT =====
        format = msg.isBroadcast()
                ? Format.BROADCAST
                : Format.ADDRESSED;

        // ===== MMSI =====
        if (msg.isAddressed()) {
            char[] src = msg.source.toCharArray();
            for (int i = 0; i < mmsi.length && i < src.length; i++) {
                mmsi[i] = src[i];
            }
        }

        // ===== CATEGORY =====
        category = Category.valueOf(msg.category.name());

        // ===== FUNCTION =====
        // 指示どおり固定
        function = Function.TEXT;

        // ===== REPLY =====
        reply = msg.reply ? Reply.ON : Reply.OFF;

        // ===== CHANNEL =====
        channel = Channel.valueOf(msg.getChannel());

        // ===== TEXT =====
        text = msg.text;
    }



    /* =====================================================
     * Getter（View 用）
     * ===================================================== */
    public Row getCurrentRow() { return currentRow; }
    public EditMode getEditMode() { return editMode; }

    public String getFormatText() { return format.name(); }
    public String getCategoryText() { return category.name(); }
    public String getFunctionText() { return function.name(); }
    public String getReplyText() { return reply.name(); }
    public String getChannelText() {
        return channel == Channel.A_B ? "A/B" : channel.name();
    }
    public String getRetryText() { return String.valueOf(retry); }

    public String getMmsiText() {
        return new String(mmsi);
    }

    public int getMmsiCursor() {
        return mmsiCursor;
    }

    public Reply getReply() {
        return reply;
    }

    public Function getFunction() {
        return function;
    }

    public Channel getChannel() {
        return channel;
    }

}
