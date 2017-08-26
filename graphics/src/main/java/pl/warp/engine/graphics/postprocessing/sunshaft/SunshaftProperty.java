package pl.warp.engine.graphics.postprocessing.sunshaft;

import pl.warp.engine.core.component.Component;
import pl.warp.engine.core.property.Property;

/**
 * @author Jaca777
 *         Created 2017-02-25 at 17
 */
public class SunshaftProperty extends Property<Component> {
    public static final String SUNSHAFT_PROPERTY_NAME = "sunshaft";

    private SunshaftSource source;

    public SunshaftProperty() {
        super(SUNSHAFT_PROPERTY_NAME);
        this.source = new SunshaftSource();
    }

    public SunshaftSource getSource() {
        return source;
    }
}
