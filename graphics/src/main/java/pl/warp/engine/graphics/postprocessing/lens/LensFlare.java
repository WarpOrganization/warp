package pl.warp.engine.graphics.postprocessing.lens;

import org.joml.Vector3f;
import pl.warp.engine.graphics.texture.Texture2DArray;

/**
 * @author Jaca777
 *         Created 2016-07-25 at 12
 */
public class LensFlare {
    private Texture2DArray lensTextures;
    private SingleFlare[] flares;

    public LensFlare(Texture2DArray lensTextures, SingleFlare[] flares) {
        this.lensTextures = lensTextures;
        this.flares = flares;
    }

    public Texture2DArray getLensTextures() {
        return lensTextures;
    }

    public SingleFlare[] getFlares() {
        return flares;
    }
}
