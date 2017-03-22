package pl.warp.game.graphics.effects.gasplanet;

import pl.warp.engine.core.scene.Property;
import pl.warp.engine.graphics.texture.Texture1D;
import pl.warp.game.scene.GameComponent;

/**
 * @author Jaca777
 *         Created 2017-02-08 at 19
 */
public class GasPlanetProperty extends Property<GameComponent> {
    public static final String GAS_PLANET_PROPERTY_NAME = "gasPlanetProperty";
    private Texture1D colors;

    public GasPlanetProperty(Texture1D colors) {
        super(GAS_PLANET_PROPERTY_NAME);
        this.colors = colors;
    }

    public Texture1D getColors() {
        return colors;
    }
}
