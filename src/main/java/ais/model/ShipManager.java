package ais.model;

import ais.codec.AisDecoder;
import java.util.Collections;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

public class ShipManager {
    private static final ShipManager instance = new ShipManager();

    private final ConcurrentMap<Integer, ShipInfo> ships = new ConcurrentHashMap<>();
    private final OwnShipInfo ownShip;

    private ShipManager() {
        ownShip = new OwnShipInfo(34.499999, 135.300000, 0.0);
    }

    public static ShipManager getInstance() {
        return instance;
    }

    public OwnShipInfo getOwnShip() {
        return ownShip;
    }

    public void setOwnShip(double lat, double lon, double heading) {
        ownShip.lat = lat;
        ownShip.lon = lon;
        ownShip.heading = heading;
    }

    public void purgeOldShips() {
        long now = System.currentTimeMillis();
        ships.entrySet().removeIf(entry -> {
            ShipInfo ship = entry.getValue();
            double elapsedMinutes = (now - ship.lastReceivedTime) / 60000.0;
            return elapsedMinutes >= 7.0;
        });
    }

    public void update(String nmea) {
        AisDecoder.decodeOptional(nmea).ifPresent(this::update);
    }

    private void update(AisDecoder.AisMessage msg) {
        ships.compute(msg.mmsi, (mmsi, existing) -> {
            ShipInfo ship = existing != null ? existing : new ShipInfo();
            ship.mmsi = msg.mmsi;
            ship.lastReceivedTime = System.currentTimeMillis();
            ship.lastMessageType = msg.messageType;

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
            return ship;
        });
    }

    private void updateDynamic(ShipInfo ship, AisDecoder.AisMessage msg) {
        if (msg.navStatus != null) ship.navStatus = msg.navStatus;
        if (msg.rot != null) ship.rot = msg.rot;
        if (msg.sog != null) ship.sog = msg.sog;
        if (msg.posnQuality != null) ship.posnQuality = msg.posnQuality;
        if (msg.lon != null) ship.lon = msg.lon;
        if (msg.lat != null) ship.lat = msg.lat;
        if (msg.cog != null) ship.cog = msg.cog;
        if (msg.trueHeading != null) ship.trueHeading = msg.trueHeading;
        if (msg.timestamp != null) ship.timestamp = msg.timestamp;
        if (msg.syncState != null) ship.syncState = msg.syncState;
        if (msg.raimFlag != null) ship.raimFlag = msg.raimFlag;
    }

    private void updateStatic(ShipInfo ship, AisDecoder.AisMessage msg) {
        if (msg.imo != null) ship.imo = msg.imo;
        if (msg.callSign != null) ship.callSign = msg.callSign;
        if (msg.vesselName != null) ship.vesselName = msg.vesselName;
        if (msg.shipType != null) ship.shipType = msg.shipType;
        if (msg.epfd != null) ship.epfd = msg.epfd;
        if (msg.etaMonth != null) ship.etaMonth = msg.etaMonth;
        if (msg.etaDay != null) ship.etaDay = msg.etaDay;
        if (msg.etaHour != null) ship.etaHour = msg.etaHour;
        if (msg.etaMinute != null) ship.etaMinute = msg.etaMinute;
        if (msg.destination != null) ship.destination = msg.destination;
        if (msg.draught != null) ship.draught = msg.draught;
        if (msg.dimA != null) ship.dimA = msg.dimA;
        if (msg.dimB != null) ship.dimB = msg.dimB;
        if (msg.dimC != null) ship.dimC = msg.dimC;
        if (msg.dimD != null) ship.dimD = msg.dimD;
        ship.length = ship.dimA + ship.dimB;
        ship.beam = ship.dimC + ship.dimD;
    }

    public Map<Integer, ShipInfo> getShips() {
        return Collections.unmodifiableMap(ships);
    }
}
