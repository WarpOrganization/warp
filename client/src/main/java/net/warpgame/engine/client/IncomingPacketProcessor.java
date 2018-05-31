package net.warpgame.engine.client;

import io.netty.buffer.ByteBuf;
import net.warpgame.engine.core.component.ComponentRegistry;
import net.warpgame.engine.core.context.service.Service;
import net.warpgame.engine.net.ConnectionState;
import net.warpgame.engine.net.PacketType;
import net.warpgame.engine.net.event.InternalMessageEnvelope;
import net.warpgame.engine.net.event.StateChangeHandler;
import net.warpgame.engine.net.event.StateChangeRequestMessage;
import net.warpgame.engine.net.event.receiver.MessageReceiver;

import static net.warpgame.engine.net.PacketType.*;

/**
 * @author Hubertus
 * Created 13.05.2018
 */
@Service
public class IncomingPacketProcessor {

    private ConnectionService connectionService;
    private SerializedSceneHolder sceneHolder;
    private MessageReceiver messageReceiver;
    private ClientRemoteEventQueue eventQueue;
    private ComponentRegistry componentRegistry;

    public IncomingPacketProcessor(ConnectionService connectionService,
                                   SerializedSceneHolder sceneHolder,
                                   ComponentRegistry componentRegistry,
                                   ClientRemoteEventQueue eventQueue,
                                   StateChangeHandler stateChangeHandler) {
        this.connectionService = connectionService;
        this.sceneHolder = sceneHolder;
        this.messageReceiver = new MessageReceiver(componentRegistry, stateChangeHandler);
        this.eventQueue = eventQueue;
        this.componentRegistry = componentRegistry;
    }

    public void processPacket(ByteBuf packet) {
        int packetType = packet.readInt();
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
            case PACKET_MESSAGE:
                processEventPacket(timestamp, packet);
                break;
            case PACKET_INTERNAL_MESSAGE:
                processInternalMessagePacket(timestamp, packet);
                break;
            case PACKET_MESSAGE_CONFIRMATION:
                processEventConfirmationPacket(timestamp, packet);
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
        eventQueue.pushEvent(
                new InternalMessageEnvelope(
                        new StateChangeRequestMessage(ConnectionState.SYNCHRONIZING, connectionService.getClientId())));
    }

    private void processConnectionRefusedPacket(long timestamp, ByteBuf packetData) {
        System.out.println("Connection refused!");
    }

    private void processSceneStatePacket(long timestamp, ByteBuf packetData) {
        sceneHolder.offerScene(timestamp, packetData);
    }

    private void processEventPacket(long timestamp, ByteBuf packetData) {
        int eventType = packetData.readInt();
        int dependencyId = packetData.readInt();
        int targetComponentId = packetData.readInt();
        messageReceiver.addEvent(packetData, targetComponentId, eventType, dependencyId, timestamp);
        connectionService.sendMessageConfirmationPacket(dependencyId);
    }

    private void processInternalMessagePacket(long timestamp, ByteBuf packetData) {
        int dependencyId = packetData.readInt();
        messageReceiver.addInternalMessage(packetData, dependencyId, timestamp);
        connectionService.sendMessageConfirmationPacket(dependencyId);
    }

    private void processEventConfirmationPacket(long timestamp, ByteBuf packetData) {
        int eventId = packetData.readInt();
        eventQueue.confirmEvent(eventId);
    }

    private void processClockSynchronizationResponsePacket(long timestamp, ByteBuf packetData) {
        int requestId = packetData.readInt();
        connectionService.getServer().getClockSynchronizer().synchronize(timestamp, requestId);

        ByteBuf responsePacket = connectionService.getHeader(PacketType.PACKET_CLOCK_SYNCHRONIZATION_RESPONSE, 4);
        responsePacket.writeInt(requestId);
        connectionService.sendPacket(responsePacket);

        if (connectionService.getServer().getClockSynchronizer().getFinishedSynchronizations() >= 3) {
            connectionService.getServer().getConnectionStateHolder().setRequestedConnectionState(ConnectionState.LIVE);
            eventQueue.pushEvent(
                    new InternalMessageEnvelope(
                            new StateChangeRequestMessage(ConnectionState.LIVE, connectionService.getClientId())));
        }
    }
}
