package pl.warp.enigne.client;

import io.netty.buffer.ByteBuf;
import pl.warp.engine.core.context.service.Service;
import pl.warp.net.*;
import pl.warp.net.event.AddressedEnvelope;
import pl.warp.net.event.Envelope;
import pl.warp.net.event.sender.EventSerializer;
import pl.warp.net.event.sender.RemoteEventQueue;

import java.util.ArrayDeque;
import java.util.HashMap;

/**
 * @author Hubertus
 * Created 19.12.2017
 */
@Service
public class ClientRemoteEventQueue implements RemoteEventQueue {

    private static final int EVENT_RESEND_INTERVAL = 600;

    private ArrayDeque<Envelope> events = new ArrayDeque<>();
    private ArrayDeque<AddressedEnvelope> resendQueue = new ArrayDeque<>();
    private HashMap<Integer, AddressedEnvelope> confirmationMap = new HashMap<>();

    private EventSerializer eventSerializer = new EventSerializer();
    private ConnectionService connectionService;

    private int eventDependencyIdCounter = 0;

    public ClientRemoteEventQueue(ConnectionService connectionService) {
        this.connectionService = connectionService;
    }

    @Override
    public synchronized void pushEvent(Envelope event) {
        events.push(event);
    }

    @Override
    public void update() {
        sendNewEvents();
        resendEvents();
    }

    private void resendEvents() {
        long currentTime = System.currentTimeMillis();
        if (!resendQueue.isEmpty()) {
            while (currentTime - resendQueue.peek().getSendTime() > EVENT_RESEND_INTERVAL
                    || resendQueue.peek().isConfirmed()) {
                AddressedEnvelope addressedEnvelope = resendQueue.poll();
                if (addressedEnvelope.isConfirmed()) {
                    confirmationMap.remove(addressedEnvelope.getDependencyId());
                } else {
                    addressedEnvelope.setSendTime(currentTime);
                    resendQueue.add(addressedEnvelope);
                    sendEvent(addressedEnvelope);
                }
            }
        }
    }

    private void sendNewEvents() {
        while (!events.isEmpty()) {
            Envelope envelope = events.pop();
            eventDependencyIdCounter++;
            AddressedEnvelope addressedEnvelope = new AddressedEnvelope();
            addressedEnvelope.setConfirmed(false);
            addressedEnvelope.setDependencyId(eventDependencyIdCounter);
            addressedEnvelope.setSerializedEventData(eventSerializer.serialize(envelope));

            confirmationMap.put(eventDependencyIdCounter, addressedEnvelope);
            resendQueue.add(addressedEnvelope);
            sendEvent(addressedEnvelope);
        }
    }

    private void sendEvent(AddressedEnvelope envelope) {
        ByteBuf packet = connectionService.getHeader(PacketType.PACKET_EVENT, envelope.getSerializedEventData().length);
        packet.writeBytes(envelope.getSerializedEventData());
        connectionService.sendPacket(packet);
    }

    public synchronized void confirmEvent(int eventDependencyId) {
        AddressedEnvelope addressedEnvelope = confirmationMap.get(eventDependencyId);
        if (addressedEnvelope != null) addressedEnvelope.setConfirmed(true);
    }
}
