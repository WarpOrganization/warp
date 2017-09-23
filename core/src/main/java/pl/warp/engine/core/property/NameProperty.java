package pl.warp.engine.core.property;

import pl.warp.engine.core.component.Component;

/**
 * @author Jaca777
 *         Created 2017-01-21 at 17
 */
public class NameProperty extends Property {
    public static final String NAME_PROPERTY_NAME = "value";

    private String componentName;

    public NameProperty(String componentName) {
        super(NAME_PROPERTY_NAME);
        this.componentName = componentName;
    }

    public String getComponentName() {
        return componentName;
    }
}
