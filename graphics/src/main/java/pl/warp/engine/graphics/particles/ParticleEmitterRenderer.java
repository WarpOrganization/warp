package pl.warp.engine.graphics.particles;

import pl.warp.engine.core.scene.Component;
import pl.warp.engine.graphics.math.MatrixStack;
import pl.warp.engine.graphics.Renderer;

/**
 * @author Jaca777
 *         Created 2016-07-12 at 14
 */
public class ParticleEmitterRenderer implements Renderer {

    private int delta;

    @Override
    public void init() {

    }

    @Override
    public void initRendering(int delta) {
        this.delta = delta;
    }

    @Override
    public void render(Component component, MatrixStack stack) {
        if (component.hasEnabledProperty(GraphicsParticleEmitterProperty.PARTICLES_EMITTER_PROPERTY_NAME))
            update(component.getProperty(GraphicsParticleEmitterProperty.PARTICLES_EMITTER_PROPERTY_NAME));
    }

    private void update(GraphicsParticleEmitterProperty property) {
        ParticleEmitter emitter = property.getEmitter();
        emitter.update(delta);
    }

    @Override
    public void destroy() {

    }
}
