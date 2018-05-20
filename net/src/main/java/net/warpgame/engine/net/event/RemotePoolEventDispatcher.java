package net.warpgame.engine.net.event;

import net.warpgame.engine.core.component.Component;
import net.warpgame.engine.core.context.service.Service;
import net.warpgame.engine.core.event.Event;
import net.warpgame.engine.core.event.EventDispatcher;
import net.warpgame.engine.core.event.Listener;
import net.warpgame.engine.net.event.sender.RemoteEventQueue;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author Hubertus
 * Created 18.12.2017
 */
@Service
public class RemotePoolEventDispatcher implements EventDispatcher {
    //    private static final int THREADS = Runtime.getRuntime().availableProcessors() * 4;
    private static final int THREADS = 1;
    private ExecutorService executor = Executors.newFixedThreadPool(THREADS);

    private RemoteEventQueue eventQueue;

    public RemotePoolEventDispatcher(RemoteEventQueue eventQueue) {
        this.eventQueue = eventQueue;
    }

    @Override
    public void dispatchEvent(Component component, Event event) {
        if (event instanceof Envelope) {
            Envelope envelope = (Envelope) event;
            envelope.setTargetComponent(component);
            eventQueue.pushEvent(envelope);
            executor.execute(() -> {
                for (Listener listener : component.getListeners(envelope.getContent().getTypeName()))
                    listener.handle(envelope.getContent());
            });
        } else {
            executor.execute(() -> {
                for (Listener listener : component.getListeners(event.getTypeName()))
                    listener.handle(event);
            });
        }

    }
}
