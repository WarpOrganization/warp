package net.warpgame.engine.server;

import io.netty.buffer.ByteBuf;
import net.warpgame.engine.core.context.service.Service;
import net.warpgame.engine.net.ClockSynchronizer;
import net.warpgame.engine.net.ConnectionState;
import net.warpgame.engine.net.PacketType;
import net.warpgame.engine.net.internalmessage.InternalMessage;
import net.warpgame.engine.net.internalmessage.InternalMessageContent;
import net.warpgame.engine.net.message.InternalMessageQueue;

import static net.warpgame.engine.net.PacketType.*;

/**
 * @author Hubertus
 * Created 13.05.2018
 */
@Service
public class IncomingPacketProcessor {

    private ClientRegistry clientRegistry;
    private ConnectionUtil connectionUtil;
    private InternalMessageQueue internalMessageQueue;

    public IncomingPacketProcessor(ClientRegistry clientRegistry,
                                   ConnectionUtil connectionUtil,
                                   InternalMessageQueue internalMessageQueue) {
        this.clientRegistry = clientRegistry;
        this.connectionUtil = connectionUtil;
        this.internalMessageQueue = internalMessageQueue;
    }

    void processPacket(int packetType, long timestamp, ByteBuf packet) {
        int clientId = packet.readInt();
        switch (packetType) {
            case PACKET_KEEP_ALIVE:
                processKeepAlivePacket(timestamp, clientId, packet);
                break;
            case PACKET_MESSAGE:
                processMessagePacket(timestamp, clientId, packet);
                break;
            case PACKET_MESSAGE_CONFIRMATION:
                processEventConfirmationPacket(timestamp, clientId, packet);
                break;
            case PACKET_CLOCK_SYNCHRONIZATION_REQUEST:
                processClockSynchronizationRequestPacket(timestamp, clientId, packet);
                break;
            case PACKET_CLOCK_SYNCHRONIZATION_RESPONSE:
                processClockSynchronizationResponsePacket(timestamp, clientId, packet);
                break;
        }
    }

    private void processKeepAlivePacket(long timestamp, int clientId, ByteBuf packetData) {
        clientRegistry.updateKeepAlive(clientId);
    }

    private void processMessagePacket(long timestamp, int clientId, ByteBuf packetData) {
        Client client = clientRegistry.getClient(clientId);
        if (client != null) {
            int messageType = packetData.readInt();
            int dependencyId = packetData.readInt();

            client.getIncomingMessageQueue().addMessage(client, messageType, dependencyId, packetData);
            connectionUtil.confirmEvent(dependencyId, client);
        }
    }

    private void processEventConfirmationPacket(long timestamp, int clientId, ByteBuf packetData) {
        int eventDependencyId = packetData.readInt();
        Client c = clientRegistry.getClient(clientId);
        c.confirmMessage(eventDependencyId);
    }

    private void processClockSynchronizationRequestPacket(long timestamp, int clientId, ByteBuf packetData) {
        int requestId = packetData.readInt();
        Client client = clientRegistry.getClient(clientId);
        if (client != null) {
            client.getClockSynchronizer().startRequest(requestId, System.currentTimeMillis());

            ByteBuf packet =
                    connectionUtil.getHeader(PacketType.PACKET_CLOCK_SYNCHRONIZATION_RESPONSE, 4);
            packet.writeInt(requestId);
            connectionUtil.sendPacket(packet, client);
        }
    }

    private void processClockSynchronizationResponsePacket(long timestamp, int clientId, ByteBuf packetData) {
        int requestId = packetData.readInt();
        Client client = clientRegistry.getClient(clientId);
        if (client != null) {
            ClockSynchronizer synchronizer = client.getClockSynchronizer();
            synchronizer.synchronize(timestamp, requestId);

            if (synchronizer.getFinishedSynchronizations() >= 3) {
                internalMessageQueue.pushMessage(new InternalMessage(InternalMessageContent.STATE_CHANGE_LIVE, clientId));
                client.getConnectionStateHolder().setRequestedConnectionState(ConnectionState.LIVE);
            }
        }
    }
}
