package ais.model;

public final class ShipDisplayFormatter {
    private ShipDisplayFormatter() {
    }

    public static String navStatus(int navStatus) {
        switch (navStatus) {
            case 0: return "UNDER WAY USING ENGINE";
            case 1: return "AT ANCHOR";
            case 2: return "NOT UNDER COMMAND";
            case 3: return "RESTRICTED MANOEUVRABILITY";
            case 4: return "CONSTRAINED BY HER DRAUGHT";
            case 5: return "MOORED";
            case 6: return "AGROUND";
            case 7: return "ENGAGED IN FISHING";
            case 8: return "UNDER WAY SAILING";
            case 9: return "RESERVED FOR HSC";
            case 10: return "RESERVED FOR WIG";
            case 14: return "AIS-SART";
            case 15: return "NOT DEFINED";
            default: return "UNKNOWN";
        }
    }

    public static String positionQuality(int posnQuality) {
        switch (posnQuality) {
            case 0: return "LOW";
            case 1: return "HIGH";
            default: return "UNKNOWN";
        }
    }

    public static String raimFlag(int raimFlag) {
        switch (raimFlag) {
            case 0: return "RAIM not in use";
            case 1: return "RAIM in use";
            default: return "UNKNOWN";
        }
    }

    public static String syncState(int syncState) {
        switch (syncState) {
            case 0: return "UTC DIRECT";
            case 1: return "UTC INDIRECT";
            case 2: return "SYNC TO BASE";
            case 3: return "SYNC FROM MOBILE";
            default: return "UNKNOWN";
        }
    }

    public static String aisClass(int lastMessageType) {
        switch (lastMessageType) {
            case 18:
            case 19:
            case 24:
                return "CLASS B";
            default:
                return "CLASS A";
        }
    }

    public static String shipType(int shipType) {
        if (shipType >= 20 && shipType < 30) return "WIG";
        if (shipType >= 40 && shipType <= 49) return "High speed craft";
        if (shipType >= 60 && shipType <= 69) return "Passenger ship";
        if (shipType >= 70 && shipType <= 79) return "Cargo ships";
        if (shipType >= 80 && shipType <= 89) return "Tanker";
        if (shipType >= 90 && shipType <= 99) return "Other type of ship";

        switch (shipType) {
            case 0: return "Not available";
            case 30: return "Fishing vessel";
            case 31: return "Towing vessel";
            case 32: return "Towing vessel (L>200m B>25m)";
            case 33: return "Dredging/Underwater OPE";
            case 34: return "Vessel Diving OPE";
            case 35: return "Vessel Military OPE";
            case 36: return "Sailing vessel";
            case 37: return "Pleasure craft";
            case 50: return "Pilot vessel";
            case 51: return "Search and Rescue vessel";
            case 52: return "Tugs";
            case 53: return "Port tenders";
            case 54: return "With Anti-pollution equip";
            case 55: return "Law enforcement vessels";
            case 58: return "Medical transports";
            case 59: return "SHIP/AIR NO ARM CNFLCT";
            default: return "Unknown (" + shipType + ")";
        }
    }

    public static String cargo(int shipType) {
        int main = shipType / 10;
        int sub = shipType % 10;

        if (main == 2 || main == 4 || main == 6 || main == 7 || main == 8 || main == 9) {
            switch (sub) {
                case 0: return "All Ships of This Type";
                case 1: return "Category X";
                case 2: return "Category Y";
                case 3: return "Category Z";
                case 4: return "Category OS";
                case 9: return "No Additional Infomation";
                default: return "Reserved";
            }
        }
        return "";
    }

    public static String epfd(int epfd) {
        switch (epfd) {
            case 0: return "Undefined";
            case 1: return "GPS";
            case 2: return "GLONASS";
            case 3: return "Combined GPS/GLONASS";
            case 4: return "Loran-C";
            case 5: return "Chayka";
            case 6: return "Integrated Navigation System";
            case 7: return "Surveyed";
            case 8: return "Galileo";
            case 15: return "Internal GNSS";
            default: return "Unknown";
        }
    }
}
