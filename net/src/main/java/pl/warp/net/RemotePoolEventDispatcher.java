package pl.warp.net;

import pl.warp.engine.core.component.Component;
import pl.warp.engine.core.event.Event;
import pl.warp.engine.core.event.EventDispatcher;
import pl.warp.engine.core.event.Listener;
import pl.warp.engine.core.script.annotation.ContextService;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author Hubertus
 * Created 18.12.2017
 */
public class RemotePoolEventDispatcher implements EventDispatcher {
    private static final int THREADS = Runtime.getRuntime().availableProcessors() * 4;
    private ExecutorService executor = Executors.newFixedThreadPool(THREADS);

    @ContextService
    private RemoteEventQueue eventQueue;

    @Override
    public void dispatchEvent(Component component, Event event) {
        if (event instanceof Envelope) {
            Envelope envelope = (Envelope) event;
//            remoteEvent.setTargetComponentId(component.getId());
//            eventQueue.pushEvent(remoteEvent);
            envelope.setTargetComponentId(component.getId());
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
