package pl.warp.engine.graphics.postprocessing.lens;

import pl.warp.engine.core.component.Component;
import pl.warp.engine.core.property.Property;

/**
 * @author Jaca777
 *         Created 2016-07-25 at 12
 */
public class GraphicsLensFlareProperty extends Property<Component> {
    public static final String LENS_FLARE_PROPERTY_NAME = "lensFlare";

    private LensFlare flare;

    public GraphicsLensFlareProperty(LensFlare flare) {
        super(LENS_FLARE_PROPERTY_NAME);
        this.flare = flare;
    }

    public LensFlare getFlare() {
        return flare;
    }
}
