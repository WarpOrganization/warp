package net.warpgame.engine.graphics.rendering.screenspace.light;

import net.warpgame.engine.core.property.Property;

/**
 * @author Jaca777
 * Created 2017-11-18 at 21
 */
public class LightSourceProperty extends Property {

    private LightSource lightSource;

    public LightSourceProperty(LightSource lightSource) {
        this.lightSource = lightSource;
    }

    public LightSource getLightSource() {
        return lightSource;
    }
}
