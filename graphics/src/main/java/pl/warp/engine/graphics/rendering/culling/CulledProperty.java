package pl.warp.engine.graphics.rendering.culling;

import pl.warp.engine.core.property.Property;

/**
 * @author Jaca777
 * Created 2017-09-30 at 23
 */
public class CulledProperty extends Property {
    public static final String NAME = "culled";
    private boolean culled;

    public CulledProperty() {
        super(NAME);
    }

    public boolean isCulled() {
        return culled;
    }
}
