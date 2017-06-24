package pl.warp.engine.game.graphics.effects.star;

import pl.warp.engine.core.scene.Property;
import pl.warp.engine.game.graphics.effects.star.corona.CoronaProgram;
import pl.warp.engine.game.graphics.effects.star.corona.CoronaRenderer;
import pl.warp.engine.game.scene.GameComponent;

/**
 * @author Jaca777
 *         Created 2017-02-08 at 23
 */
public class StarContextProperty extends Property<GameComponent> {
    public static final String STAR_CONTEXT_PROPERTY_NAME = "starContextProperty";

    private StarProgram starProgram;
    private CoronaProgram coronaProgram;
    private CoronaRenderer coronaRenderer;

    public StarContextProperty(StarProgram starProgram, CoronaProgram coronaProgram, CoronaRenderer coronaRenderer) {
        super(STAR_CONTEXT_PROPERTY_NAME);
        this.starProgram = starProgram;
        this.coronaProgram = coronaProgram;
        this.coronaRenderer = coronaRenderer;
    }

    public StarProgram getStarProgram() {
        return starProgram;
    }

    public CoronaProgram getCoronaProgram() {
        return coronaProgram;
    }

    public CoronaRenderer getCoronaRenderer() {
        return coronaRenderer;
    }
}
