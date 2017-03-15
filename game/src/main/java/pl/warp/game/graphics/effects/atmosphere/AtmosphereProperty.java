package pl.warp.game.graphics.effects.atmosphere;

import org.joml.Vector3f;
import pl.warp.engine.core.scene.Property;
import pl.warp.game.scene.GameComponent;

/**
 * @author Jaca777
 *         Created 2017-03-12 at 11
 */
public class AtmosphereProperty extends Property<GameComponent> {
    public static final String ATMOSPHERE_PROPERTY_NAME = "atmosphere";

    private Vector3f color;

    private float atmosphereRadius;

    public AtmosphereProperty(Vector3f color, float atmosphereRadius) {
        super(ATMOSPHERE_PROPERTY_NAME);
        this.color = color;
        this.atmosphereRadius = atmosphereRadius;
    }

    public Vector3f getColor() {
        return color;
    }

    public float getAtmosphereRadius() {
        return atmosphereRadius;
    }
}
