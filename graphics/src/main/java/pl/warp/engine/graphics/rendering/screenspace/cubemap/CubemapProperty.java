package pl.warp.engine.graphics.rendering.screenspace.cubemap;

import pl.warp.engine.core.property.Property;
import pl.warp.engine.graphics.texture.Cubemap;

/**
 * @author Jaca777
 * Created 2017-11-27 at 18
 */
public class CubemapProperty extends Property {
    public static final String NAME = "cubemapProperty";

    private Cubemap cubemap;

    public CubemapProperty(Cubemap cubemap) {
        super(NAME);
        this.cubemap = cubemap;
    }

    public Cubemap getCubemap() {
        return cubemap;
    }

    public void setCubemap(Cubemap cubemap) {
        this.cubemap = cubemap;
    }
}
