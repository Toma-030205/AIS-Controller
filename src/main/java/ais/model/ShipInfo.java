package ais.model;

public class ShipInfo {
    private int mmsi;
    private long lastReceivedTime;

    private double lat;
    private double lon;
    private double sog;
    private double cog;
    private double trueHeading;
    private int navStatus;
    private double rot;
    private int posnQuality;
    private int raimFlag;
    private int timestamp;
    private int syncState;

    private int imo;
    private String callSign;
    private String vesselName;
    private int lastMessageType;

    private int shipType;
    private String CargoType;
    private String destination;
    private double draught;
    private int dimA;
    private int dimB;
    private int dimC;
    private int dimD;
    private int length;
    private int beam;
    private int epfd;

    private int etaMonth = -1;
    private int etaDay = -1;
    private int etaHour = -1;
    private int etaMinute = -1;

    public ShipInfo() {
    }

    public void recordMessage(int mmsi, int messageType, long receivedAt) {
        this.mmsi = mmsi;
        this.lastMessageType = messageType;
        this.lastReceivedTime = receivedAt;
    }

    public void applyDynamic(AisDynamicData data) {
        if (data.navStatus != null) navStatus = data.navStatus;
        if (data.rot != null) rot = data.rot;
        if (data.sog != null) sog = data.sog;
        if (data.posnQuality != null) posnQuality = data.posnQuality;
        if (data.lon != null) lon = data.lon;
        if (data.lat != null) lat = data.lat;
        if (data.cog != null) cog = data.cog;
        if (data.trueHeading != null) trueHeading = data.trueHeading;
        if (data.timestamp != null) timestamp = data.timestamp;
        if (data.syncState != null) syncState = data.syncState;
        if (data.raimFlag != null) raimFlag = data.raimFlag;
    }

    public void applyStatic(AisStaticData data) {
        if (data.imo != null) imo = data.imo;
        if (data.callSign != null) callSign = data.callSign;
        if (data.vesselName != null) vesselName = data.vesselName;
        if (data.shipType != null) shipType = data.shipType;
        if (data.epfd != null) epfd = data.epfd;
        if (data.etaMonth != null) etaMonth = data.etaMonth;
        if (data.etaDay != null) etaDay = data.etaDay;
        if (data.etaHour != null) etaHour = data.etaHour;
        if (data.etaMinute != null) etaMinute = data.etaMinute;
        if (data.destination != null) destination = data.destination;
        if (data.draught != null) draught = data.draught;
        if (data.dimA != null) dimA = data.dimA;
        if (data.dimB != null) dimB = data.dimB;
        if (data.dimC != null) dimC = data.dimC;
        if (data.dimD != null) dimD = data.dimD;
        updateDimensions();
    }

    public boolean isStale(long now, double thresholdMinutes) {
        return (now - lastReceivedTime) / 60000.0 >= thresholdMinutes;
    }

    public int getMmsi() {
        return mmsi;
    }

    public double getLat() {
        return lat;
    }

    public double getLon() {
        return lon;
    }

    public double getSog() {
        return sog;
    }

    public double getCog() {
        return cog;
    }

    public double getTrueHeading() {
        return trueHeading;
    }

    public double getRot() {
        return rot;
    }

    public int getTimestamp() {
        return timestamp;
    }

    public int getImo() {
        return imo;
    }

    public String getCallSign() {
        return callSign;
    }

    public String getVesselName() {
        return vesselName == null ? "" : vesselName;
    }

    public String getDestination() {
        return destination == null ? "" : destination;
    }

    public double getDraught() {
        return draught;
    }

    public int getLength() {
        return length;
    }

    public int getBeam() {
        return beam;
    }

    private void updateDimensions() {
        length = dimA + dimB;
        beam = dimC + dimD;
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

    public static class AisDynamicData {
        public Integer navStatus;
        public Double rot;
        public Double sog;
        public Integer posnQuality;
        public Double lon;
        public Double lat;
        public Double cog;
        public Double trueHeading;
        public Integer timestamp;
        public Integer syncState;
        public Integer raimFlag;
    }

    public static class AisStaticData {
        public Integer imo;
        public String callSign;
        public String vesselName;
        public Integer shipType;
        public Integer epfd;
        public Integer etaMonth;
        public Integer etaDay;
        public Integer etaHour;
        public Integer etaMinute;
        public String destination;
        public Double draught;
        public Integer dimA;
        public Integer dimB;
        public Integer dimC;
        public Integer dimD;
    }
}
