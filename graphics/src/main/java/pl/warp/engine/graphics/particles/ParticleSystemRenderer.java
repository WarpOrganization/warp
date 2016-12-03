package pl.warp.engine.graphics.particles;

import org.apache.log4j.Logger;
import pl.warp.engine.core.scene.Component;
import pl.warp.engine.graphics.Renderer;
import pl.warp.engine.graphics.camera.Camera;
import pl.warp.engine.graphics.math.MatrixStack;
import pl.warp.engine.graphics.particles.dot.DotParticleRenderer;
import pl.warp.engine.graphics.particles.textured.TexturedParticleRenderer;
import pl.warp.engine.graphics.postprocessing.lens.LensFlareRenderer;

/**
 * @author Jaca777
 *         Created 2016-07-11 at 13
 */
public class ParticleSystemRenderer implements Renderer {

    private static final Logger logger = Logger.getLogger(LensFlareRenderer.class);

    public static final int MAX_PARTICLES_NUMBER = 1000;

    private Camera camera;
    private DotParticleRenderer dotParticleRenderer;
    private TexturedParticleRenderer texturedParticleRenderer;



    public ParticleSystemRenderer(Camera camera) {
        this.camera = camera;
    }

    @Override
    public void init() {
        logger.info("Initializing particle renderer...");
        dotParticleRenderer = new DotParticleRenderer();
        texturedParticleRenderer = new TexturedParticleRenderer();
        logger.info("Particle renderer initialized...");
    }

    @Override
    public void initRendering(int delta) {
        texturedParticleRenderer.useCamera(camera);
        dotParticleRenderer.useCamera(camera);
    }

    @Override
    public void render(Component component, MatrixStack stack) {
        if (component.hasEnabledProperty(GraphicsParticleEmitterProperty.PARTICLE_EMITTER_PROPERTY_NAME)) {
            GraphicsParticleEmitterProperty emitterProperty =
                    component.getProperty(GraphicsParticleEmitterProperty.PARTICLE_EMITTER_PROPERTY_NAME);
            ParticleSystem system = emitterProperty.getSystem();
            getRenderer(system).render(system, stack);
        }
    }

    private ParticleRenderer getRenderer(ParticleSystem system) {
        switch (system.getParticleType()) {
            case DOT:
                return dotParticleRenderer;
            case TEXTURED:
                return texturedParticleRenderer;
            default:
                throw new IllegalStateException("Unknown particle type: " + system.getParticleType());
        }
    }

    @Override
    public void destroy() {
        texturedParticleRenderer.destroy();
    }

}
