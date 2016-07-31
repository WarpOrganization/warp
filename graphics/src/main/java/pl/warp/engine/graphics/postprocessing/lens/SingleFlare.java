package pl.warp.engine.graphics.postprocessing.lens;

import org.joml.Vector3f;

/**
 * @author Jaca777
 *         Created 2016-07-25 at 12
 */
public class SingleFlare {
    private float offset;
    private int textureIndex;
    private float scale;
    private Vector3f color;

    public SingleFlare(float offset, int textureIndex, float scale, Vector3f color) {
        this.offset = offset;
        this.textureIndex = textureIndex;
        this.scale = scale;
        this.color = color;
    }

    public float getOffset() {
        return offset;
    }

    public int getTextureIndex() {
        return textureIndex;
    }

    public float getScale() {
        return scale;
    }

    public Vector3f getColor() {
        return color;
    }
}
