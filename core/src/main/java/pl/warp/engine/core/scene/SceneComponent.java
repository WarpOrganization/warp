package pl.warp.engine.core.scene;


import pl.warp.engine.core.EngineContext;

import java.util.*;
import java.util.function.Consumer;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author Jaca777
 *         Created 2016-06-27 at 12
 *         TODO delete children streams
 */
public abstract class SceneComponent implements Component {

    private Component parent;
    private EngineContext context;
    private final Map<String, Property> properties = new TreeMap<>();
    private final Set<Listener> listeners = new HashSet<>();
    private final List<Component> children = new LinkedList<>();
    private boolean alive = true;

    public SceneComponent(Component parent) {
        this.parent = parent;
        parent.addChild(this);
        this.context = parent.getContext();
    }


    public SceneComponent(EngineContext context) {
        this.context = context;
    }


    /**
     * Finds a property of a given type. It's not as fast as {@link #getProperty(String)}.
     *
     * @throws PropertyNotPresentException
     */
    @Override
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
    @Override
    public <T extends Property> boolean hasProperty(Class<T> c) {
        return properties.entrySet().stream()
                .filter(entry -> entry.getValue().getClass().equals(c))
                .count() > 0;
    }

    /**
     * @throws PropertyNotPresentException
     */
    @Override
    public <T extends Property> T getProperty(String name) {
        if (!hasProperty(name)) throw new PropertyNotPresentException(name);
        else return (T) properties.get(name);
    }

    @Override
    public boolean hasProperty(String name) {
        return properties.containsKey(name);
    }

    @Override
    public <T extends Property> Optional<T> getPropertyIfExists(String name){
        return hasProperty(name) ? Optional.of(getProperty(name)) : Optional.empty();
    }

    @Override
    public boolean hasEnabledProperty(String name) {
        return hasProperty(name) && getProperty(name).isEnabled();
    }

    @Override
    public Set<Property> getProperties() {
        return properties.entrySet().stream()
                .map(Map.Entry::getValue)
                .collect(Collectors.toSet());
    }

    /**
     * Triggers event on this component. No other components are affected.
     */
    @Override
    public <T extends Event> void triggerEvent(T event) {
        synchronized (listeners) {
            for (Listener listener : listeners) {
                if (listener.isInterestedIn(event))
                    listener.handle(event);
            }
        }
    }

    /**
     * Triggers event on each children.
     */
    @Override
    public <T extends Event> void triggerOnChildren(T event) {
        forEachChildren(child -> {
            child.triggerEvent(event);
            child.triggerOnChildren(event);
        });
    }

    /**
     * Triggers event on a whole composite.
     */
    @Override
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
    @Override
    public <T extends Event> void triggerOnRoot(T event) {
        if (hasParent()) parent.triggerOnRoot(event);
        else triggerEvent(event);
    }

    /**
     * @throws IllegalStateException if parent is not present
     */
    @Override
    public Component getParent() {
        return parent;
    }

    @Override
    public boolean hasParent() {
        return parent != null;
    }

    @Override
    public void setParent(Component parent) {
        Component previousParent = this.parent;
        this.parent = parent;
        if(previousParent != null) previousParent.removeChild(this);
        if(parent != null) parent.addChild(this);
    }

    @Override
    public Component getChild(int index) {
        return children.get(index);
    }

    /**
     * Returns children's components of type T. Children of children are traversed as well (and so on).
     * It's a bit slower than {@link #getChildrenProperties(String)}.
     */
    //Considered redundant
    @Override
    public <T extends Property> Set<T> getChildrenProperties(Class<T> propertyClass) {
        Stream<T> childrenOfChildrenProperties = children.stream()
                .flatMap(p -> p.getChildrenProperties(propertyClass).stream());
        Stream<T> childrenProperties = children.stream()
                .filter(c -> c.hasProperty(propertyClass))
                .map(c -> c.getProperty(propertyClass));
        Stream<T> allChildrenProperties = Stream.concat(
                childrenOfChildrenProperties,
                childrenProperties);
        return allChildrenProperties.collect(Collectors.toSet());
    }

    //Considered redundant
    @Override
    public <T extends Property> Set<T> getChildrenProperties(String propertyName) {
        Stream<T> childrenOfChildrenProperties = children.stream()
                .flatMap(p -> p.<T>getChildrenProperties(propertyName).stream());
        Stream<T> childrenProperties = children.stream()
                .filter(c -> c.hasProperty(propertyName))
                .map(c -> c.getProperty(propertyName));
        Stream<T> allChildrenProperties = Stream.concat(
                childrenOfChildrenProperties,
                childrenProperties);
        return allChildrenProperties.collect(Collectors.toSet());
    }

    @Override
    public void addChild(Component child) {
        if (child.hasParent() && child.getParent() != this)
            throw new IllegalStateException("Unable to add a child. The component already has a parent.");
        else synchronized (children) {
            children.add(child);
            if(child.getParent() != this) child.setParent(this);
        }
    }

    @Override
    public void removeChild(Component child) {
        synchronized (children) {
            if (children.contains(child)) {
                if(child.getParent() == this)  child.setParent(null);
                children.remove(child);
            } else throw new ChildNotPresentException("Unable to remove a child.");
        }
    }

    @Override
    public synchronized void destroy() {
        if (alive) {
            if (hasParent())
                getParent().removeChild(this);
            triggerEvent(new ComponentDeathEvent(this));
            forEachChildren(Component::destroy);
            alive = false;
        } else throw new IllegalStateException("The component has already been destroyed.");

    }

    @Override
    public void forEachChildren(Consumer<Component> f) {
        synchronized (children) {
            children.forEach(f);
        }
    }

    @Override
    public EngineContext getContext() {
        return context;
    }


    @Override
    public void addProperty(Property property) {
        this.properties.put(property.getName(), property);
        property.setOwner(this);
    }

    @Override
    public void removeProperty(Property property) {
        this.properties.remove(property.getName());
    }

    @Override
    public void addListener(Listener<?, ?> listener) {
        synchronized (listeners) {
            this.listeners.add(listener);
        }
    }

    @Override
    public void removeListener(Listener<?, ?> listener) {
        synchronized (listeners) {
            this.listeners.remove(listener);
        }
    }

}
