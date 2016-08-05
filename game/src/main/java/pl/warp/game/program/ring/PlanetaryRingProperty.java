package pl.warp.game.program.ring;

import pl.warp.engine.core.scene.Component;
import pl.warp.engine.core.scene.Property;

/**
 * @author Jaca777
 *         Created 2016-08-05 at 18
 */
public class PlanetaryRingProperty extends Property<Component> {

    public static final String PLANETARY_RING_PROPERTY_NAME = "planetaryRingProperty";

    private float startRadius;
    private float endRadius;

    public PlanetaryRingProperty(Component owner, float startMeshRadius, float endMeshRadius) {
        super(owner, PLANETARY_RING_PROPERTY_NAME);
        this.startRadius = startMeshRadius;
        this.endRadius = endMeshRadius;
    }



    public float getStartRadius() {
        return startRadius;
    }

    public float getEndRadius() {
        return endRadius;
    }
}
