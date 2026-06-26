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
        ownShip.setPositionAndHeading(lat, lon, heading);
    }

    public void purgeOldShips() {
        long now = System.currentTimeMillis();
        ships.entrySet().removeIf(entry -> {
            ShipInfo ship = entry.getValue();
            return ship.isStale(now, 7.0);
        });
    }

    public void update(String nmea) {
        AisDecoder.decodeOptional(nmea).ifPresent(this::update);
    }

    private void update(AisDecoder.AisMessage msg) {
        ships.compute(msg.mmsi, (mmsi, existing) -> {
            ShipInfo ship = existing != null ? existing : new ShipInfo();
            AisMessageMapper.applyTo(ship, msg);
            return ship;
        });
    }

    public Map<Integer, ShipInfo> getShips() {
        return Collections.unmodifiableMap(ships);
    }
}
