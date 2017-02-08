package pl.warp.game.graphics.effects.gas;

import pl.warp.engine.core.scene.Property;
import pl.warp.game.scene.GameComponent;

/**
 * @author Jaca777
 *         Created 2017-02-08 at 19
 */
public class GasPlanetContextProperty extends Property<GameComponent> {
    public static final String GAS_PLANET_CONTEXT_PROPERTY_NAME = "gasPlanetContextProperty";


    private GasPlanetProgram program;

    public GasPlanetContextProperty(GasPlanetProgram program) {
        super(GAS_PLANET_CONTEXT_PROPERTY_NAME);
        this.program = program;
    }

    public GasPlanetProgram getProgram() {
        return program;
    }
}
