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

    private List<Flare> flares;

    public GraphicsLensFlareProperty(Component owner) {
        super(owner, LENS_FLARE_PROPERTY_NAME);
    }

    public void addFlare(Flare flare) {
        flares.add(flare);
    }

    public List<Flare> getFlares() {
        return flares;
    }
}
