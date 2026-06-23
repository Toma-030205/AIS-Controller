package ais.model;

public class ShipInfo {
    public int mmsi;
    public long lastReceivedTime;

    public double lat;
    public double lon;
    public double sog;
    public double cog;
    public double trueHeading;
    public int navStatus;
    public double rot;
    public int posnQuality;
    public int raimFlag;
    public int timestamp;
    public int syncState;

    public int imo;
    public String callSign;
    public String vesselName;
    public int lastMessageType;

    public int shipType;
    public String CargoType;
    public String destination;
    public double draught;
    public int dimA;
    public int dimB;
    public int dimC;
    public int dimD;
    public int length;
    public int beam;
    public int epfd;

    public int etaMonth = -1;
    public int etaDay = -1;
    public int etaHour = -1;
    public int etaMinute = -1;

    public ShipInfo() {
    }

    public String getNavStatusDisplay() {
        return ShipDisplayFormatter.navStatus(navStatus);
    }

    public String getPosnQualityDisplay() {
        return ShipDisplayFormatter.positionQuality(posnQuality);
    }

    public String getRaimFlagDisplay() {
        return ShipDisplayFormatter.raimFlag(raimFlag);
    }

    public String getSyncStateDisplay() {
        return ShipDisplayFormatter.syncState(syncState);
    }

    public String getAisClassText() {
        return ShipDisplayFormatter.aisClass(lastMessageType);
    }

    public String getShipTypeDisplay() {
        return ShipDisplayFormatter.shipType(shipType);
    }

    public String getCargo() {
        return ShipDisplayFormatter.cargo(shipType);
    }

    public String getEpfdDisplay() {
        return ShipDisplayFormatter.epfd(epfd);
    }

    public String getEtaDisplay() {
        if (etaMonth <= 0 || etaDay <= 0 || etaHour < 0 || etaMinute < 0) {
            return "";
        }
        return String.format("%d/%d, %02d:%02d", etaMonth, etaDay, etaHour, etaMinute);
    }

    public double getETmin() {
        if (lastReceivedTime <= 0) {
            return -1.0;
        }
        long now = System.currentTimeMillis();
        return (now - lastReceivedTime) / 60000.0;
    }
}
