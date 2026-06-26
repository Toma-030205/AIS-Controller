package ais.util;

import ais.model.OwnShipInfo;
import java.util.LinkedHashMap;
import java.util.Map;



public class OwnShipJsonUtil {

    public static String toVoyageJson(OwnShipInfo own) {
        Map<String, String> fields = new LinkedHashMap<>();
        fields.put("type", "OwnShipVoyageUpdate");
        fields.put("navStatus", String.valueOf(own.navStatus.getAisValue()));
        fields.put("destination", own.destination);
        fields.put("etaMMDD", own.getEtaDateValue());
        fields.put("etaHHMM", own.getEtaTimeValue());
        fields.put("draught", own.getDraughtDisplayText());
        fields.put("shipType", own.getShipTypeSendValue());
        fields.put("cargoType", own.CargoType);

        StringBuilder sb = new StringBuilder();
        sb.append("{");
        boolean first = true;
        for (Map.Entry<String, String> field : fields.entrySet()) {
            if (!first) {
                sb.append(",");
            }
            appendJsonField(sb, field.getKey(), field.getValue());
            first = false;
        }
        sb.append("}");
        return sb.toString();
    }

    private static void appendJsonField(StringBuilder sb, String key, String value) {
        sb.append("\"")
                .append(escapeJson(key))
                .append("\":\"")
                .append(escapeJson(value))
                .append("\"");
    }

    private static String escapeJson(String value) {
        if (value == null) {
            return "";
        }

        StringBuilder escaped = new StringBuilder(value.length());
        for (int i = 0; i < value.length(); i++) {
            char c = value.charAt(i);
            switch (c) {
                case '"':
                    escaped.append("\\\"");
                    break;
                case '\\':
                    escaped.append("\\\\");
                    break;
                case '\b':
                    escaped.append("\\b");
                    break;
                case '\f':
                    escaped.append("\\f");
                    break;
                case '\n':
                    escaped.append("\\n");
                    break;
                case '\r':
                    escaped.append("\\r");
                    break;
                case '\t':
                    escaped.append("\\t");
                    break;
                default:
                    if (c < 0x20) {
                        escaped.append(String.format("\\u%04x", (int) c));
                    } else {
                        escaped.append(c);
                    }
                    break;
            }
        }
        return escaped.toString();
    }
}
