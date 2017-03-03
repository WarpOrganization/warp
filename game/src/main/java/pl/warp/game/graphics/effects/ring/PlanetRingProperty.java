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
    private boolean renderShadow;

    public PlanetRingProperty(float startRadius, float endRadius, Texture1D ringColors, boolean renderShadow) {
        super(PLANETARY_RING_PROPERTY_NAME);
        this.startRadius = startRadius;
        this.endRadius = endRadius;
        this.ringColors = ringColors;
        this.renderShadow = renderShadow;
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

    public boolean getRenderShadow() {
        return renderShadow;
    }
}
