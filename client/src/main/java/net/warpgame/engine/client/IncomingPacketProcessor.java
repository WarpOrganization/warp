package net.warpgame.engine.client;

import io.netty.buffer.ByteBuf;
import net.warpgame.engine.core.component.ComponentRegistry;
import net.warpgame.engine.core.context.service.Service;
import net.warpgame.engine.net.ConnectionState;
import net.warpgame.engine.net.event.InternalMessageHandler;
import net.warpgame.engine.net.event.receiver.EventReceiver;

import static net.warpgame.engine.net.PacketType.*;

/**
 * @author Hubertus
 * Created 13.05.2018
 */
@Service
public class IncomingPacketProcessor {

    private ConnectionService connectionService;
    private SerializedSceneHolder sceneHolder;
    private EventReceiver eventReceiver;
    private ClientRemoteEventQueue eventQueue;

    public IncomingPacketProcessor(ConnectionService connectionService,
                                   SerializedSceneHolder sceneHolder,
                                   ComponentRegistry componentRegistry,
                                   ClientRemoteEventQueue eventQueue,
                                   InternalMessageHandler internalMessageHandler) {
        this.connectionService = connectionService;
        this.sceneHolder = sceneHolder;
        this.eventReceiver = new EventReceiver(componentRegistry, internalMessageHandler);
        this.eventQueue = eventQueue;
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
            case PACKET_EVENT:
                processEventPacket(timestamp, packet);
                break;
            case PACKET_INTERNAL_MESSAGE:
                processInternalMessagePacket(timestamp, packet);
                break;
            case PACKET_EVENT_CONFIRMATION:
                processEventConfirmationPacket(timestamp, packet);
            case PACKET_CLOCK_SYNCHRONIZATION_RESPONSE:
                processClockSynchronizationResponsePacket(timestamp, packet);
                break;
        }
    }

    private void processConnectedPacket(long timestamp, ByteBuf packetData) {
        int clientId = packetData.readInt();
        connectionService.setClientCredentials(clientId, 0);
        //TODO issue state change to SYNCHRONIZING
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
        eventReceiver.addEvent(packetData, targetComponentId, eventType, dependencyId, timestamp);
        connectionService.confirmEvent(dependencyId);
    }

    private void processInternalMessagePacket(long timestamp, ByteBuf packetData) {
        int dependencyId = packetData.readInt();
        eventReceiver.addInternalMessage(packetData, dependencyId, timestamp);
        connectionService.confirmEvent(dependencyId);
    }

    private void processEventConfirmationPacket(long timestamp, ByteBuf packetData) {
        int eventId = packetData.readInt();
        eventQueue.confirmEvent(eventId);
    }

    private void processClockSynchronizationResponsePacket(long timestamp, ByteBuf packetData) {
        int requestId = packetData.readInt();
        connectionService.getClockSynchronizer().synchronize(timestamp, requestId);
        if (connectionService.getClockSynchronizer().getFinishedSynchronizations() >= 3) {
            connectionService.setConnectionState(ConnectionState.LIVE);
            //TODO issue state change to LOADING
        }
    }
}
