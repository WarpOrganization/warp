package pl.warp.engine.core.event;

import pl.warp.engine.core.component.Component;

/**
 * @author Jaca777
 *         Created 2017-02-02 at 13
 */
public interface EventDispatcher {
    void dispatchEvent(Component component, Event event);
}
