package ais.model;



public class ShipTypeCargoInputModel {

    public enum Phase {
        SHIP_TYPE,
        CARGO_TYPE
    }

    private Phase phase = Phase.SHIP_TYPE;
    private boolean shipTypeUS = false;   // US = true / International = false

    private int shipTypeCode = 20;
    private int cargoIndex = 5; // ALL SHIPS OF THIS TYPE

    /* =====================
     * Phase control
     * ===================== */
    public Phase getPhase() {
        return phase;
    }

    public void nextPhase() {
        phase = Phase.CARGO_TYPE;
    }

    public void resetPhase() {
        phase = Phase.SHIP_TYPE;
    }

    /* =====================
     * Ship type
     * ===================== */
    public int getShipTypeCode() {
        return shipTypeCode;
    }

    public void setShipTypeCode(int code) {
        shipTypeCode = code;
        normalizeCargo();
    }

    public void setShipTypeUS(boolean us) {
        this.shipTypeUS = us;
        normalizeShipType();
        normalizeCargo();
    }

    public int[] getSelectableShipTypes() {

        java.util.List<Integer> list = new java.util.ArrayList<>();

        if (shipTypeUS) {
            // US : 20–29
            for (int i = 20; i <= 29; i++) {
                list.add(i);
            }   
            } else {
            list.add(20);   // ★ 国際標準でも 20-WIG を表示
        }

        // 共通 : 30–37
        for (int i = 30; i <= 37; i++) {
            list.add(i);
        }

        // 共通 tens 代表 + 詳細
        int[][] ranges = {
            {40, 40},
            {50, 59},
            {60, 60},
            {70, 70},
            {80, 80},
            {90, 90}
        };

        for (int[] r : ranges) {
            for (int i = r[0]; i <= r[1]; i++) {
                list.add(i);
            }
        }

        // int[] 化
        int[] result = new int[list.size()];
        for (int i = 0; i < list.size(); i++) {
            result[i] = list.get(i);
        }
        return result;
    }


    private void normalizeShipType() {
        int[] selectable = getSelectableShipTypes();
        for (int t : selectable) {
            if (t == shipTypeCode) {
                return;
            }
        }
        shipTypeCode = selectable[0];
    }

    /* =====================
     * Cargo
     * ===================== */
    public int getCargoIndex() {
        return cargoIndex;
    }

    public void setCargoIndex(int idx) {
        cargoIndex = idx;
    }

    public boolean isCargoSelectable() {
        int tens = shipTypeCode / 10;

        if (shipTypeUS) {
            // US : 4x,6x,7x,8x,9x
            return tens == 4 || tens == 6 || tens == 7
                    || tens == 8 || tens == 9;
        } else {
            // International : 2x,4x,6x,7x,8x,9x
            return tens == 2 || tens == 4 || tens == 6
                    || tens == 7 || tens == 8 || tens == 9;
        }
    }


    private void normalizeCargo() {
        if (!isCargoSelectable()) {
            cargoIndex = -1;
        } else if (cargoIndex < 0) {
            cargoIndex = 5; // ALL SHIPS OF THIS TYPE
        }
    }

    /* =====================
     * Display API（★唯一の表示窓口）
     * ===================== */
    /**
     * TYPE OF SHIP : 20 - WIG
     */
    public String getShipTypeDisplayText() {
        return shipTypeCode + "   " + getShipTypeName(shipTypeCode);
    }

    /**
     * CARGO TYPE : CATEGORY Z (DG/HS/MP)
     */
    public String getCargoTypeDisplayText() {
        if (!isCargoSelectable() || cargoIndex < 0) {
            return "NONE";
        }
        return getCargoTypeName(cargoIndex);
    }

    /* =====================
     * Internal name tables
     * ===================== */
    private String getShipTypeName(int code) {

        if ((code >= 30 && code <= 37) || (code >= 50 && code <= 59)
                || (shipTypeUS && code >= 20 && code <= 29)) {
            return shipTypeDetailName(code);
        }



        switch (code / 10) {
            case 2:
                return "WIG";
            case 4:
                return shipTypeUS ? "HSC OR PASSENGER < 100GT"
                        : "HIGH SPEED CRAFT";
            case 6:
                return shipTypeUS ? "PASSENGER SHIP > 100GT"
                        : "PASSENGER SHIPS";
            case 7:
                return "CARGO SHIPS";
            case 8:
                return "TANKER";
            case 9:
                return "OTHER TYPE OF SHIP";
            default:
                return "UNKNOWN";
        }
    }

    private String getCargoTypeName(int idx) {
        switch (idx) {
            case 0:
                return "CATEGORY X (DG/HS/MP)";
            case 1:
                return "CATEGORY Y (DG/HS/MP)";
            case 2:
                return "CATEGORY Z (DG/HS/MP)";
            case 3:
                return "CATEGORY OS (DG/HS/MP)";
            case 4:
                return "NO ADDITIONAL INFORMATION";
            case 5:
                return "ALL SHIPS OF THIS TYPE";
            default:
                return "NONE";
        }
    }

    private String shipTypeDetailName(int code) {
        switch (code) {
            case 20:
                return "WIG IN GROUND";
            case 21:
                return "TOWING OTHER THAN BARGE";
            case 22:
                return "TOWING BARGES";
            case 23:
                return "LIGHT BOATS";
            case 24:
                return "MODU/FPS/FPSO/LIFTBOAT";
            case 25:
                return "OFFSHORE SUPPLY VESSEL";
            case 26:
                return "PROCESSING VESSEL";
            case 27:
                return "SCHOOL/SCIENTIFIC/RESEARCH";
            case 28:
                return "U.S. PUB OR GOVT VESSEL";
            case 29:
                return "AUTONOMOUS/REMOTELY-OPE";
            case 30:
                return "FISHING VESSEL";
            case 31:
                return "TOWING VESSEL";
            case 32:
                return "TOWING VESSEL L>200M B>25M";
            case 33:
                return "DREDGE OR UNDERWTR OPE";
            case 34:
                return "VESSEL – DIVING OPE";
            case 35:
                return "VESSEL – MILITARY OPE";
            case 36:
                return "SAILING VESSEL";
            case 37:
                return "PLEASURE CRAFT";
            case 50:
                return "PILOT VESSEL";
            case 51:
                return "SEARCH AND RESCUE VESSEL";
            case 52:
                return shipTypeUS ? "HARBOR TUGS" : "TUGS";
            case 53:
                return shipTypeUS ? "FISH/OFFSHORE/PT TENDER" : "PORT TENDERS";
            case 54:
                return "WITH ANTI-POLLUTION EQUIP";
            case 55:
                return "LAW ENFORCEMENT VESSEL";
            case 56:
                return "LOCAL VESSEL";
            case 57:
                return "LOCAL VSL MARINE EVENT";
            case 58:
                return "MEDICAL TRANSPORTS";
            case 59:
                return "SHIP/AIR NO ARM CONFLICT";
            default:
                return "UNKNOWN";
        }
    }
}
