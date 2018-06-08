package net.warpgame.engine.core.property.observable;

import net.warpgame.engine.core.component.Component;
import net.warpgame.engine.core.component.Component;
import net.warpgame.engine.core.property.Property;

import java.util.Arrays;

/**
 * @author Jaca777
 *         Created 2016-12-26 at 20
 */
public class ObservableProperty extends Property {
    public ObservableProperty(ObservableValue... values) {
        Arrays.asList(values).forEach(c -> c.registerObserver(s -> stateChanged((ObservableValue<?>) s)));
    }

    @Override
    public void setOwner(Component owner) {
        if (getOwner() != null) throw new IllegalStateException("Component can't have two owners.");
        else {
            super.setOwner(owner);
            owner.triggerOnRoot(new PropertyAddedEvent<>(this, owner));
        }
    }

    private void stateChanged(ObservableValue<?> changedValue) {
        getOwner().triggerEvent(new ObservablePropertyChangedEvent(this, changedValue));
    }
}
