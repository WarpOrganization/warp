package pl.warp.game.graphics.effects.planet;

import pl.warp.engine.core.scene.Property;
import pl.warp.game.scene.GameComponent;

/**
 * @author Jaca777
 *         Created 2017-03-22 at 16
 */
public class PlanetContextProperty extends Property<GameComponent> {
    public static final String PLANET_CONTEXT_PROPERTY = "planetContextProperty";

    private PlanetProgram program;

    public PlanetContextProperty(PlanetProgram program) {
        super(PLANET_CONTEXT_PROPERTY);
        this.program = program;
    }

    public PlanetProgram getProgram() {
        return program;
    }
}
