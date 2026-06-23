package ais.model;

import java.util.ArrayList;
import java.util.List;



public class OwnShipInfo {

    // --- 基本情報 (MSG5 相当) ---
    public int mmsi = 999000001;        // 自船の MMSI（仮）
    public String vesselName = "MY SHIP";  // 船名（仮）
    public int imo = 1234567;          // IMO（仮）
    public String callSign = "JJAB";   // コールサイン（仮）

    // --- 船体寸法 (MSG5) ---
    public int dimBow = 10;            // Bow〜GPSアンテナ距離
    public int dimStern = 20;          // Stern〜GPSアンテナ距離
    public int dimPort = 5;            // Port〜GPSアンテナ距離
    public int dimStarboard = 5;       // Starboard〜GPSアンテナ距離
    public int length = 30;            // 船長
    public int beam = 10;              // 船幅

    // --- 航行状態 (MSG1 / MSG3) ---
    public double lat;                 // 緯度
    public double lon;                 // 経度
    public double sog = 12.3;          // SOG（仮）
    public double cog = 89.0;          // COG（仮）
    public double heading = 85.0;      // HDG（仮）
    public double rot = 0.0;           // ROT（仮）
    public NavStatus navStatus = NavStatus.NOT_DEFINED;  // 航行状態

    // --- 目的地情報 (MSG5) ---
    public String destination = "JP NGO";     // 仮
    public List<String> destinationHistory = new ArrayList<>();
    public int etaMonth=1;   // 1–12
    public int etaDay=1;     // 1–31
    public int etaHour=0;    // 0–23
    public int etaMinute=0;  // 0–59
    public double draught = 5.1;            // 喫水
    public int persons = 10;                // 乗員（仮）
    public String shipTypeUS = "OFF";       // 船種の設定
    public int shipType = 70;               // Cargo ship（仮）

    // --- その他 ---
    public String posnDevice = "GPS";   // 仮
    public String posnQuality = "POSN > 10M"; // 仮
    public String rccState = "SYNC";    // 仮
    public String CargoType = "ALL SHIPS OF THIS TYPE"; // 仮

    public int getMmsi() {
        return mmsi;
    }

    public int getNavStatusAisValue() {
        return navStatus.getAisValue();
    }

    public NavStatus getNavStatus() {
        return navStatus;
    }

    public void setNavStatus(NavStatus navStatus) {
        this.navStatus = navStatus;
    }

    /* --- ETA --- */
    public void setEta(int m, int d, int h, int min) {
        this.etaMonth = m;
        this.etaDay = d;
        this.etaHour = h;
        this.etaMinute = min;
    }

    public String getEtaDisplayText() {
        return String.format(
                "%02d/%02d %02d:%02d",
                etaMonth, etaDay, etaHour, etaMinute
        );
    }

    public String getEtaSendValue() {
    return String.format(
            "%02d%02d%02d%02d",
            etaMonth, etaDay, etaHour, etaMinute
    );
}

public String getEtaDateValue() {
    return String.format("%02d%02d", etaMonth, etaDay); // MMDD
}

public String getEtaTimeValue() {
    return String.format("%02d%02d", etaHour, etaMinute); // HHMM
}



    public int getEtaMonth() {
        return etaMonth;
    }

    public int getEtaDay() {
        return etaDay;
    }

    public int getEtaHour() {
        return etaHour;
    }

    public int getEtaMinute() {
        return etaMinute;
    }

    /* --- Draught --- */
    public int getDraughtAis() {
        if (draught >= 25.5) {
            return 255;
        }
        if (draught < 0) {
            return 0;
        }
        return (int) Math.round(draught * 10.0);
    }

    public String getDraughtDisplayText() {
        if (draught >= 25.5) {
            return "25.5m or greater";
        }
        return String.format("%.1fm", draught);
    }


    /* --- Destination --- */
    public String getDestination() {
        return destination;
    }

    public int getShipType() {
        return shipType;
    }

    public String getShipTypeDisplayText() {

        ShipTypeCargoInputModel model = new ShipTypeCargoInputModel();
        model.setShipTypeUS("OFF : International".equals(shipTypeUS) ? false : true);
        model.setShipTypeCode(shipType);

        return model.getShipTypeDisplayText();
    }

    public String getShipTypeSendValue() {
    return String.valueOf(shipType);
}

    public String getCallSign() {
        return callSign;
    }

    public String getVesselName() {
        return vesselName;
    }

    public int getDimA() {
        return dimBow;
    }

    public int getDimB() {
        return dimStern;
    }

    public int getDimC() {
        return dimPort;
    }

    public int getDimD() {
        return dimStarboard;
    }

    public double getSog() {
        return sog;
    }

    public double getCog() {
        return cog;
    }

    public double getHeading() {
        return heading;
    }

    public double getRot() {
        return rot;
    }

    public double getLat() {
        return lat;
    }

    public double getLon() {
        return lon;
    }

    // ★ 追加：コピーコンストラクタ
    public OwnShipInfo(OwnShipInfo src) {

        // --- 基本情報 ---
        this.mmsi = src.mmsi;
        this.vesselName = src.vesselName;
        this.imo = src.imo;
        this.callSign = src.callSign;

        // --- 船体寸法 ---
        this.dimBow = src.dimBow;
        this.dimStern = src.dimStern;
        this.dimPort = src.dimPort;
        this.dimStarboard = src.dimStarboard;
        this.length = src.length;
        this.beam = src.beam;

        // --- 航行状態 ---
        this.lat = src.lat;
        this.lon = src.lon;
        this.sog = src.sog;
        this.cog = src.cog;
        this.heading = src.heading;
        this.rot = src.rot;
        this.navStatus = src.navStatus;

        // --- Voyage 情報 ---
        this.etaMonth = src.etaMonth;
        this.etaDay = src.etaDay;
        this.etaHour = src.etaHour;
        this.etaMinute = src.etaMinute;
        this.draught = src.draught;
        this.destination = src.destination;
        this.persons = src.persons;
        this.shipTypeUS = src.shipTypeUS;
        this.shipType = src.shipType;
        this.CargoType = src.CargoType;
        this.destinationHistory = new ArrayList<>(src.destinationHistory);

        // --- その他 ---
        this.posnDevice = src.posnDevice;
        this.posnQuality = src.posnQuality;
        this.rccState = src.rccState;
    }

    // ★ 追加：SET 用コピー
    public void copyFrom(OwnShipInfo src) {

        // --- Voyage 情報のみ反映 ---
        this.navStatus = src.navStatus;
        this.destination = src.destination;
        this.etaMonth = src.etaMonth;
        this.etaDay = src.etaDay;
        this.etaHour = src.etaHour;
        this.etaMinute = src.etaMinute;
        this.draught = src.draught;
        this.persons = src.persons;
        this.shipTypeUS = src.shipTypeUS;
        this.shipType = src.shipType;
        this.CargoType = src.CargoType;

        this.destinationHistory.clear();
        this.destinationHistory.addAll(src.destinationHistory);
    }


    // コンストラクタ
    public OwnShipInfo(double lat, double lon, double heading) {
        this.lat = lat;
        this.lon = lon;
        this.heading = heading;

        destinationHistory.add(destination);
    }
}
