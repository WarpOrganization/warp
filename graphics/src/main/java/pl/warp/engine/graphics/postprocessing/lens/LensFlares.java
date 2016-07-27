package pl.warp.engine.graphics.postprocessing.lens;

import pl.warp.engine.graphics.texture.Texture2DArray;

/**
 * @author Jaca777
 *         Created 2016-07-25 at 12
 */
public class LensFlares {
    private Texture2DArray lensTextures;
    private Flare[] flares;

    public LensFlares(Texture2DArray lensTextures, Flare[] flares) {
        this.lensTextures = lensTextures;
        this.flares = flares;
    }

    public Texture2DArray getLensTextures() {
        return lensTextures;
    }

    public Flare[] getFlares() {
        return flares;
    }
}
