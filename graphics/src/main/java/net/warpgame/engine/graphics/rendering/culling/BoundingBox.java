package net.warpgame.engine.graphics.rendering.culling;

import org.joml.Vector3f;

/**
 * @author Jaca777
 * Created 2017-10-28 at 01
 */
public class BoundingBox {

    private Vector3f min, max;

    public BoundingBox(Vector3f max, Vector3f min) {
        this.min = min;
        this.max = max;
    }

    public Vector3f min() {
        return min;
    }

    public void setMin(Vector3f min) {
        this.min = min;
    }

    public Vector3f max() {
        return max;
    }

    public void setMax(Vector3f max) {
        this.max = max;
    }
}
