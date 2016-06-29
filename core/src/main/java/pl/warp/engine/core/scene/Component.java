package pl.warp.engine.core.scene;

import pl.warp.engine.core.EngineContext;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author Jaca777
 *         Created 2016-06-27 at 12
 */
public abstract class Component {

    private Component parent;
    private EngineContext context;
    private Map<String, Property> properties = new HashMap<>();
    private Set<Listener> listeners = new HashSet<>();
    private Set<String> tags = new TreeSet<>();

    public Component(Component parent) {
        this.parent = parent;
        this.context = parent.getContext();
    }

    public Component(EngineContext context) {
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

    public <T extends Event> void broadcastEvent(T event) {
        if (hasParent()) parent.broadcastEvent(event);
        else {
            triggerEvent(event);
            broadcastEventToChildren(event);
        }
    }

    public <T extends Event> void broadcastEventToChildren(T event) {
        if (hasChildren())
            getChildren().forEach(child -> {
                child.triggerEvent(event);
                child.broadcastEventToChildren(event);
            });
    }

    public Component getParent() {
        if (parent == null) throw new IllegalStateException("This component has no parent");
        return parent;
    }

    public boolean hasParent() {
        return parent != null;
    }

    public Set<Component> getChildrenWithTag(String tag) {
        Stream<Component> childrenOfChildrenWithTag = getChildren().stream()
                .filter(Component::hasChildren)
                .flatMap(p -> p.getChildrenWithTag(tag).stream());
        Stream<Component> childrenWithTag = getChildren().stream()
                .filter(c -> c.hasTag(tag));
        Stream<Component> allChildrenWithTag = Stream.concat(
                childrenOfChildrenWithTag,
                childrenWithTag);
        return allChildrenWithTag.collect(Collectors.toSet());
    }

    /**
     * Works only with properties with default name.
     */
    public <T extends Property> Set<T> getChildrenProperties(Class<T> propertyClass) {
        return getChildrenProperties(propertyClass.getName());
    }

    public <T extends Property> Set<T> getChildrenProperties(String propertyName) {
        Stream<T> childrenOfChildrenProperties = getChildren().stream()
                .filter(Component::hasChildren)
                .flatMap(p -> p.<T>getChildrenProperties(propertyName).stream());
        Stream<T> childrenProperties = getChildren().stream()
                .filter(c -> c.hasProperty(propertyName))
                .map(c -> c.getProperty(propertyName));
        Stream<T> allChildrenProperties = Stream.concat(
                childrenOfChildrenProperties,
                childrenProperties);
        return allChildrenProperties.collect(Collectors.toSet());
    }

    public abstract List<Component> getChildren();

    public abstract boolean hasChildren();

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
