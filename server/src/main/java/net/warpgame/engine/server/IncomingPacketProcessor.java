package net.warpgame.engine.server;

import io.netty.buffer.ByteBuf;
import net.warpgame.engine.core.context.service.Service;
import net.warpgame.engine.net.ClockSynchronizer;
import net.warpgame.engine.net.PacketType;

import static net.warpgame.engine.net.PacketType.*;

/**
 * @author Hubertus
 * Created 13.05.2018
 */
@Service
public class IncomingPacketProcessor {

    private ClientRegistry clientRegistry;
    private ConnectionUtil connectionUtil;

    public IncomingPacketProcessor(ClientRegistry clientRegistry,
                                   ConnectionUtil connectionUtil) {
        this.clientRegistry = clientRegistry;
        this.connectionUtil = connectionUtil;
    }

    void processPacket(int packetType, long timestamp, ByteBuf packet) {
        int clientId = packet.readInt();

        switch (packetType) {
            case PACKET_KEEP_ALIVE:
                processKeepAlivePacket(timestamp, clientId, packet);
                break;
            case PACKET_EVENT:
                processEventPacket(timestamp, clientId, packet);
                break;
            case PACKET_EVENT_CONFIRMATION:
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

    private void processEventPacket(long timestamp, int clientId, ByteBuf packetData) {
        Client client = clientRegistry.getClient(clientId);
        if (client != null) {
            int eventType = packetData.readInt();
            int dependencyId = packetData.readInt();
            int targetComponentId = packetData.readInt();
            client.getEventReceiver().addEvent(packetData, targetComponentId, eventType, dependencyId, timestamp);
            connectionUtil.confirmEvent(dependencyId, client);
        }
    }

    private void processEventConfirmationPacket(long timestamp, int clientId, ByteBuf packetData) {
        int eventDependencyId = packetData.readInt();
        Client c = clientRegistry.getClient(clientId);
        c.confirmEvent(eventDependencyId);
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
                System.out.println(synchronizer.getDelta());
                //TODO issue state change to loading
            }
        }
    }
}
