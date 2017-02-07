package pl.warp.engine.graphics.particles;

import pl.warp.engine.core.scene.Component;
import pl.warp.engine.graphics.Renderer;
import pl.warp.engine.graphics.math.MatrixStack;

/**
 * @author Jaca777
 *         Created 2017-02-07 at 17
 */
public class ParticleSystemsRecorder implements Renderer {

    ParticleSystemStorage storage;

    public ParticleSystemsRecorder(ParticleSystemStorage storage) {
        this.storage = storage;
    }

    @Override
    public void init() {

    }

    @Override
    public void initRendering(int delta) {
        storage.reset();
    }

    @Override
    public void render(Component component, MatrixStack stack) {
        if(component.hasProperty(GraphicsParticleEmitterProperty.PARTICLE_EMITTER_PROPERTY_NAME)){
            GraphicsParticleEmitterProperty emitterProperty = component.getProperty(GraphicsParticleEmitterProperty.PARTICLE_EMITTER_PROPERTY_NAME);
            storage.add(emitterProperty.getSystem(), stack.topMatrix());
        }
    }

    @Override
    public void destroy() {

    }
}
