package pl.warp.engine.server;

import pl.warp.engine.core.context.service.Service;
import pl.warp.net.EventSerializer;
import pl.warp.net.RemoteEvent;
import pl.warp.net.RemoteEventQueue;

import java.util.ArrayDeque;

/**
 * @author Hubertus
 * Created 18.12.2017
 */
@Service
public class ServerRemoteEventQueue implements RemoteEventQueue {

    private static final int EVENT_RESEND_INTERVAL = 600;

    private ArrayDeque<RemoteEvent> events = new ArrayDeque<>();
    private ArrayDeque<ServerEventWrapper> resendQueue = new ArrayDeque<>();
    private ClientRegistry clientRegistry;

    private EventSerializer eventSerializer = new EventSerializer();

    public ServerRemoteEventQueue(ClientRegistry clientRegistry) {
        this.clientRegistry = clientRegistry;
    }


    @Override
    public synchronized void pushEvent(RemoteEvent event) {
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
            ServerEventWrapper eventWrapper = resendQueue.poll();
            if (eventWrapper.isConfirmed()) {
                eventWrapper.getTargetClient().removeEvent(eventWrapper.getDependencyId());
            } else {
                //TODO compose proper packet
//                clientRegistry.send(eventWrapper.getTargetClient().getId(), eventWrapper.getEvent());
            }
        }
    }

    private void sendNewEvents() {
        while (!events.isEmpty()){
            RemoteEvent remoteEvent = events.poll();
            ServerEventWrapper eventWrapper = new ServerEventWrapper();
        }
    }
}
