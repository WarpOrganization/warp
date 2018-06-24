package net.warpgame.engine.net;

/**
 * @author Hubertus
 * Created 10.12.2017
 */
public enum PacketType {
    /**
     * Packet with no body
     */
    PACKET_CONNECT,
    /**
     * Body:
     * (int) clientId
     */
    PACKET_CONNECTED,
    /**
     * Packet with no body
     */
    PACKET_CONNECTION_REFUSED,
    /**
     * Packet with no body
     */
    PACKET_KEEP_ALIVE,
    /**
     *
     */
    PACKET_SCENE_STATE,
    /**
     * Body:
     * (int) messageType, (int) eventDependencyId, (byte[]) serializedMessageData
     */
    PACKET_MESSAGE,
    /**
     * Body:
     * (int) eventDependencyId
     */
    PACKET_MESSAGE_CONFIRMATION,
    /**
     * Body:
     * (int) requestId
     */
    PACKET_CLOCK_SYNCHRONIZATION_REQUEST,
    /**
     * (int) requestId
     */
    PACKET_CLOCK_SYNCHRONIZATION_RESPONSE
}
