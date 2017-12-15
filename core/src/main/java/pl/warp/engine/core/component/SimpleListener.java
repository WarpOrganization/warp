package pl.warp.engine.core.component;

import pl.warp.engine.core.event.Event;
import pl.warp.engine.core.event.Listener;

import java.util.function.Consumer;

/**
 * @author Jaca777
 *         Created 2016-06-30 at 14
 */
public class SimpleListener<U extends Event> extends Listener<U> {

    private Consumer<U> handler;

    protected SimpleListener(Component owner, String eventTypeName, Consumer<U> handler) {
        super(owner, eventTypeName);
        this.handler = handler;
    }

    @Override
    public void handle(U event) {
        this.handler.accept(event);
    }

    public static <T extends Event> SimpleListener createListener(Component owner, String eventTypeName, Consumer<T> handler) {
        SimpleListener simpleListener = new SimpleListener(owner, eventTypeName, handler);
        owner.addListener(simpleListener);
        return simpleListener;
    }
}
