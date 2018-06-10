package net.warpgame.engine.core.property.observable;

import net.warpgame.engine.core.event.Event;

/**
 * @author Jaca777
 *         Created 2016-12-26 at 20
 */
public class ObservablePropertyChangedEvent<T> extends Event {

    private ObservableProperty property;
    private ObservableValue<T> changedValue;

    public ObservablePropertyChangedEvent(ObservableProperty property, ObservableValue<T> changedValue) {
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
