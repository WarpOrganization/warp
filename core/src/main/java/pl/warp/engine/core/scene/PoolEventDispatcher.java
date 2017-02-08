package pl.warp.engine.core.scene;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author Jaca777
 *         Created 2017-02-02 at 13
 */
public class PoolEventDispatcher implements EventDispatcher {

    public static final int THREADS = Runtime.getRuntime().availableProcessors() * 4;
    private ExecutorService executor = Executors.newFixedThreadPool(THREADS);

    @Override
    public void dispatchEvent(Component component, Event event) {
        executor.execute(() -> {
            for (Listener listener : component.getListeners())
                if (listener.isInterestedIn(event)) listener.handle(event);
        });
    }
}
