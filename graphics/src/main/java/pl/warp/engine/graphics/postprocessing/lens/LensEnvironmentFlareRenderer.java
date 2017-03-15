package pl.warp.engine.graphics.postprocessing.lens;

import pl.warp.engine.core.scene.Component;
import pl.warp.engine.graphics.Environment;
import pl.warp.engine.graphics.Renderer;
import pl.warp.engine.graphics.math.MatrixStack;

/**
 * @author Jaca777
 *         Created 2016-07-25 at 12
 */
public class LensEnvironmentFlareRenderer implements Renderer{
    
    private Environment environment;

    public LensEnvironmentFlareRenderer(Environment environment) {
        this.environment = environment;
    }

    @Override
    public void init() {
    }

    @Override
    public void initRendering() {
        environment.resetLensFlareComponents();
    }

    @Override
    public void render(Component component, MatrixStack stack) {
        if(component.hasEnabledProperty(GraphicsLensFlareProperty.LENS_FLARE_PROPERTY_NAME))
            environment.addLensFlareComponent(component);
    }

    @Override
    public void destroy() {

    }
}
