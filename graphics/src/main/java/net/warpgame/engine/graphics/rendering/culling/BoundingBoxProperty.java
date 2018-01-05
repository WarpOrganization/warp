package net.warpgame.engine.graphics.rendering.culling;

import net.warpgame.engine.core.property.Property;

/**
 * @author Jaca777
 * Created 2017-12-28 at 19
 */
public class BoundingBoxProperty extends Property {
    public static final String NAME = "boundingBoxProperty";

    private BoundingBox boundingBox;

    public BoundingBoxProperty(BoundingBox boundingBox) {
        super(NAME);
        this.boundingBox = boundingBox;
    }

    public BoundingBox getBoundingBox() {
        return boundingBox;
    }
}
