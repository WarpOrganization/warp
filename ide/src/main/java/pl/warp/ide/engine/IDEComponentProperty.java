package pl.warp.ide.engine;

import pl.warp.engine.core.scene.Component;
import pl.warp.engine.core.scene.Property;

/**
 * @author Jaca777
 *         Created 2017-01-26 at 12
 */
public class IDEComponentProperty extends Property<Component> {

    public static final String IDE_COMPONENT_PROPERTY_NAME = "ideComponent";

    public IDEComponentProperty() {
        super(IDE_COMPONENT_PROPERTY_NAME);
    }
}
