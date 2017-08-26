package pl.warp.ide.controller.sceneeditor;

import pl.warp.engine.core.component.Component;
import pl.warp.engine.core.property.Property;

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
