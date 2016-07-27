package pl.warp.engine.graphics.postprocessing.lens;

import pl.warp.engine.core.scene.Component;
import pl.warp.engine.core.scene.Property;

import java.util.List;

/**
 * @author Jaca777
 *         Created 2016-07-25 at 12
 */
public class GraphicsLensFlareProperty extends Property<Component> {
    public static final String LENS_FLARE_PROPERTY_NAME = "lensFlare";

    private LensFlare flare;

    public GraphicsLensFlareProperty(Component owner, LensFlare flare) {
        super(owner, LENS_FLARE_PROPERTY_NAME);
        this.flare = flare;
    }

    public LensFlare getFlare() {
        return flare;
    }
}
