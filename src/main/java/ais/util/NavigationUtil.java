package ais.util;


public class NavigationUtil {
    private static final double EARTH_RADIUS_KM = 6371.0;

    /**
     * 緯度経度を使って、自船 → 他船までの距離 (海里) を返す
     */
    public static double calcRangeNm(double lat1, double lon1, double lat2, double lon2) {
        double km = haversineDistanceKm(lat1, lon1, lat2, lon2);
        return km * 0.539957;  // 1 km ≒ 0.539957 海里
    }

    /**
     * 緯度経度を使って距離 (km) を返す (Haversine)
     */
    public static double haversineDistanceKm(double lat1, double lon1, double lat2, double lon2) {
        double φ1 = Math.toRadians(lat1);
        double φ2 = Math.toRadians(lat2);
        double dφ = Math.toRadians(lat2 - lat1);
        double dλ = Math.toRadians(lon2 - lon1);

        double sin_dφ_2 = Math.sin(dφ / 2);
        double sin_dλ_2 = Math.sin(dλ / 2);

        double a = sin_dφ_2 * sin_dφ_2
                 + Math.cos(φ1) * Math.cos(φ2)
                 * sin_dλ_2 * sin_dλ_2;
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        return EARTH_RADIUS_KM * c;
    }

    /**
     * 自船 → 他船までの初期方位 (BRG°, 0° = 真北, 時計回り) を返す
     */
    public static double calcBearingDeg(double lat1, double lon1, double lat2, double lon2) {
        double φ1 = Math.toRadians(lat1);
        double φ2 = Math.toRadians(lat2);
        double λ1 = Math.toRadians(lon1);
        double λ2 = Math.toRadians(lon2);

        double dλ = λ2 - λ1;

        double y = Math.sin(dλ) * Math.cos(φ2);
        double x = Math.cos(φ1) * Math.sin(φ2)
                 - Math.sin(φ1) * Math.cos(φ2) * Math.cos(dλ);
        double θ = Math.atan2(y, x);
        double bearing = Math.toDegrees(θ);
        // 正規化（0-360°）
        return (bearing + 360.0) % 360.0;
    }

    /**
    * TCPA（最接近時間、分）を計算する
    */
    public static double calcTCPA(
        double latOwn, double lonOwn, double sogOwn, double cogOwn,
        double latTgt, double lonTgt, double sogTgt, double cogTgt) {

    // 1) XY座標へ（簡易：小範囲なら平面換算でOK）
    double xOwn = lonOwn * Math.cos(Math.toRadians(latOwn));
    double yOwn = latOwn;

    double xTgt = lonTgt * Math.cos(Math.toRadians(latTgt));
    double yTgt = latTgt;

    double Rx = xTgt - xOwn;
    double Ry = yTgt - yOwn;

    // 2) 速度ベクトル（ノット → 度/時 のままでOK）
    double vxOwn = sogOwn * Math.sin(Math.toRadians(cogOwn));
    double vyOwn = sogOwn * Math.cos(Math.toRadians(cogOwn));

    double vxTgt = sogTgt * Math.sin(Math.toRadians(cogTgt));
    double vyTgt = sogTgt * Math.cos(Math.toRadians(cogTgt));

    double vRx = vxTgt - vxOwn;
    double vRy = vyTgt - vyOwn;

    double vR2 = vRx * vRx + vRy * vRy;
    if (vR2 < 1e-6) return Double.POSITIVE_INFINITY; // 相対速度ゼロ

    // TCPA（時間：時間単位）
    double tcpaHours = - (Rx * vRx + Ry * vRy) / vR2;

    if (tcpaHours < 0) return Double.POSITIVE_INFINITY; // すでに接近済み

    return tcpaHours * 60.0; // 分で返す
    }
    
    /** ETA（所要時間, 分）を計算（距離NM ÷ 速度Knots） */
    public static double calcEtaMin(double rangeNm, double sogKnots) {
        if (sogKnots <= 0) return Double.POSITIVE_INFINITY;
        return (rangeNm / sogKnots) * 60.0;
    
    }
}    
