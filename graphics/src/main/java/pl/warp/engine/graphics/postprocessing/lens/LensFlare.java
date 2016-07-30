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
    private Vector3f color;

    public LensFlare(Texture2DArray lensTextures, SingleFlare[] flares, Vector3f color) {
        this.lensTextures = lensTextures;
        this.flares = flares;
        this.color = color;
    }

    public LensFlare(Texture2DArray lensTextures, SingleFlare[] flares) {
        this(lensTextures, flares, new Vector3f(1));
    }


    public Vector3f getColor() {
        return color;
    }

    public Texture2DArray getLensTextures() {
        return lensTextures;
    }

    public SingleFlare[] getFlares() {
        return flares;
    }
}
