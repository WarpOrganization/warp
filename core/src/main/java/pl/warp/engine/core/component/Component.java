package pl.warp.engine.core.component;

import pl.warp.engine.core.context.EngineContext;
import pl.warp.engine.core.event.Event;
import pl.warp.engine.core.event.Listener;
import pl.warp.engine.core.property.Property;
import pl.warp.engine.core.script.Script;

import java.util.Set;
import java.util.function.Consumer;

/**
 * @author Jaca777
 *         Created 2017-01-27 at 19
 */
public interface Component {

    <T extends Property> T getProperty(String name);

    boolean hasProperty(String name);

    <T extends Property> T getPropertyOrNull(String name);

    boolean hasEnabledProperty(String name);


    <T extends Event> void triggerEvent(T event);

    Set<Listener<?>> getListeners(String eventName);

    <T extends Event> void triggerOnChildren(T event);

    <T extends Event> void broadcastEvent(T event);

    <T extends Event> void triggerOnRoot(T event);

    Component getParent();

    boolean hasParent();

    int getChildrenNumber();


    void addChild(Component child);

    void destroy();

    void forEachChildren(Consumer<Component> f);

    EngineContext getContext();

    void addProperty(Property property);

    void removeProperty(Property property);

    void addScript(Class<? extends Script> script);

    void setParent(Component parent);

    void removeChild(Component component);

    void addListener(Listener<?> listener);

    void removeListener(Listener<?> listener);

    int getId();
}
