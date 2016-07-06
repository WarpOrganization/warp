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
    private Map<String, Property> properties = new TreeMap<>();
    private Set<Listener> listeners = new HashSet<>();
    private Set<String> tags = new TreeSet<>();
    private List<Component> children = new ArrayList<>();

    public Component(Component parent) {
        this.parent = parent;
        parent.addChild(this);
        this.context = parent.getContext();
    }

    public Component(EngineContext context) {
        this.context = context;
    }

    /**
     * Finds a property of a given type. It's not as fast as {@link #getProperty(String)}.
     *
     * @throws PropertyNotPresentException
     */
    public <T extends Property> T getProperty(Class<T> c) {
        Optional<Property> optionalProperty = properties.entrySet().stream()
                .filter(entry -> entry.getValue().getClass().equals(c))
                .findAny().map(Map.Entry::getValue);
        if (!optionalProperty.isPresent())
            throw new PropertyNotPresentException(c);
        else return (T) optionalProperty.get();
    }

    /**
     * Checks whether component has a property of a given type. It's not as fast as {@link #hasProperty(String)}
     */
    public <T extends Property> boolean hasProperty(Class<T> c) {
        return properties.entrySet().stream()
                .filter(entry -> entry.getValue().getClass().equals(c))
                .count() > 0;
    }

    /**
     * @throws PropertyNotPresentException
     */
    public <T extends Property> T getProperty(String name) {
        if (!hasProperty(name)) throw new PropertyNotPresentException(name);
        else return (T) properties.get(name);
    }

    public boolean hasProperty(String name) {
        return properties.containsKey(name);
    }

    public boolean hasEnabledProperty(String name) {
        return hasProperty(name) && getProperty(name).isEnabled();
    }

    /**
     * Triggers event on this component. No other components are affected.
     */
    public <T extends Event> void triggerEvent(T event) {
        for (Listener listener : listeners) {
            if (listener.isInterestedIn(event))
                listener.handle(event);
        }
    }

    /**
     * Triggers event on each children.
     */
    public <T extends Event> void triggerOnChildren(T event) {
        getChildren().forEach(child -> {
            child.triggerEvent(event);
            child.triggerOnChildren(event);
        });
    }

    /**
     * Triggers event on a whole composite.
     */
    public <T extends Event> void broadcastEvent(T event) {
        if (hasParent()) parent.broadcastEvent(event);
        else {
            triggerEvent(event);
            triggerOnChildren(event);
        }
    }

    /**
     * Triggers event on a a composite root.
     */
    public <T extends Event> void triggerOnRoot(T event) {
        if (hasParent()) parent.triggerOnRoot(event);
        else triggerEvent(event);
    }

    /**
     * @throws IllegalStateException if parent is not present
     */
    public Component getParent() {
        if (parent == null) throw new IllegalStateException("This component has no parent");
        return parent;
    }

    public boolean hasParent() {
        return parent != null;
    }

    public Set<Component> getChildrenWithTag(String tag) {
        Stream<Component> childrenOfChildrenWithTag = getChildren().stream()
                .flatMap(p -> p.getChildrenWithTag(tag).stream());
        Stream<Component> childrenWithTag = getChildren().stream()
                .filter(c -> c.hasTag(tag));
        Stream<Component> allChildrenWithTag = Stream.concat(
                childrenOfChildrenWithTag,
                childrenWithTag);
        return allChildrenWithTag.collect(Collectors.toSet());
    }

    /**
     * Returns children's components of type T. Children of children are traversed as well (and so on).
     * It's a bit slower than {@link #getChildrenProperties(String)}.
     */
    //Considered redundant
    public <T extends Property> Set<T> getChildrenProperties(Class<T> propertyClass) {
        Stream<T> childrenOfChildrenProperties = getChildren().stream()
                .flatMap(p -> p.getChildrenProperties(propertyClass).stream());
        Stream<T> childrenProperties = getChildren().stream()
                .filter(c -> c.hasProperty(propertyClass))
                .map(c -> c.getProperty(propertyClass));
        Stream<T> allChildrenProperties = Stream.concat(
                childrenOfChildrenProperties,
                childrenProperties);
        return allChildrenProperties.collect(Collectors.toSet());
    }

    //Considered redundant
    public <T extends Property> Set<T> getChildrenProperties(String propertyName) {
        Stream<T> childrenOfChildrenProperties = getChildren().stream()
                .flatMap(p -> p.<T>getChildrenProperties(propertyName).stream());
        Stream<T> childrenProperties = getChildren().stream()
                .filter(c -> c.hasProperty(propertyName))
                .map(c -> c.getProperty(propertyName));
        Stream<T> allChildrenProperties = Stream.concat(
                childrenOfChildrenProperties,
                childrenProperties);
        return allChildrenProperties.collect(Collectors.toSet());
    }

    protected void addChild(Component child) {
        children.add(child);
    }

    protected void removeChild(Component child) {
        if (children.contains(child))
            children.remove(child);
        else throw new ChildNotPresentException("Unable to remove a child.");
    }

    public List<Component> getChildren() {
        return children;
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

    /**
     * Methods used by factory methods of properties and listeners.
     * Makes it possible to introduce type safety.
     */

    void addProperty(Property<?> property) {
        this.properties.put(property.getName(), property);
    }

    void removeProperty(Property<?> property) {
        this.properties.remove(property.getName());
    }

    void addListener(Listener<?, ?> listener) {
        this.listeners.add(listener);
    }

    void removeListener(Listener<?, ?> listener) {
        this.listeners.remove(listener);
    }

}
