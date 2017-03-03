package pl.warp.ide.controller.look;

import pl.warp.engine.core.scene.Component;
import pl.warp.engine.core.scene.NameProperty;

import java.util.function.Function;

/**
 * @author Jaca777
 *         Created 2017-01-21 at 17
 */
public class DefaultNameSupplier implements Function<Component, String> {

    private String defaultName;

    public DefaultNameSupplier(String defaultName) {
        this.defaultName = defaultName;
    }

    @Override
    public String apply(Component component) {
        if (component.hasProperty(NameProperty.NAME_PROPERTY_NAME))
            return getName(component);
        else return defaultName;
    }

    private String getName(Component component) {
        NameProperty property = component.getProperty(NameProperty.NAME_PROPERTY_NAME);
        return property.getComponentName();
    }
}
