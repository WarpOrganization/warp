package pl.warp.engine.core.scene;

import pl.warp.engine.core.EngineContext;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * @author Jaca777
 *         Created 2016-06-27 at 12
 */
public abstract class Component {

    private Parent parent;
    private EngineContext context;
    private Map<Class<? extends Property>, Property> properties = new HashMap<>();
    private Set<Listener> listeners = new HashSet<>();
    private Set<String> tags = new HashSet<>();

    public Component(Parent parent) {
        this.parent = parent;
        this.context = parent.getContext();
    }

    public Component(EngineContext context) {
        this.parent = null;
        this.context = context;
    }

    public <T extends Property> T getProperty(Class<T> c) {
        return (T) properties.get(c);
    }

    public <T extends Property> boolean hasProperty(Class<T> c) {
        return properties.containsKey(c);
    }

    public <T extends Event> void triggerEvent(T event) {
        for (Listener listener : listeners) {
            if (listener.isInterestedIn(event))
                listener.handle(event);
        }
    }

    public Component getParent() {
        if (parent == null) throw new IllegalStateException("This component has no parent");
        return parent;
    }

    public boolean hasParent() {
        return parent != null;
    }

    public EngineContext getContext() {
        return context;
    }

    public void addTag(String tag) {
        tags.add(tag);
    }

    public boolean hasTag(String tag) {
        return tags.contains(tag);
    }

    void addProperty(Property<?> property) {
        this.properties.put(property.getClass(), property);
    }

    void addListener(Listener<?, ?> listener) {
        this.listeners.add(listener);
    }
}
