package pl.warp.engine.core.property;

/**
 * @author Jaca777
 *         Created 2017-01-21 at 17
 */
public class NameProperty extends Property {
    public static final String NAME = "value";

    private String componentName;

    public NameProperty(String componentName) {
        super(NAME);
        this.componentName = componentName;
    }

    public String getComponentName() {
        return componentName;
    }
}
