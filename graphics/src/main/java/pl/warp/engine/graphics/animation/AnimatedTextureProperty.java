package pl.warp.engine.graphics.animation;

import org.joml.Vector2f;
import pl.warp.engine.core.scene.Component;
import pl.warp.engine.core.scene.Property;

/**
 * @author Jaca777
 *         Created 2017-03-04 at 14
 */
public class AnimatedTextureProperty extends Property<Component> {
    public static final String ANIMATED_TEXTURE_PROPERTY_NAME = "animatedTexture";

    private Vector2f direction;
    private float delta;

    public AnimatedTextureProperty(Vector2f direction) {
        super(ANIMATED_TEXTURE_PROPERTY_NAME);
        this.direction = direction;
        this.delta = 0;
    }

    public Vector2f getDirection() {
        return direction;
    }

    public void setDirection(Vector2f direction) {
        this.direction = direction;
    }

    public float getDelta() {
        return delta;
    }

    public void setDelta(float delta) {
        this.delta = delta;
    }
}
