package pl.warp.engine.core.scene.position;

import pl.warp.engine.core.scene.Component;
import pl.warp.engine.core.scene.Property;

/**
 * @author Jaca777
 *         Created 2016-12-17 at 12
 */
public class AbsolutePositionProperty extends Property<Component> {
    public static final String POSITION_PROPERTY_NAME = "absolutePositionProperty";

    private BigVectorN position;

    public AbsolutePositionProperty(Component owner, BigVectorN position) {
        super(owner, POSITION_PROPERTY_NAME);
        this.position = position;
    }

    public BigVectorN getPosition() {
        return position;
    }
}
