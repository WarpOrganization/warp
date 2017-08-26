package pl.warp.engine.game.graphics.effects.star.corona;

import pl.warp.engine.core.component.Component;
import pl.warp.engine.core.property.Property;

/**
 * @author Jaca777
 *         Created 2017-02-09 at 23
 */
public class CoronaProperty extends Property<Component>{
    public static final String CORONA_PROPERTY_NAME = "coronaProperty";

    private float temperature;
    private float size;

    public CoronaProperty(float temperature, float size) {
        super(CORONA_PROPERTY_NAME);
        this.temperature = temperature;
        this.size = size;
    }


    public float getTemperature() {
        return temperature;
    }

    public float getSize() {
        return size;
    }

    public void setTemperature(float temperature) {
        this.temperature = temperature;
    }

    public void setSize(float size) {
        this.size = size;
    }
}
