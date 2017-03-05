package pl.warp.engine.graphics.skybox;

import pl.warp.engine.core.scene.Property;
import pl.warp.engine.core.scene.Scene;
import pl.warp.engine.graphics.texture.Cubemap;

/**
 * @author Jaca777
 *         Created 2016-07-08 at 19
 */
public class GraphicsSkyboxProperty extends Property<Scene> {

    public static final String CUBEMAP_PROPERTY_NAME = "cubemap";

    private Cubemap cubemap;
    private float brightness;

    public GraphicsSkyboxProperty(Cubemap cubemap, float brightness) {
        super(CUBEMAP_PROPERTY_NAME);
        this.cubemap = cubemap;
        this.brightness = brightness;
    }

    public Cubemap getCubemap() {
        return cubemap;
    }

    public float getBrightness() {
        return brightness;
    }

    public void setBrightness(float brightness) {
        this.brightness = brightness;
    }
}
