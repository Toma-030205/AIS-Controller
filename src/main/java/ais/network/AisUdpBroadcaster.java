package ais.network;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.nio.charset.StandardCharsets;



public class AisUdpBroadcaster {

    private static final int AIS_PORT = 27020;
    private static final String BROADCAST_ADDR = "255.255.255.255";

    private final InetAddress address;
    private final DatagramSocket socket;

    public AisUdpBroadcaster() throws IOException {
        this.address = InetAddress.getByName(BROADCAST_ADDR);
        this.socket = new DatagramSocket();
        this.socket.setBroadcast(true);
    }

    

    public void sendJson(String json) throws IOException {
        byte[] data = json.getBytes(StandardCharsets.UTF_8);
        DatagramPacket packet
                = new DatagramPacket(data, data.length, address, AIS_PORT);
        socket.send(packet);
    }


    /**
     * ソケット解放
     */
    public void close() {
        socket.close();
    }
}
