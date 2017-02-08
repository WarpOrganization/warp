package pl.warp.game.graphics.effects.ring;

import pl.warp.engine.core.scene.Component;
import pl.warp.engine.core.scene.Property;
import pl.warp.engine.graphics.texture.Texture1D;

/**
 * @author Jaca777
 *         Created 2016-08-05 at 18
 */
public class PlanetRingProperty extends Property<Component> {

    public static final String PLANETARY_RING_PROPERTY_NAME = "planetaryRingProperty";

    private float startRadius;
    private float endRadius;
    private Texture1D ringColors;

    public PlanetRingProperty(float startMeshRadius, float endMeshRadius, Texture1D ringColors) {
        super(PLANETARY_RING_PROPERTY_NAME);
        this.startRadius = startMeshRadius;
        this.endRadius = endMeshRadius;
        this.ringColors = ringColors;
    }

    public float getStartRadius() {
        return startRadius;
    }

    public float getEndRadius() {
        return endRadius;
    }

    public Texture1D getRingColors() {
        return ringColors;
    }
}
