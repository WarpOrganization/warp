package net.warpgame.engine.server;

import io.netty.buffer.ByteBuf;
import net.warpgame.engine.core.context.service.Profile;
import net.warpgame.engine.core.context.service.Service;
import net.warpgame.engine.net.ClockSynchronizer;
import net.warpgame.engine.net.ConnectionState;
import net.warpgame.engine.net.PacketType;
import net.warpgame.engine.net.message.InternalMessageSource;
import net.warpgame.engine.net.messagetypes.internalmessage.InternalMessage;
import net.warpgame.engine.net.messagetypes.internalmessage.InternalMessageContent;

/**
 * @author Hubertus
 * Created 13.05.2018
 */
@Service
@Profile("server")
public class IncomingPacketProcessor {

    private ClientRegistry clientRegistry;
    private ConnectionUtil connectionUtil;
    private InternalMessageSource internalMessageSource;

    public IncomingPacketProcessor(ClientRegistry clientRegistry,
                                   ConnectionUtil connectionUtil,
                                   InternalMessageSource internalMessageSource) {
        this.clientRegistry = clientRegistry;
        this.connectionUtil = connectionUtil;
        this.internalMessageSource = internalMessageSource;
    }

    void processPacket(PacketType packetType, long timestamp, ByteBuf packet) {
        int clientId = packet.readInt();
        clientRegistry.updateActivity(clientId);
        switch (packetType) {
            case PACKET_KEEP_ALIVE:
                processKeepAlivePacket(timestamp, clientId, packet);
                break;
            case PACKET_MESSAGE:
                processMessagePacket(timestamp, clientId, packet);
                break;
            case PACKET_MESSAGE_CONFIRMATION:
                processMessageConfirmationPacket(timestamp, clientId, packet);
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
        clientRegistry.updateActivity(clientId);
        Client client = clientRegistry.getClient(clientId);
        client.updateRTT(timestamp);
        connectionUtil.sendPacket(
                connectionUtil.getHeader(PacketType.PACKET_KEEP_ALIVE, 0),
                client
        );
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

    private void processMessageConfirmationPacket(long timestamp, int clientId, ByteBuf packetData) {
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
                internalMessageSource.pushMessage(new InternalMessage(InternalMessageContent.STATE_CHANGE_LIVE, clientId));
                client.getConnectionStateHolder().setRequestedConnectionState(ConnectionState.LIVE);
            }
        }
    }
}
