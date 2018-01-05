package net.warpgame.engine.graphics.rendering.culling;

import net.warpgame.engine.core.property.Property;

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
