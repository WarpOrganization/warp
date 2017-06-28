package pl.warp.engine.core.scene.framing;

import pl.warp.engine.core.EngineContext;
import pl.warp.engine.core.scene.Component;
import pl.warp.engine.core.scene.Property;
import pl.warp.engine.core.scene.SceneComponent;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author Jaca777
 *         Created 2017-06-24 at 16
 */
public class FramedComponent extends SceneComponent {

    private Map<String, PropertyFrameProxy> proxies = new HashMap<>();

    public FramedComponent(Component parent) {
        super(parent);
    }

    public FramedComponent(EngineContext context) {
        super(context);
    }

    @Override
    public <T extends Property> T getProperty(Class<T> c) {
        return proxies.entrySet().stream()
                .filter(entry -> entry.getValue().getClass().equals(c))
                .findAny().map(Map.Entry::getValue)
                .map(p -> (T) p.access())
                .orElseGet(() -> super.getProperty(c));
    }

    @Override
    public <T extends Property> boolean hasProperty(Class<T> c) {
        Stream<PropertyFrameProxy> proxies = this.proxies.entrySet().stream().map(Map.Entry::getValue);
        return Stream.concat(proxies, getProperties().stream())
                .filter(entry -> entry.getClass().equals(c))
                .count() > 0;
    }

    @Override
    public <T extends Property> T getProperty(String name) {
        if(proxies.containsKey(name))
            return (T) proxies.get(name).access();
        else return super.getProperty(name);
    }

    @Override
    public boolean hasProperty(String name) {
        return proxies.containsKey(name) || super.hasProperty(name);
    }
    @Override
    public Set<Property> getProperties() {
        Stream<Property> proxies = this.proxies.entrySet().stream()
                .map(Map.Entry::getValue)
                .map(PropertyFrameProxy::access);
        Stream<Property> properties = super.getProperties().stream();
        return Stream.concat(proxies, properties).collect(Collectors.toSet());
    }

    @Override
    public void addProperty(Property property) {
        if(property instanceof PropertyFrame)
            makeProxy(property);
        else super.addProperty(property);

    }

    private void makeProxy(Property property) {

    }

    @Override
    public void removeProperty(Property property) {
        if(property instanceof PropertyFrame)
            closeProxy(property);
        else super.removeProperty(property);
    }

    private void closeProxy(Property property) {

    }
}
