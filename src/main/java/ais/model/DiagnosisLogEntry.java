package ais.model;

import java.time.LocalDateTime;



public class DiagnosisLogEntry {

    /* =====================================================
     * 共通
     * ===================================================== */
    private final boolean ok;
    private final LocalDateTime dateTime;

    /* =====================================================
     * TRANSPONDER 用詳細結果
     * （CONT / INT GPS / TRX / PS）
     * ===================================================== */
    private final DiagnoseResult cont;
    private final DiagnoseResult gps;
    private final DiagnoseResult trx;
    private final DiagnoseResult ps;

    /* =====================================================
     * コンストラクタ（TRANSPONDER 用）
     * ===================================================== */
    public DiagnosisLogEntry(
            DiagnoseResult cont,
            DiagnoseResult gps,
            DiagnoseResult trx,
            DiagnoseResult ps,
            LocalDateTime dateTime
    ) {
        this.cont = cont;
        this.gps = gps;
        this.trx = trx;
        this.ps = ps;
        this.ok = (cont == DiagnoseResult.OK
                && gps == DiagnoseResult.OK
                && trx == DiagnoseResult.OK
                && ps == DiagnoseResult.OK);
        this.dateTime = dateTime;
    }

    /* =====================================================
     * コンストラクタ（CONTROLLER / LAN 用）
     * ===================================================== */
    public DiagnosisLogEntry(
            DiagnoseResult result,
            LocalDateTime dateTime
    ) {
        this.cont = null;
        this.gps = null;
        this.trx = null;
        this.ps = null;
        this.ok = (result == DiagnoseResult.OK);
        this.dateTime = dateTime;
    }

    /* =====================================================
     * Getter
     * ===================================================== */
    public boolean isOk() {
        return ok;
    }

    public DiagnoseResult getCont() {
        return cont;
    }

    public DiagnoseResult getGps() {
        return gps;
    }

    public DiagnoseResult getTrx() {
        return trx;
    }

    public DiagnoseResult getPs() {
        return ps;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }
}
