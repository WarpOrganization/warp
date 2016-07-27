package pl.warp.engine.graphics.postprocessing.lens;

import pl.warp.engine.graphics.texture.Texture2D;

/**
 * @author Jaca777
 *         Created 2016-07-25 at 12
 */
public class Flare {
    private float pos;
    private int textureIndex;

    public Flare(float pos, int textureIndex) {
        this.pos = pos;
        this.textureIndex = textureIndex;
    }

    public float getPos() {
        return pos;
    }

    public int getTextureIndex() {
        return textureIndex;
    }
}
