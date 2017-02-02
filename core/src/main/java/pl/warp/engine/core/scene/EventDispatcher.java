package pl.warp.engine.core.scene;

/**
 * @author Jaca777
 *         Created 2017-02-02 at 13
 */
public interface EventDispatcher {
    void dispatchEvent(Component component, Event event);
}
