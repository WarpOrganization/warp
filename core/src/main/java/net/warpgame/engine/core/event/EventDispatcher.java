package net.warpgame.engine.core.event;

import net.warpgame.engine.core.component.Component;
import net.warpgame.engine.core.component.Component;

/**
 * @author Jaca777
 *         Created 2017-02-02 at 13
 */
public interface EventDispatcher {
    void dispatchEvent(Component component, Event event);
}
