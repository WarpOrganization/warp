package pl.warp.game.graphics.effects.ring;

import pl.warp.engine.core.scene.Property;
import pl.warp.game.scene.GameComponent;

/**
 * @author Jaca777
 *         Created 2017-02-08 at 20
 */
public class PlanetRingContextProperty extends Property<GameComponent> {
    public static final String PLANETARY_RING_CONTEXT_PROPERTY_NAME = "planetaryRingContextProperty";

    private PlanetRingProgram program;

    public PlanetRingContextProperty(PlanetRingProgram program) {
        super(PLANETARY_RING_CONTEXT_PROPERTY_NAME);
        this.program = program;
    }

    public PlanetRingProgram getProgram() {
        return program;
    }
}
