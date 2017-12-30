package pl.warp.engine.server;

import io.netty.buffer.ByteBuf;
import pl.warp.engine.core.context.service.Service;
import pl.warp.net.Envelope;
import pl.warp.net.EventSerializer;
import pl.warp.net.PacketType;
import pl.warp.net.RemoteEventQueue;

import java.util.ArrayDeque;
import java.util.Collection;

/**
 * @author Hubertus
 * Created 18.12.2017
 */
@Service
public class ServerRemoteEventQueue implements RemoteEventQueue {

    private static final int EVENT_RESEND_INTERVAL = 600;

    private ArrayDeque<Envelope> events = new ArrayDeque<>();
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
        events.push(event);
    }

    @Override
    public synchronized void update() {
        sendNewEvents();
        resendEvents();
    }

    private void resendEvents() {
        long currentTime = System.currentTimeMillis();

        while (currentTime - resendQueue.peek().getSendTime() > EVENT_RESEND_INTERVAL || resendQueue.peek().isConfirmed()) {
            ServerAddressedEnvelope addressedEnvelope = resendQueue.poll();
            if (!addressedEnvelope.isConfirmed()) {
                sendEvent(addressedEnvelope);
                resendQueue.add(addressedEnvelope);
            }
        }
    }

    private void sendNewEvents() {
        Collection<Client> clients = clientRegistry.getClients();
        while (!events.isEmpty()) {
            Envelope envelope = events.poll();

            for (Client c : clients) {
                ServerAddressedEnvelope addressedEnvelope = new ServerAddressedEnvelope();
                addressedEnvelope.setTargetClient(c);
                addressedEnvelope.setConfirmed(false);
                addressedEnvelope.setSerializedEventData(eventSerializer.serialize(envelope));
                addressedEnvelope.setDependencyId(c.getNextEventDependencyId());
                c.addEvent(addressedEnvelope);
                sendEvent(addressedEnvelope);
                resendQueue.add(addressedEnvelope);
            }
        }
    }

    private void sendEvent(ServerAddressedEnvelope addressedEnvelope) {
        addressedEnvelope.setSendTime(System.currentTimeMillis());
        ByteBuf packet = connectionUtil.getHeader(PacketType.PACKET_EVENT, addressedEnvelope.getSerializedEventData().length);
        packet.writeBytes(addressedEnvelope.getSerializedEventData());
        connectionUtil.sendPacket(packet, addressedEnvelope.getTargetClient());
    }
}
