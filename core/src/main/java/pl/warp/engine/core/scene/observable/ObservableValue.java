package pl.warp.engine.core.scene.observable;

import java.util.HashSet;
import java.util.Set;
import java.util.function.Consumer;

/**
 * @author Jaca777
 *         Created 2016-12-26 at 20
 */
public class ObservableValue<T> {
    private Set<Consumer<ObservableValue<T>>> stateObservers = new HashSet<>();

    private T value;

    public ObservableValue(T value) {
        this.value = value;
    }

    public void setValue(T value) {
        this.value = value;
        stateObservers.forEach(o -> o.accept(this));
    }

    public T getValue() {
        return value;
    }


    public void registerObserver(Consumer<ObservableValue<T>> observer) {
        stateObservers.add(observer);
    }

    public void unregisterObserver(Consumer<ObservableValue<T>> observer) {
        stateObservers.remove(observer);
    }

    public static <T> ObservableValue<T> of(T initialPosition) {
        return new ObservableValue<>(initialPosition);
    }
}
