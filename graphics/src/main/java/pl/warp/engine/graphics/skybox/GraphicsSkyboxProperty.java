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

    public GraphicsSkyboxProperty(Cubemap cubemap) {
        super(CUBEMAP_PROPERTY_NAME);
        this.cubemap = cubemap;
    }

    public Cubemap getCubemap() {
        return cubemap;
    }
}
