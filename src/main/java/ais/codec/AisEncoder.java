package ais.codec;

import ais.model.OwnShipInfo;
import java.util.ArrayList;
import java.util.List;




public class AisEncoder {

    /* =====================================================
     * 公開 API
     * ===================================================== */
    public static List<String> encodeMsg1(OwnShipInfo own) {
        String bits = buildMsg1Bits(own);
        return toNmeaSentences(bits);
    }

    public static List<String> encodeMsg5(OwnShipInfo own) {
        String bits = buildMsg5Bits(own);
        return toNmeaSentences(bits);
    }

    /* =====================================================
     * Message 5
     * ===================================================== */
    private static String buildMsg5Bits(OwnShipInfo own) {
        StringBuilder b = new StringBuilder();

        append(b, 5, 6);              // Message ID
        append(b, 0, 2);              // Repeat
        append(b, own.getMmsi(), 30); // MMSI

        append(b, 0, 2);              // AIS version
        append(b, 0, 30);             // IMO (not available)

        appendString(b, own.getCallSign(), 7);
        appendString(b, own.getVesselName(), 20);

        append(b, own.getShipType(), 8);

        append(b, own.getDimA(), 9);
        append(b, own.getDimB(), 9);
        append(b, own.getDimC(), 6);
        append(b, own.getDimD(), 6);

        append(b, 0, 4);              // EPFD

        append(b, own.getEtaMonth(), 4);
        append(b, own.getEtaDay(), 5);
        append(b, own.getEtaHour(), 5);
        append(b, own.getEtaMinute(), 6);

        append(b, own.getDraughtAis(), 8);

        appendString(b, own.getDestination(), 20);

        append(b, 0, 1);              // DTE
        append(b, 0, 1);              // Spare

        return b.toString();
    }

    /* =====================================================
     * Message 1
     * ===================================================== */
    private static String buildMsg1Bits(OwnShipInfo own) {
        StringBuilder b = new StringBuilder();

        append(b, 1, 6);                     // Message ID
        append(b, 0, 2);                     // Repeat
        append(b, own.getMmsi(), 30);        // MMSI

        append(b, own.getNavStatusAisValue(), 4);

        append(b, 128, 8);                   // ROT not available
        append(b, encodeSog(own.getSog()), 10);
        append(b, 0, 1);                     // Position accuracy

        appendSigned(b, own.getLon(), 28);   // Longitude
        appendSigned(b, own.getLat(), 27);   // Latitude

        append(b, encodeCog(own.getCog()), 12);
        append(b, encodeHeading(own.getHeading()), 9);
        append(b, encodeTimestamp(), 6);

        append(b, 0, 2);                     // Maneuver
        append(b, 0, 3);                     // Spare
        append(b, 0, 1);                     // RAIM

        appendCommStateSOTDMA(b);

        return b.toString();
    }

    /* =====================================================
     * Comm State（SOTDMA）
     * ===================================================== */
    private static void appendCommStateSOTDMA(StringBuilder b) {
        append(b, 0, 1);   // SOTDMA
        append(b, 3, 2);   // Sync state = n/a (03)
        append(b, 0, 14);  // Slot timeout / offset
        append(b, 0, 2);   // Sub-message
    }

    /* =====================================================
     * real-world → AIS 変換
     * ===================================================== */
    private static int encodeSog(double sog) {
        return (sog < 0 || sog > 102.2) ? 1023 : (int) Math.round(sog * 10);
    }

    private static int encodeCog(double cog) {
        return (cog < 0 || cog >= 360) ? 3600 : (int) Math.round(cog * 10);
    }

    private static int encodeHeading(double hdg) {
        return (hdg < 0 || hdg >= 360) ? 511 : (int) Math.round(hdg);
    }

    private static int encodeTimestamp() {
        return 60; // not available
    }

    /* =====================================================
     * NMEA 0183（マルチセンテンス対応）
     * ===================================================== */
    private static List<String> toNmeaSentences(String bits) {
        List<String> result = new ArrayList<>();

        int pad = (6 - bits.length() % 6) % 6;
        while (bits.length() % 6 != 0) {
            bits += "0";
        }

        String payload = bitsToPayload(bits);

        int maxLen = 60;
        int total = (payload.length() + maxLen - 1) / maxLen;
        String seq = ""; // optional

        for (int i = 0; i < total; i++) {
            int start = i * maxLen;
            int end = Math.min(start + maxLen, payload.length());
            String part = payload.substring(start, end);

            // Only last sentence carries pad
            int padVal = (i == total - 1) ? pad : 0;

            String body = "!AIVDM," + total + "," + (i + 1) + ","
                    + seq + ",A," + part + "," + padVal;

            result.add(body + "*" + checksum(body));
        }

        return result;
    }

    private static String bitsToPayload(String bits) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < bits.length(); i += 6) {
            int v = Integer.parseInt(bits.substring(i, i + 6), 2);
            sb.append((char) (v < 40 ? v + 48 : v + 56));
        }
        return sb.toString();
    }

    private static String checksum(String body) {
        int cs = 0;
        for (int i = 1; i < body.length(); i++) {
            cs ^= body.charAt(i);
        }
        return String.format("%02X", cs);
    }

    /* =====================================================
     * 共通ビット操作
     * ===================================================== */
    private static void append(StringBuilder sb, int value, int len) {
        String bin = Integer.toBinaryString(value & ((1 << len) - 1));
        sb.append(String.format("%" + len + "s", bin).replace(' ', '0'));
    }

    // 符号付き値を 2 の補数で append
    private static void appendSigned(StringBuilder sb, double deg, int len) {
        int scaled = (int) Math.round(deg * 600000.0);

        // 範囲外
        int max = (1 << (len - 1)) - 1;
        int min = -(1 << (len - 1));
        if (scaled > max) {
            scaled = max;
        }
        if (scaled < min) {
            scaled = min;
        }

        int raw = scaled & ((1 << len) - 1);
        append(sb, raw, len);
    }

    // 6bit 文字列仕様に準拠
    private static void appendString(StringBuilder sb, String s, int len) {
        // 空白に置換
        if (s == null || s.isEmpty()) {
            s = "";
        }
        // AIS 6bit 文字仕様
        for (int i = 0; i < len; i++) {
            char c = i < s.length() ? s.charAt(i) : '@';
            int val = ais6bitChar(c);
            append(sb, val, 6);
        }
    }

    private static int ais6bitChar(char c) {
        // A–Z → 1–26
        if (c >= 'A' && c <= 'Z') {
            return c - 'A' + 1;
        }
        // 0–9 → 48–57 → 48 offset
        if (c >= '0' && c <= '9') {
            return c - '0' + 48;
        }
        if (c == ' ') {
            return 32;
        }
        return 0; // '@' or others → padding
    }
}
