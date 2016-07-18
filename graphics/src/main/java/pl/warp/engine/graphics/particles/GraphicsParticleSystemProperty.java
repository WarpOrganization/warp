package pl.warp.engine.graphics.particles;

import pl.warp.engine.core.scene.Component;
import pl.warp.engine.core.scene.Property;

/**
 * @author Jaca777
 *         Created 2016-06-29 at 20
 */
public class GraphicsParticleSystemProperty extends Property<Component> {
    public static final String PARTICLE_SYSTEM_PROPERTY_NAME = "particleSystem";
    private ParticleSystem system;

    public GraphicsParticleSystemProperty(Component owner, ParticleSystem system) {
        super(owner, PARTICLE_SYSTEM_PROPERTY_NAME);
        this.system = system;
    }

    public ParticleSystem getSystem() {
        return system;
    }
}
