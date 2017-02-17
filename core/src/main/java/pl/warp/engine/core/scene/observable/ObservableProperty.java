package pl.warp.engine.core.scene.observable;

import pl.warp.engine.core.scene.Component;
import pl.warp.engine.core.scene.Property;

import java.util.Arrays;

/**
 * @author Jaca777
 *         Created 2016-12-26 at 20
 */
public class ObservableProperty<T extends Component> extends Property<T> {
    public ObservableProperty(String name, ObservableValue... values) {
        super(name);
        Arrays.asList(values).forEach(c -> c.registerObserver(s -> stateChanged((ObservableValue<?>) s)));
    }

    @Override
    public void setOwner(T owner) {
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
