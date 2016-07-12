package pl.warp.engine.graphics.particles;

import pl.warp.engine.core.scene.Component;
import pl.warp.engine.core.scene.Property;

/**
 * @author Jaca777
 *         Created 2016-07-12 at 14
 */
class GraphicsParticleEnvironmentProperty extends Property<Component> {
    public static final String PARTICLE_ENVIRONMENT_PROPERTY_NAME = "particleEnvironment";

    private ParticleEnvironment environment;

    public GraphicsParticleEnvironmentProperty(Component owner, ParticleEnvironment environment) {
        super(owner, PARTICLE_ENVIRONMENT_PROPERTY_NAME);
        this.environment = environment;
    }

    public ParticleEnvironment getEnvironment() {
        return environment;
    }
}
