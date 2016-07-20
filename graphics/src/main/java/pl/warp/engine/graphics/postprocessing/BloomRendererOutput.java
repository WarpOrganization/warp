package pl.warp.engine.graphics.postprocessing;

import pl.warp.engine.graphics.texture.Texture2D;

/**
 * @author Jaca777
 *         Created 2016-07-20 at 12
 */
public class BloomRendererOutput {
    private Texture2D scene;

    public BloomRendererOutput(Texture2D scene) {
        this.scene = scene;
    }

    public Texture2D getScene() {
        return scene;
    }
}
