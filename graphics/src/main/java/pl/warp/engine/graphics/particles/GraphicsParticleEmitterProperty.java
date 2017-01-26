package pl.warp.engine.graphics.particles;

import pl.warp.engine.core.scene.Component;
import pl.warp.engine.core.scene.Property;

/**
 * @author Jaca777
 *         Created 2016-06-29 at 20
 */
public class GraphicsParticleEmitterProperty extends Property<Component> {

    public static final String PARTICLE_EMITTER_PROPERTY_NAME = "particleEmitter";
    private ParticleSystem system;

    public GraphicsParticleEmitterProperty(ParticleSystem system) {
        super(PARTICLE_EMITTER_PROPERTY_NAME);
        this.system = system;
    }

    @Override
    public void enable() {
        super.enable();
    }

    @Override
    public void disable() {
        super.disable();
    }


    public ParticleSystem getSystem() {
        return system;
    }
}
