package pl.warp.engine.game.graphics.effects.planet;

import pl.warp.engine.core.scene.Property;
import pl.warp.engine.graphics.texture.Cubemap;

/**
 * @author Jaca777
 *         Created 2017-03-22 at 13
 */
public class PlanetProperty extends Property {

    public static final String PLANET_PROPERTY_NAME = "planetProperty";

    private Cubemap planetSurfaceTexture;

    public PlanetProperty(Cubemap planetSurfaceTexture) {
        super(PLANET_PROPERTY_NAME);
        this.planetSurfaceTexture = planetSurfaceTexture;
    }

    public Cubemap getPlanetSurfaceTexture() {
        return planetSurfaceTexture;
    }
}
