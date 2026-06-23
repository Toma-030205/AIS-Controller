package ais.network;

import ais.model.ShipManager;
import java.util.logging.Level;
import java.util.logging.Logger;



public class UdpReceiver implements Runnable {
    private static final Logger LOGGER = Logger.getLogger(UdpReceiver.class.getName());

    private final int port;
    private final ShipManager shipManager;
    private final Runnable onTableUpdate;
    private final java.util.function.Consumer<String> onMessage;

    private Thread thread;

    public UdpReceiver(
            int port,
            ShipManager shipManager,
            Runnable onTableUpdate,
            java.util.function.Consumer<String> onMessage) {

        this.port = port;
        this.shipManager = shipManager;
        this.onTableUpdate = onTableUpdate;
        this.onMessage = onMessage;
    }

    public void start() {
        thread = new Thread(this, "UDP-Receiver");
        thread.setDaemon(true);
        thread.start();
    }

    @Override
    public void run() {
        try (java.net.DatagramSocket socket = new java.net.DatagramSocket(port)) {

            byte[] buffer = new byte[1024];
            long lastPurgeTime = System.currentTimeMillis();

            onMessage.accept("UDP ポート " + port + " で待機中...\n");

            while (!Thread.currentThread().isInterrupted()) {

                java.net.DatagramPacket packet =
                        new java.net.DatagramPacket(buffer, buffer.length);
                socket.receive(packet);

                String message = new String(
                        packet.getData(), 0, packet.getLength(), "UTF-8");

                onMessage.accept(message + "\n");

                for (String line : message.split("\\r?\\n")) {
                    if (line.isEmpty()) continue;
                    try {
                        shipManager.update(line);
                    } catch (Exception ex) {
                        LOGGER.log(Level.FINE, "Invalid NMEA skipped: {0}", line);
                    }
                }

                long now = System.currentTimeMillis();
                if (now - lastPurgeTime >= 60000) {
                    shipManager.purgeOldShips();
                    lastPurgeTime = now;
                }

                onTableUpdate.run();
            }

        } catch (Exception ex) {
            onMessage.accept("エラー: " + ex.getMessage() + "\n");
        }
    }
}
