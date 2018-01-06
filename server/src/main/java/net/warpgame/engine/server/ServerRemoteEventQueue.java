package net.warpgame.engine.server;

import io.netty.buffer.ByteBuf;
import net.warpgame.engine.core.context.service.Service;
import net.warpgame.engine.net.PacketType;
import net.warpgame.engine.net.event.Envelope;
import net.warpgame.engine.net.event.sender.EventSerializer;
import net.warpgame.engine.net.event.sender.RemoteEventQueue;

import java.util.ArrayDeque;
import java.util.Collection;

/**
 * @author Hubertus
 * Created 18.12.2017
 */
@Service
public class ServerRemoteEventQueue implements RemoteEventQueue {

    private static final int EVENT_RESEND_INTERVAL = 600;

    private ArrayDeque<ServerEnvelope> events = new ArrayDeque<>();
    private ArrayDeque<ServerAddressedEnvelope> resendQueue = new ArrayDeque<>();
    private ClientRegistry clientRegistry;
    private ConnectionUtil connectionUtil;

    private EventSerializer eventSerializer = new EventSerializer();

    public ServerRemoteEventQueue(ClientRegistry clientRegistry, ConnectionUtil connectionUtil) {
        this.clientRegistry = clientRegistry;
        this.connectionUtil = connectionUtil;
    }


    @Override
    public synchronized void pushEvent(Envelope event) {
        events.add((ServerEnvelope) event);
    }

    @Override
    public synchronized void update() {
        sendNewEvents();
        resendEvents();
    }

    private synchronized void resendEvents() {
        long currentTime = System.currentTimeMillis();
        while (!resendQueue.isEmpty()
                && (currentTime - resendQueue.peek().getSendTime() > EVENT_RESEND_INTERVAL
                || resendQueue.peek().isConfirmed())) {
            ServerAddressedEnvelope addressedEnvelope = resendQueue.poll();
            if (!addressedEnvelope.isConfirmed()) {
                sendEvent(addressedEnvelope);
                resendQueue.add(addressedEnvelope);
            }
        }
    }

    private synchronized void sendNewEvents() {
        Collection<Client> clients = clientRegistry.getClients();
        while (!events.isEmpty()) {
            ServerEnvelope envelope = events.poll();
            Client targetClient = envelope.getTargetClient();
            if (targetClient == null) {
                for (Client c : clients) setupEvent(c, envelope);
            } else setupEvent(targetClient, envelope);
        }
    }

    private void setupEvent(Client c, ServerEnvelope envelope) {
        ServerAddressedEnvelope addressedEnvelope = new ServerAddressedEnvelope();
        addressedEnvelope.setTargetClient(c);
        addressedEnvelope.setConfirmed(false);
        addressedEnvelope.setSerializedEventData(eventSerializer.serialize(envelope));
        addressedEnvelope.setDependencyId(c.getNextEventDependencyId());
        c.addEvent(addressedEnvelope);
        sendEvent(addressedEnvelope);
        resendQueue.add(addressedEnvelope);
    }

    private void sendEvent(ServerAddressedEnvelope addressedEnvelope) {
        addressedEnvelope.setSendTime(System.currentTimeMillis());
        ByteBuf packet = connectionUtil.getHeader(PacketType.PACKET_EVENT, addressedEnvelope.getSerializedEventData().length + 12);
        packet.writeInt(addressedEnvelope.getEventType());
        packet.writeInt(addressedEnvelope.getDependencyId());
        packet.writeInt(addressedEnvelope.getTargetComponentId());
        packet.writeBytes(addressedEnvelope.getSerializedEventData());
        connectionUtil.sendPacket(packet, addressedEnvelope.getTargetClient());
    }
}
