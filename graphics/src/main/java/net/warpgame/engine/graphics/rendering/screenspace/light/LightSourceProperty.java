package net.warpgame.engine.graphics.rendering.screenspace.light;

import net.warpgame.engine.core.property.Property;
import org.joml.Vector3f;
import org.joml.Vector3fc;

/**
 * @author Jaca777
 * Created 2017-11-18 at 21
 */
public class LightSourceProperty extends Property {

    private Vector3f color;

    public LightSourceProperty(Vector3fc color) {
        this.color = new Vector3f(color);
    }

    @Override
    public void init() {
        SceneLightManager sceneLightManager = getOwner().getContext()
                .getLoadedContext()
                .findOne(SceneLightManager.class)
                .get();
        sceneLightManager.addLight(this);
    }

    public Vector3fc getColor() {
        return color;
    }

    public void setColor(Vector3fc color) {
        this.color.set(color);
    }
}
