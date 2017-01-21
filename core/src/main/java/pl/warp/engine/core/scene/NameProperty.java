package pl.warp.engine.core.scene;

/**
 * @author Jaca777
 *         Created 2017-01-21 at 17
 */
public class NameProperty extends Property<Component> {
    public static final String NAME_PROPERTY_NAME = "propertyName";

    private String componentName;

    public NameProperty(Component owner, String componentName) {
        super(owner, NAME_PROPERTY_NAME);
        this.componentName = componentName;
    }

    public String getComponentName() {
        return componentName;
    }
}
