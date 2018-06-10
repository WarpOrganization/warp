package net.warpgame.engine.core.property;

/**
 * @author Jaca777
 *         Created 2017-01-21 at 17
 */
public class NameProperty extends Property {

    private String componentName;

    public NameProperty(String componentName) {
        this.componentName = componentName;
    }

    public String getComponentName() {
        return componentName;
    }
}
