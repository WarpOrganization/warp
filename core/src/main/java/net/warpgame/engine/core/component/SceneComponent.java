package net.warpgame.engine.core.component;


import net.warpgame.engine.core.context.EngineContext;
import net.warpgame.engine.core.event.Event;
import net.warpgame.engine.core.event.Listener;
import net.warpgame.engine.core.property.Property;
import net.warpgame.engine.core.property.PropertyNotPresentException;
import net.warpgame.engine.core.script.Script;

import java.util.*;
import java.util.function.Consumer;

/**
 * @author Jaca777
 * Created 2016-06-27 at 12
 */
public class SceneComponent implements Component {

    private Component parent;
    private EngineContext context;
    private final Map<String, Property> properties = new HashMap<>();
    private final List<Component> children = new LinkedList<>();
    //not using Guava Multimap in order to avoid creating not needed wrappers
    //Anyway, it's still not the best choice
    private final Map<Integer, Set<Listener<?>>> listeners = new HashMap<>();
    private boolean alive = true;
    private int id;

    public SceneComponent(Component parent) {
        this.parent = parent;
        parent.addChild(this);
        this.context = parent.getContext();
        id = context.getComponentRegistry().addComponent(this);
    }


    public SceneComponent(EngineContext context) {
        this.context = context;
        id = context.getComponentRegistry().addComponent(this);
    }

    public SceneComponent(Component parent, int id) {
        this.parent = parent;
        parent.addChild(this);
        this.context = parent.getContext();
        context.getComponentRegistry().addComponent(this, id);
        this.id = id;
    }

    public SceneComponent(EngineContext context, int id) {
        this.context = context;
        context.getComponentRegistry().addComponent(this, id);
        this.id = id;
    }

    /**
     * @throws PropertyNotPresentException
     */
    @Override
    public <T extends Property> T getProperty(String typeId) {
        if (!hasProperty(typeId))
            throw new PropertyNotPresentException(typeId);
        else return (T) properties.get(typeId);
    }


    @Override
    public boolean hasProperty(String id) {
        return properties.containsKey(id);
    }

    @Override
    public <T extends Property> T getPropertyOrNull(String id) {
        return (T) properties.get(id);
    }

    @Override
    public boolean hasEnabledProperty(String id) {
        return hasProperty(id) && getProperty(id).isEnabled();
    }

    /**
     * Triggers event on this component. No other components are affected.
     */
    @Override
    public <T extends Event> void triggerEvent(T event) {
        if (alive) getContext().getEventDispatcher().dispatchEvent(this, event);
    }

    @Override
    public  Set<Listener<?>> getListeners(int eventType) {
        return listeners.getOrDefault(eventType, Collections.emptySet());
    }

    /**
     * Triggers event on each children.
     */
    @Override
    public <T extends Event> void triggerOnChildren(T event) {
        if (alive) forEachChildren(child -> {
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
     *
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
        if (previousParent != null) previousParent.removeChild(this);
        if (parent != null) parent.addChild(this);
    }

    @Override
    public int getChildrenNumber() {
        return children.size();
    }

    @Override
    public void addChild(Component child) {
        if (child.hasParent() && child.getParent() != this)
            throw new IllegalStateException("Unable to add a child. The component already has a parent.");
        else synchronized (children) {
            children.add(child);
            if (child.getParent() != this) child.setParent(this);
        }
    }

    @Override
    public void removeChild(Component child) {
        synchronized (children) {
            if (children.contains(child)) {
                if (child.getParent() == this) child.setParent(null);
                children.remove(child);
            } else throw new ChildNotPresentException("Unable to remove a child.");
        }
    }

    @Override
    public synchronized void destroy() {
        if (alive) {
            context.getComponentRegistry().removeComponent(id);
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
    public void addListener(Listener<?> listener) {
        synchronized (listeners) {
            if(!this.listeners.containsKey(listener.getEventType()))
                this.listeners.put(listener.getEventType(), new HashSet<>());
            this.listeners.get(listener.getEventType())
                    .add(listener);
        }
    }

    @Override
    public void removeListener(Listener<?> listener) {
        synchronized (listeners) {
            if(!this.listeners.containsKey(listener.getEventType()))
                throw new NoSuchElementException("Unable to remove nonexistent listener");
            this.listeners.get(listener.getEventType()).remove(listener);
        }
    }

    @Override
    public void addScript(Class<? extends Script> script) {
        context.getScriptManager().addScript(this, script);
    }

    @Override
    public int getId() {
        return id;
    }
}
