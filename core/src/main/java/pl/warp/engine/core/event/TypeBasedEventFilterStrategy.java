package pl.warp.engine.core.event;

/**
 * @author Jaca777
 *         Created 2016-06-30 at 14
 */
public class TypeBasedEventFilterStrategy <T extends Event> implements EventFilterStrategy{

    private Class<T> eventClass;

    public TypeBasedEventFilterStrategy(Class<T> eventClass) {
        this.eventClass = eventClass;
    }

    @Override
    public boolean apply(Event event) {
        return eventClass.isAssignableFrom(event.getClass());
    }
}
