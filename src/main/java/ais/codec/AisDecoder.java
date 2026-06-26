package ais.codec;

import ais.model.NavStatus;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.Map;
import java.util.Optional;


// SafeAisDecoder.java

/**
 * 安全性を意識した AIS デコーダ（Java 8 対応）。
 * - AIVDM / AIVDO のフラグメントを結合
 * - ビット列の長さチェック
 * - 6bit → 文字列変換時の安全化
 */
public class AisDecoder {

    private static final ConcurrentMap<String, FragmentEntry> fragmentBuffer = new ConcurrentHashMap<>();

    private static final char[] SIXBIT_TABLE = {
        '@','A','B','C','D','E','F','G','H','I','J','K','L','M','N','O',
        'P','Q','R','S','T','U','V','W','X','Y','Z',' ','0','1','2','3',
        '4','5','6','7','8','9',' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',
        ' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',
    };

    /** フラグメント保管用 */
    private static class FragmentEntry {
        final int total;
        final String seqId;
        final String channel;
        final String[] parts;
        int received = 0;
        int lastFillBits = 0;

        FragmentEntry(int total, String seqId, String channel) {
            this.total = total;
            this.seqId = seqId;
            this.channel = channel;
            this.parts = new String[total];
        }
    }

    public static class AisMessage {
        public int messageType;
        public int mmsi;
        public String bits;

        // dynamic info
        public Double lat = null;
        public Double lon = null;
        public Double sog = null;
        public Double cog = null;
        public Double trueHeading = null;
        public Integer navStatus = null;
        public Double rot = null;
        public Integer posnQuality = null;
        public Integer raimFlag = null;
        public Integer timestamp = null;
        public Integer syncState = null;

        // static info (type 5)
        public Integer imo = null;
        public String callSign = null;
        public String vesselName = null;
        public Integer shipType = null;
        public String destination = null;
        public Integer epfd = null;
        public Integer etaMonth = null;
        public Integer etaDay = null;
        public Integer etaHour = null;
        public Integer etaMinute = null;
        public Double draught = null;
        public Integer dimA = null;
        public Integer dimB = null;
        public Integer dimC = null;
        public Integer dimD = null;

        @Override
        public String toString() {
            return "AisMessage{" +
                   "type=" + messageType +
                   ", mmsi=" + mmsi +
                   ", lat=" + lat + ", lon=" + lon +
                   ", vesselName=" + vesselName +
                   '}';
        }
    }

    public static AisMessage decode(String nmea) {
        return decodeOptional(nmea).orElse(null);
    }

    public static Optional<AisMessage> decodeOptional(String nmea) {
        if (nmea == null) return Optional.empty();
        nmea = nmea.trim();
        if (!(nmea.startsWith("!AIVDM") || nmea.startsWith("!AIVDO"))) return Optional.empty();

        String[] parts = nmea.split(",", -1);
        if (parts.length < 7) return Optional.empty();

        int total = tryParse(parts[1], 1);
        int num = tryParse(parts[2], 1);
        String seq = parts[3];
        String ch = parts[4];
        String payload = parts[5];
        int fill = 0;
        try { fill = Integer.parseInt(parts[6].split("\\*")[0]); } catch (Exception e) { fill = 0; }

        if (total <= 1) {
            String bits = decodePayload(payload);
            if (fill > 0 && fill < bits.length()) {
                bits = bits.substring(0, bits.length() - fill);
            }
            return Optional.ofNullable(build(bits));
        }

        // マルチフラグメント
        String key = seq + "|" + ch;
        fragmentBuffer.putIfAbsent(key, new FragmentEntry(total, seq, ch));
        FragmentEntry fe = fragmentBuffer.get(key);

        synchronized (fe) {
            if (num >=1 && num <= fe.parts.length) {
                if (fe.parts[num - 1] == null) {
                    fe.parts[num - 1] = payload;
                    fe.received++;
                }
                fe.lastFillBits = fill;
            } else {
                // 異常 fragment number → リセット
                fragmentBuffer.remove(key);
                return Optional.empty();
            }

            if (fe.received < fe.total) {
                return Optional.empty();  // まだ揃っていない
            }

            // 全部揃った ⇒ 結合
            StringBuilder sb = new StringBuilder();
            for (String p : fe.parts) {
                if (p == null) {
                    fragmentBuffer.remove(key);
                    return Optional.empty();
                }
                sb.append(p);
            }
            fragmentBuffer.remove(key);

            String bits = decodePayload(sb.toString());
            if (fe.lastFillBits > 0 && fe.lastFillBits < bits.length()) {
                bits = bits.substring(0, bits.length() - fe.lastFillBits);
            }
            return Optional.ofNullable(build(bits));
        }
    }


    private static int tryParse(String s, int def) {
        try { return Integer.parseInt(s); }
        catch (Exception e) { return def; }
    }

