package net.warpgame.engine.graphics.rendering.screenspace.cubemap;

import net.warpgame.engine.core.property.Property;
import net.warpgame.engine.graphics.texture.Cubemap;

/**
 * @author Jaca777
 * Created 2017-11-27 at 18
 */
public class CubemapProperty extends Property {

    private Cubemap cubemap;

    public CubemapProperty(Cubemap cubemap) {
        this.cubemap = cubemap;
    }

    public Cubemap getCubemap() {
        return cubemap;
    }

    public void setCubemap(Cubemap cubemap) {
        this.cubemap = cubemap;
    }
}
