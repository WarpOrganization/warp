package pl.warp.enigne.client;

import pl.warp.engine.core.context.service.Service;
import pl.warp.net.EventSerializer;
import pl.warp.net.RemoteEvent;
import pl.warp.net.RemoteEventQueue;

import java.util.ArrayDeque;
import java.util.HashMap;

/**
 * @author Hubertus
 * Created 19.12.2017
 */
@Service
public class ClientRemoteEventQueue implements RemoteEventQueue {

    private static final int EVENT_RESEND_INTERVAL = 600;

    private ArrayDeque<RemoteEvent> events = new ArrayDeque<>();
    private ArrayDeque<ClientEventWrapper> resendQueue = new ArrayDeque<>();
    private HashMap<Integer, ClientEventWrapper> confirmationMap = new HashMap<>();

    private EventSerializer eventSerializer = new EventSerializer();
    private ConnectionService connectionService;

    private int eventDependencyIdCounter = 0;

    public ClientRemoteEventQueue(ConnectionService connectionService) {
        this.connectionService = connectionService;
    }

    @Override
    public synchronized void pushEvent(RemoteEvent event) {
        events.push(event);
    }

    @Override
    public void update() {
        sendNewEvents();
        resendEvents();
    }

    private void resendEvents() {
        long currentTime = System.currentTimeMillis();
        while (currentTime - resendQueue.peek().getSendTime() > EVENT_RESEND_INTERVAL
                || resendQueue.peek().isConfirmed()) {
            ClientEventWrapper eventWrapper = resendQueue.poll();
            if (eventWrapper.isConfirmed()) {
                confirmationMap.remove(eventWrapper.getDependencyId());
            } else {
                eventWrapper.setSendTime(currentTime);
                resendQueue.push(eventWrapper);
                sendEvent(eventWrapper);
            }
        }
    }

    private void sendNewEvents() {
        while (!events.isEmpty()) {
            RemoteEvent remoteEvent = events.pop();
            eventDependencyIdCounter++;
            ClientEventWrapper eventWrapper = new ClientEventWrapper(
                    eventSerializer.serialize(remoteEvent),
                    remoteEvent.getTargetComponentId(),
                    eventDependencyIdCounter);
            confirmationMap.put(eventDependencyIdCounter, eventWrapper);
            resendQueue.push(eventWrapper);
            sendEvent(eventWrapper);
        }
    }

    private void sendEvent(ClientEventWrapper eventWrapper) {

    }

    public synchronized void confirmEvent(int eventDependencyId) {
        ClientEventWrapper eventWrapper = confirmationMap.get(eventDependencyId);
        if (eventWrapper != null) eventWrapper.confirm();
    }
}
