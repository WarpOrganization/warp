package pl.warp.engine.graphics.postprocessing.lens;

/**
 * @author Jaca777
 *         Created 2016-07-25 at 12
 */
public class SingleFlare {
    private float offset;
    private int textureIndex;
    private float scale;

    public SingleFlare(float offset, int textureIndex, float scale) {
        this.offset = offset;
        this.textureIndex = textureIndex;
        this.scale = scale;
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
}
