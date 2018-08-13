package net.warpgame.engine.core.context.config;

import com.google.common.collect.Multimap;
import com.google.common.collect.Ordering;
import com.google.common.collect.TreeMultimap;
import net.warpgame.engine.core.context.service.Service;

import java.util.Collection;
import java.util.Comparator;
import java.util.function.Consumer;

/**
 * @author Jaca777
 * Created 2017-09-23 at 15
 */

@Service
public class ConfigurationManager {

    private Multimap<String, RegisteredObserver> registeredObservers = TreeMultimap.create(
            Ordering.natural(), Comparator.comparing(RegisteredObserver::hashCode)
    );

    void updateValue(String name, Object value) {
        Collection<RegisteredObserver> observers = registeredObservers.get(name);
        observers.forEach(callHandle(value));
    }

    protected Consumer<RegisteredObserver> callHandle(Object value) {
        return observer -> observer.getExecutor().scheduleOnce(() -> {
            try {
                observer.getHandle().invoke(value);
            } catch (Throwable throwable) {
                throw new ServiceConfigurationException(throwable);
            }

        });
    }

    public void addObserver(String value, RegisteredObserver observer) {
        this.registeredObservers.put(value, observer);
    }


}
