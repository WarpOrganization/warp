package net.warpgame.engine.graphics.rendering.screenspace.light;

import net.warpgame.engine.core.property.Property;

/**
 * @author Jaca777
 * Created 2017-11-18 at 21
 */
public class LightSourceProperty extends Property {
    public static final String NAME = "lightSource";
    private LightSource lightSource;

    public LightSourceProperty(LightSource lightSource) {
        super(NAME);
        this.lightSource = lightSource;
    }


    public LightSource getLightSource() {
        return lightSource;
    }
}
