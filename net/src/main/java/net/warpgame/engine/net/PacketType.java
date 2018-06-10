package net.warpgame.engine.net;

/**
 * @author Hubertus
 * Created 10.12.2017
 */
public class PacketType {
    /**
     * Packet with no body
     */
    public static final int PACKET_CONNECT = 1;
    /**
     * Body:
     * (int) clientId
     */
    public static final int PACKET_CONNECTED = 2;
    /**
     * Packet with no body
     */
    public static final int PACKET_CONNECTION_REFUSED = 3;
    /**
     * Packet with no body
     */
    public static final int PACKET_KEEP_ALIVE = 4;
    /**
     *
     */
    public static final int PACKET_SCENE_STATE = 5;
    /**
     * Body:
     * (int) messageType, (int) eventDependencyId, (byte[]) serializedMessageData
     */
    public static final int PACKET_MESSAGE = 6;
    /**
     * Body:
     * (int) eventDependencyId
     */
    public static final int PACKET_MESSAGE_CONFIRMATION = 8;
    /**
     * Body:
     * (int) requestId
     */
    public static final int PACKET_CLOCK_SYNCHRONIZATION_REQUEST = 9;
    /**
     * (int) requestId
     */
    public static final int PACKET_CLOCK_SYNCHRONIZATION_RESPONSE = 10;
}
