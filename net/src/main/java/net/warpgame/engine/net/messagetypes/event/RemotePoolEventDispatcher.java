package net.warpgame.engine.net.messagetypes.event;

import net.warpgame.engine.core.component.Component;
import net.warpgame.engine.core.context.service.Service;
import net.warpgame.engine.core.event.Event;
import net.warpgame.engine.core.event.EventDispatcher;
import net.warpgame.engine.core.event.Listener;
import net.warpgame.engine.net.message.EventMessageSource;

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

    private EventMessageSource eventMessageSource;

    public RemotePoolEventDispatcher(EventMessageSource eventMessageSource) {
        this.eventMessageSource = eventMessageSource;
    }

    @Override
    public void dispatchEvent(Component component, Event event) {
        if (event instanceof NetworkEvent) {
            NetworkEvent networkEvent = (NetworkEvent) event;
            if (!networkEvent.isTransfered())
                eventMessageSource.pushMessage(new EventEnvelope(networkEvent, component));
        }

        executor.execute(() -> {
            for (Listener listener : component.getListeners(event.getType()))
                listener.handle(event);
        });
    }
}
