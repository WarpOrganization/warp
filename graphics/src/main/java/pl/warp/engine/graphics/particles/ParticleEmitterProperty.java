package pl.warp.engine.graphics.particles;

import pl.warp.engine.core.component.Component;
import pl.warp.engine.core.property.Property;

/**
 * @author Jaca777
 *         Created 2016-06-29 at 20
 */
public class ParticleEmitterProperty extends Property<Component> {

    public static final String PARTICLE_EMITTER_PROPERTY_NAME = "particleEmitter";
    private ParticleSystem system;

    public ParticleEmitterProperty(ParticleSystem system) {
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
