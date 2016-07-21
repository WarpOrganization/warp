package pl.warp.engine.graphics.postprocessing;

import pl.warp.engine.graphics.texture.Texture2D;

/**
 * @author Jaca777
 *         Created 2016-07-20 at 12
 */
public class BloomRendererOutput {

    private Texture2D scene;
    private Texture2D bloom;

    public BloomRendererOutput(Texture2D scene, Texture2D bloom) {
        this.scene = scene;
        this.bloom = bloom;
    }

    public Texture2D getScene() {
        return scene;
    }

    public void setScene(Texture2D scene) {
        this.scene = scene;
    }

    public Texture2D getBloom() {
        return bloom;
    }

    public void setBloom(Texture2D bloom) {
        this.bloom = bloom;
    }
}
