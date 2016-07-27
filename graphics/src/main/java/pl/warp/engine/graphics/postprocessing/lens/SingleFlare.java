package pl.warp.engine.graphics.postprocessing.lens;

import pl.warp.engine.graphics.texture.Texture2D;

/**
 * @author Jaca777
 *         Created 2016-07-25 at 12
 */
public class SingleFlare {
    private float pos;
    private int textureIndex;
    private float scale;

    public SingleFlare(float pos, int textureIndex, float scale) {
        this.pos = pos;
        this.textureIndex = textureIndex;
        this.scale = scale;
    }

    public float getPos() {
        return pos;
    }

    public int getTextureIndex() {
        return textureIndex;
    }

    public float getScale() {
        return scale;
    }
}
