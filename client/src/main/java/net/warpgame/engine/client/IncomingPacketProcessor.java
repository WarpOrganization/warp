package net.warpgame.engine.client;

import io.netty.buffer.ByteBuf;
import net.warpgame.engine.core.context.service.Profile;
import net.warpgame.engine.core.context.service.Service;
import net.warpgame.engine.net.ConnectionState;
import net.warpgame.engine.net.PacketType;
import net.warpgame.engine.net.internalmessage.InternalMessage;
import net.warpgame.engine.net.internalmessage.InternalMessageContent;
import net.warpgame.engine.net.message.InternalMessageQueue;

/**
 * @author Hubertus
 * Created 13.05.2018
 */
@Service
@Profile("client")
public class IncomingPacketProcessor {

    private ConnectionService connectionService;
    private SerializedSceneHolder sceneHolder;
    private InternalMessageQueue internalMessageQueue;
    private PacketType[] packetTypes = PacketType.values();

    public IncomingPacketProcessor(ConnectionService connectionService,
                                   SerializedSceneHolder sceneHolder,
                                   InternalMessageQueue internalMessageQueue) {
        this.connectionService = connectionService;
        this.sceneHolder = sceneHolder;
        this.internalMessageQueue = internalMessageQueue;
    }

    public void processPacket(ByteBuf packet) {
        PacketType packetType = packetTypes[packet.readInt()];
        long timestamp = packet.readLong();
        switch (packetType) {
            case PACKET_CONNECTED:
                processConnectedPacket(timestamp, packet);
                break;
            case PACKET_CONNECTION_REFUSED:
                processConnectionRefusedPacket(timestamp, packet);
                break;
            case PACKET_SCENE_STATE:
                processSceneStatePacket(timestamp, packet);
                break;
            case PACKET_MESSAGE:
                processMessagePacket(timestamp, packet);
                break;
            case PACKET_MESSAGE_CONFIRMATION:
                processMessageConfirmationPacket(timestamp, packet);
                break;
            case PACKET_CLOCK_SYNCHRONIZATION_RESPONSE:
                processClockSynchronizationResponsePacket(timestamp, packet);
                break;
        }
    }

    private void processConnectedPacket(long timestamp, ByteBuf packetData) {
        int clientId = packetData.readInt();
        connectionService.setClientCredentials(clientId, 0);
        connectionService.getServer().getConnectionStateHolder().setRequestedConnectionState(ConnectionState.SYNCHRONIZING);
        internalMessageQueue.pushMessage(new InternalMessage(InternalMessageContent.STATE_CHANGE_SYNCHRONIZING, 0));
    }

    private void processConnectionRefusedPacket(long timestamp, ByteBuf packetData) {
        System.out.println("Connection refused!");
    }

    private void processSceneStatePacket(long timestamp, ByteBuf packetData) {
        sceneHolder.offerScene(timestamp, packetData);
    }

    private void processMessagePacket(long timestamp, ByteBuf packetData) {
        int messageType = packetData.readInt();
        int dependencyId = packetData.readInt();
        connectionService
                .getServer()
                .getIncomingMessageQueue()
                .addMessage(connectionService.getServer(), messageType, dependencyId, packetData);
        connectionService.sendMessageConfirmationPacket(dependencyId);
    }

    private void processMessageConfirmationPacket(long timestamp, ByteBuf packetData) {
        int messageDependencyId = packetData.readInt();
        connectionService.getServer().confirmMessage(messageDependencyId);
    }

    private void processClockSynchronizationResponsePacket(long timestamp, ByteBuf packetData) {
        int requestId = packetData.readInt();
        connectionService.getServer().getClockSynchronizer().synchronize(timestamp, requestId);

        ByteBuf responsePacket = connectionService.getHeader(PacketType.PACKET_CLOCK_SYNCHRONIZATION_RESPONSE, 4);
        responsePacket.writeInt(requestId);
        connectionService.sendPacket(responsePacket);

        if (connectionService.getServer().getClockSynchronizer().getFinishedSynchronizations() >= 3) {
            connectionService.getServer().getConnectionStateHolder().setRequestedConnectionState(ConnectionState.LIVE);
            internalMessageQueue.pushMessage(new InternalMessage(InternalMessageContent.STATE_CHANGE_LIVE, 0));
        }
    }
}
