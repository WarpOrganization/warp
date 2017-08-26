package pl.warp.engine.core.event;

/**
 * @author Jaca777
 *         Created 2016-06-30 at 14
 */
interface EventFilterStrategy {
    boolean apply(Event event);
}
