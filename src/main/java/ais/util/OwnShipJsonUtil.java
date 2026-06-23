package ais.util;

import ais.model.NavStatus;
import ais.model.OwnShipInfo;



public class OwnShipJsonUtil {

    public static String toVoyageJson(OwnShipInfo own) {

        StringBuilder sb = new StringBuilder();
        sb.append("{");
        sb.append("\"type\":\"OwnShipVoyageUpdate\",");
        sb.append("\"navStatus\":\"").append(own.navStatus.getAisValue()).append("\",");
        sb.append("\"destination\":\"").append(escapeJson(own.destination)).append("\",");

        sb.append("\"etaMMDD\":\"").append(own.getEtaDateValue()).append("\",");
        sb.append("\"etaHHMM\":\"").append(own.getEtaTimeValue()).append("\",");


        sb.append("\"draught\":\"").append(own.getDraughtDisplayText()).append("\",");

        sb.append("\"shipType\":\"").append(own.getShipTypeSendValue()).append("\",");

        sb.append("\"cargoType\":\"").append(escapeJson(own.CargoType)).append("\"");

        sb.append("}");
        return sb.toString();
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
