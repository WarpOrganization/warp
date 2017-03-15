package pl.warp.game.graphics.effects.atmosphere;

import pl.warp.engine.core.scene.Property;

/**
 * @author Jaca777
 *         Created 2017-03-12 at 12
 */
public class AtmosphereContextProperty  extends Property {
    public static final String ATMOSPHERE_CONTEXT_PROPERTY_NAME = "atmosphereContext";

    private AtmosphereRenderer renderer;
    private AtmosphereProgram program;

    public AtmosphereContextProperty(AtmosphereRenderer renderer, AtmosphereProgram program) {
        super(ATMOSPHERE_CONTEXT_PROPERTY_NAME);
        this.renderer = renderer;
        this.program = program;
    }

    public AtmosphereRenderer getRenderer() {
        return renderer;
    }

    public AtmosphereProgram getProgram() {
        return program;
    }
}
