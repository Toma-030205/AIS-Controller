package ais.model;

import ais.codec.AisDecoder;

public final class AisMessageMapper {

    private AisMessageMapper() {
    }

    public static void applyTo(ShipInfo ship, AisDecoder.AisMessage msg) {
        ship.recordMessage(msg.mmsi, msg.messageType, System.currentTimeMillis());

        switch (msg.messageType) {
            case 1:
            case 2:
            case 3:
                updateDynamic(ship, msg);
                break;
            case 5:
                updateStatic(ship, msg);
                break;
            default:
                break;
        }
    }

    private static void updateDynamic(ShipInfo ship, AisDecoder.AisMessage msg) {
        ShipInfo.AisDynamicData data = new ShipInfo.AisDynamicData();
        data.navStatus = msg.navStatus;
        data.rot = msg.rot;
        data.sog = msg.sog;
        data.posnQuality = msg.posnQuality;
        data.lon = msg.lon;
        data.lat = msg.lat;
        data.cog = msg.cog;
        data.trueHeading = msg.trueHeading;
        data.timestamp = msg.timestamp;
        data.syncState = msg.syncState;
        data.raimFlag = msg.raimFlag;
        ship.applyDynamic(data);
    }

    private static void updateStatic(ShipInfo ship, AisDecoder.AisMessage msg) {
        ShipInfo.AisStaticData data = new ShipInfo.AisStaticData();
        data.imo = msg.imo;
        data.callSign = msg.callSign;
        data.vesselName = msg.vesselName;
        data.shipType = msg.shipType;
        data.epfd = msg.epfd;
        data.etaMonth = msg.etaMonth;
        data.etaDay = msg.etaDay;
        data.etaHour = msg.etaHour;
        data.etaMinute = msg.etaMinute;
        data.destination = msg.destination;
        data.draught = msg.draught;
        data.dimA = msg.dimA;
        data.dimB = msg.dimB;
        data.dimC = msg.dimC;
        data.dimD = msg.dimD;
        ship.applyStatic(data);
    }
}
