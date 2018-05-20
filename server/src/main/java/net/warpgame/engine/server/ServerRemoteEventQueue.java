package net.warpgame.engine.server;

import io.netty.buffer.ByteBuf;
import net.warpgame.engine.core.context.service.Service;
import net.warpgame.engine.net.PacketType;
import net.warpgame.engine.net.event.Envelope;
import net.warpgame.engine.net.event.sender.EventSerializer;
import net.warpgame.engine.net.event.sender.RemoteEventQueue;
import net.warpgame.engine.server.envelope.ServerAddresedEnvelope;
import net.warpgame.engine.server.envelope.ServerAddressedEventEnvelope;
import net.warpgame.engine.server.envelope.ServerAddressedInternalMessageEnvelope;
import net.warpgame.engine.server.envelope.ServerEnvelope;

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
    private ArrayDeque<ServerAddresedEnvelope> resendQueue = new ArrayDeque<>();
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
            ServerAddresedEnvelope addressedEnvelope = resendQueue.poll();
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
        ServerAddresedEnvelope addressedEnvelope =
                envelope.isInternal() ? new ServerAddressedInternalMessageEnvelope() : new ServerAddressedEventEnvelope();
        addressedEnvelope.setTargetClient(c);
        addressedEnvelope.setConfirmed(false);
        addressedEnvelope.setSerializedEventData(eventSerializer.serialize(envelope));
        addressedEnvelope.setDependencyId(c.getNextEventDependencyId());
        if (!envelope.isInternal()) addressedEnvelope.setTargetComponent(envelope.getTargetComponent());
        if (envelope.isShouldConfirm()) addressedEnvelope.setShouldConfirm(true);
        c.addEvent(addressedEnvelope);
        sendEvent(addressedEnvelope);
        resendQueue.add(addressedEnvelope);
    }

    private void sendEvent(ServerAddresedEnvelope envelope) {
        envelope.setSendTime(System.currentTimeMillis());
        if (envelope.isInternal()) sendInternalMessage(envelope);
        else sendStandardEvent(envelope);
    }

    private void sendStandardEvent(ServerAddresedEnvelope envelope) {
        ByteBuf packet = connectionUtil.getHeader(PacketType.PACKET_EVENT, envelope.getSerializedEventData().length + 12);
        packet.writeInt(envelope.getEventType());
        packet.writeInt(envelope.getDependencyId());
        packet.writeInt(envelope.getTargetComponent().getId());
        packet.writeBytes(envelope.getSerializedEventData());
        connectionUtil.sendPacket(packet, envelope.getTargetClient());
    }

    private void sendInternalMessage(ServerAddresedEnvelope envelope) {
        ByteBuf packet = connectionUtil.getHeader(PacketType.PACKET_INTERNAL_MESSAGE, envelope.getSerializedEventData().length + 4);
        packet.writeInt(envelope.getDependencyId());
        packet.writeBytes(envelope.getSerializedEventData());
        connectionUtil.sendPacket(packet, envelope.getTargetClient());
    }
}
