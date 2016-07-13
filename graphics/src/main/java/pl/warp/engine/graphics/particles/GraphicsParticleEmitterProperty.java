package pl.warp.engine.graphics.particles;

import pl.warp.engine.core.scene.Component;
import pl.warp.engine.core.scene.Property;

/**
 * @author Jaca777
 *         Created 2016-06-29 at 20
 */
public class GraphicsParticleEmitterProperty extends Property<Component> {
    public static final String PARTICLES_EMITTER_PROPERTY_NAME = "particles";
    private ParticleEmitter emitter;
    private ParticleEnvironment environment;

    public GraphicsParticleEmitterProperty(Component emitterComponent, ParticleEmitter emitter, Component environmentComponent) {
        super(emitterComponent, PARTICLES_EMITTER_PROPERTY_NAME);
        this.emitter = emitter;
        this.environment = emitter.getEnvironment();
        new GraphicsParticleEnvironmentProperty(environmentComponent, environment);
    }

    public GraphicsParticleEmitterProperty(Component owner, ParticleEmitter emitter) {
        this(owner, emitter, owner);
    }

    public ParticleEmitter getEmitter() {
        return emitter;
    }

    public ParticleEnvironment getEnvironment() {
        return environment;
    }
}
