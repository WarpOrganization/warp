package pl.warp.engine.core.scene;

import pl.warp.engine.core.EngineContext;

/**
 * @author Jaca777
 *         Created 2016-06-25 at 18
 */
public interface Component {
    <T extends Property> T getProperty(Class<T> c);
    <T extends Property> boolean hasProperty(Class<T> c);
    void addProperty(Property property);
    void addScript(Script<?> script);
    Component getParent();
    EngineContext getContext();
}
