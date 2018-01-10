package net.warpgame.engine.client;

import io.netty.buffer.ByteBuf;
import net.warpgame.engine.core.context.service.Service;
import net.warpgame.engine.net.PacketType;
import net.warpgame.engine.net.event.AddressedEnvelope;
import net.warpgame.engine.net.event.Envelope;
import net.warpgame.engine.net.event.sender.EventSerializer;
import net.warpgame.engine.net.event.sender.RemoteEventQueue;

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

    @Override
    public synchronized void pushEvent(Envelope event) {
        events.add(event);
    }

    @Override
    public void update() {
        sendNewEvents();
        resendEvents();
    }

    private synchronized void resendEvents() {
        long currentTime = System.currentTimeMillis();
        while (!resendQueue.isEmpty() && (currentTime - resendQueue.peek().getSendTime() > EVENT_RESEND_INTERVAL
                || resendQueue.peek().isConfirmed())) {
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

    private synchronized void sendNewEvents() {
        while (!events.isEmpty()) {
            Envelope envelope = events.pop();
            eventDependencyIdCounter++;
            AddressedEnvelope addressedEnvelope = new AddressedEnvelope();
            addressedEnvelope.setConfirmed(false);
            addressedEnvelope.setDependencyId(eventDependencyIdCounter);
            addressedEnvelope.setSerializedEventData(eventSerializer.serialize(envelope));
            addressedEnvelope.setEventType(envelope.getContent().getType());
            addressedEnvelope.setTargetComponent(envelope.getTargetComponent());
            addressedEnvelope.setShouldConfirm(envelope.isShouldConfirm());

            confirmationMap.put(eventDependencyIdCounter, addressedEnvelope);
            resendQueue.add(addressedEnvelope);
            sendEvent(addressedEnvelope);
        }
    }

    private void sendEvent(AddressedEnvelope envelope) {
        ByteBuf packet = connectionService.getHeader(PacketType.PACKET_EVENT, envelope.getSerializedEventData().length + 12);
        packet.writeInt(envelope.getEventType());
        packet.writeInt(envelope.getDependencyId());
        packet.writeInt(envelope.getTargetComponent().getId());
        packet.writeBytes(envelope.getSerializedEventData());
        connectionService.sendPacket(packet);
    }

    public synchronized void confirmEvent(int eventDependencyId) {
        AddressedEnvelope addressedEnvelope = confirmationMap.get(eventDependencyId);
        if (addressedEnvelope != null) {
            if(addressedEnvelope.isShouldConfirm()){
                addressedEnvelope.getTargetComponent().triggerEvent(addressedEnvelope.getBouncerEvent());
            }
            addressedEnvelope.setConfirmed(true);
        }
    }

    public void setConnectionService(ConnectionService connectionService) {
        this.connectionService = connectionService;
    }
}