package pl.warp.engine.game.graphics.effects.star;

import pl.warp.engine.core.property.Property;
import pl.warp.engine.game.scene.GameSceneComponent;

/**
 * @author Jaca777
 *         Created 2017-02-09 at 00
 */
public class StarProperty extends Property<GameSceneComponent> {
    public static final String STAR_PROPERTY_NAME = "starProperty";

    private float temperature;

    public StarProperty(float temperature) {
        super(STAR_PROPERTY_NAME);
        this.temperature = temperature;
    }

    public float getTemperature() {
        return temperature;
    }

    public void setTemperature(float temperature) {
        this.temperature = temperature;
    }

}
