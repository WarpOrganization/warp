package net.warpgame.engine.graphics.rendering.culling;

import net.warpgame.engine.core.property.Property;

/**
 * @author Jaca777
 * Created 2017-12-28 at 19
 */
public class BoundingBoxProperty extends Property {
    private BoundingBox boundingBox;

    public BoundingBoxProperty(BoundingBox boundingBox) {
        this.boundingBox = boundingBox;
    }

    public BoundingBox getBoundingBox() {
        return boundingBox;
    }
}