    private static String decodePayload(String payload) {
        StringBuilder sb = new StringBuilder(payload.length()*6);
        for (char c : payload.toCharArray()) {
            int val = c - 48;
            if (val > 40) val -= 8;
            String bin = Integer.toBinaryString(val & 0x3F);
            String padded = String.format("%6s", bin).replace(' ', '0');
            sb.append(padded);
        }
        return sb.toString();
    }

    private static AisMessage build(String bits) {
        if (bits == null || bits.length() < 38) return null;
        try {
            int type = Integer.parseInt(bits.substring(0,6), 2);
            int mmsi = Integer.parseInt(bits.substring(8,38), 2);
            AisMessage msg = new AisMessage();
            msg.messageType = type;
            msg.mmsi = mmsi;
            msg.bits = bits;

            // dynamic data (type 1/2/3)
            if (type == 1 || type == 2 || type == 3) {
                if (bits.length() >= 137) {
                    msg.navStatus = Integer.parseInt(bits.substring(38, 42), 2);
                    msg.rot = (double) twosComp(bits.substring(42, 50));
                    msg.sog = Integer.parseInt(bits.substring(50, 60), 2) / 10.0;
                    msg.posnQuality = Integer.parseInt(bits.substring(60, 61), 2);
                    msg.lon = twosComp(bits.substring(61, 89)) / 600000.0;
                    msg.lat = twosComp(bits.substring(89, 116)) / 600000.0;
                    msg.cog = Integer.parseInt(bits.substring(116, 128), 2) / 10.0;
                    msg.trueHeading = (double)Integer.parseInt(bits.substring(128, 137), 2);
                    msg.timestamp = Integer.parseInt(bits.substring(137, 143), 2);
                    if (bits.length() >= 145) {
                        msg.syncState = Integer.parseInt(bits.substring(143, 145), 2);
                    }
                    if (bits.length() >= 149) {
                        msg.raimFlag = Integer.parseInt(bits.substring(148, 149), 2);
                    }
                } else {
                    return null;
                }
            }

            // static data (type 5)
            if (type == 5) {
                parseType5(msg, bits);
            }

            return msg;
        } catch (Exception e) {
            return null;
        }
    }

    private static void parseType5(AisMessage msg, String bits) {
        int len = bits.length();

        if (len >= 70) {
            msg.imo = Integer.parseInt(bits.substring(40,70), 2);
        }
        if (len >= 112) {
            msg.callSign = decode6bit(bits, 70, 42).trim();
        }
        if (len >= 232) {
            msg.vesselName = decode6bit(bits, 112, 120).trim();
        }
        if (len >= 240) {
            msg.shipType = Integer.parseInt(bits.substring(232, 240), 2);
        }
        if (len >= 270) {
            msg.dimA = Integer.parseInt(bits.substring(240, 249), 2);
            msg.dimB = Integer.parseInt(bits.substring(249, 258), 2);
            msg.dimC = Integer.parseInt(bits.substring(258, 264), 2);
            msg.dimD = Integer.parseInt(bits.substring(264, 270), 2);
        }
        if (len >= 274) {
            msg.epfd = Integer.parseInt(bits.substring(270, 274), 2);
        }
        if (len >= 294) {
            int etaMonth = Integer.parseInt(bits.substring(274, 278), 2);
            int etaDay = Integer.parseInt(bits.substring(278, 283), 2);
            int etaHour = Integer.parseInt(bits.substring(283, 288), 2);
            int etaMinute = Integer.parseInt(bits.substring(288, 294), 2);

            msg.etaMonth = (etaMonth >= 1 && etaMonth <= 12) ? etaMonth : -1;
            msg.etaDay = (etaDay >= 1 && etaDay <= 31) ? etaDay : -1;
            msg.etaHour = (etaHour <= 23) ? etaHour : -1;
            msg.etaMinute = (etaMinute <= 59) ? etaMinute : -1;
        }
        if (len >= 302) {
            msg.draught = Integer.parseInt(bits.substring(294, 302), 2) / 10.0;
        }
        if (len >= 422) {
            msg.destination = decode6bit(bits, 302, 120).trim();
        }
    }

    private static String decode6bit(String bits, int start, int bitLen) {
        StringBuilder sb = new StringBuilder();
        int end = Math.min(bits.length(), start + bitLen);
        for (int i = start; i + 6 <= end; i += 6) {
            String sub = bits.substring(i, i+6);
            int v = Integer.parseInt(sub, 2);
            if (v < 0 || v >= SIXBIT_TABLE.length) {
                sb.append(' ');
            } else {
                char ch = SIXBIT_TABLE[v];
                if (ch == '@') ch = ' ';
                sb.append(ch);
            }
        }
        return sb.toString();
    }

    public static int twosComp(String bits) {
        if (bits == null || bits.isEmpty()) return 0;
        if (bits.charAt(0) == '0') {
            return Integer.parseInt(bits, 2);
        } else {
            int val = Integer.parseInt(bits, 2);
            return val - (1 << bits.length());
        }
    }
}
