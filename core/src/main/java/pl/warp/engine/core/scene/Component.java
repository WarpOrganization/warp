package pl.warp.engine.core.scene;

import pl.warp.engine.core.EngineContext;

import java.util.*;

/**
 * @author Jaca777
 *         Created 2016-06-27 at 12
 */
public abstract class Component {

    private Parent parent;
    private EngineContext context;
    private Map<String, Property> properties = new HashMap<>();
    private Set<Listener> listeners = new HashSet<>();
    private Set<String> tags = new TreeSet<>();

    public Component(Parent parent) {
        this.parent = parent;
        this.context = parent.getContext();
    }

    public Component(EngineContext context) {
        this.parent = null;
        this.context = context;
    }

    /**
     * Works only with properties with default name.
     */
    public <T extends Property> T getProperty(Class<T> c) {
        return (T) properties.get(c.getName());
    }

    public <T extends Property> boolean hasProperty(Class<T> c) {
        return properties.containsKey(c.getName());
    }


    public <T extends Property> T getProperty(String name) {
        return (T) properties.get(name);
    }

    public boolean hasProperty(String name) {
        return properties.containsKey(name);
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
        this.properties.put(property.getName(), property);
    }

    void addListener(Listener<?, ?> listener) {
        this.listeners.add(listener);
    }
}
