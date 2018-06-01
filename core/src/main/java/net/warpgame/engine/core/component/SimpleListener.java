package net.warpgame.engine.core.component;

import net.warpgame.engine.core.event.Event;
import net.warpgame.engine.core.event.Listener;

import java.util.function.Consumer;

/**
 * @author Jaca777
 * Created 2016-06-30 at 14
 */
public class SimpleListener<U extends Event> extends Listener<U> {

    private Consumer<U> handler;

    protected SimpleListener(Component owner, int eventType, Consumer<U> handler) {
        super(owner, eventType);
        this.handler = handler;
    }

    @Override
    public void handle(U event) {
        this.handler.accept(event);
    }

    public static <T extends Event> SimpleListener createListener(Component owner, int eventType, Consumer<T> handler) {
        SimpleListener simpleListener = new SimpleListener(owner, eventType, handler);
        owner.addListener(simpleListener);
        return simpleListener;
    }
}
