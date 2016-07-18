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
    public void render(Component component, MatrixStack stack) { //TODO find another way, it's not a renderer.
        if (component.hasEnabledProperty(GraphicsParticleSystemProperty.PARTICLE_SYSTEM_PROPERTY_NAME))
            update(component.getProperty(GraphicsParticleSystemProperty.PARTICLE_SYSTEM_PROPERTY_NAME));
    }

    private void update(GraphicsParticleSystemProperty property) {
        ParticleSystem system = property.getSystem();
        system.update(delta);
    }

    @Override
    public void destroy() {

    }
}
