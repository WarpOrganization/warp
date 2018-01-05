package net.warpgame.engine.core.property.observable;

import net.warpgame.engine.core.event.Event;

/**
 * @author Jaca777
 *         Created 2016-12-26 at 20
 */
public class ObservablePropertyChangedEvent<T> extends Event {

    public static final String OBSERVABLE_PROPERTY_CHANGED_EVENT_NAME = "ObservablePropertyChangedEvent";

    private ObservableProperty property;
    private ObservableValue<T> changedValue;

    public ObservablePropertyChangedEvent(ObservableProperty property, ObservableValue<T> changedValue) {
        super(OBSERVABLE_PROPERTY_CHANGED_EVENT_NAME);
        this.property = property;
        this.changedValue = changedValue;
    }

    public ObservableProperty getProperty() {
        return property;
    }

    public ObservableValue<T> getChangedValue() {
        return changedValue;
    }
}
