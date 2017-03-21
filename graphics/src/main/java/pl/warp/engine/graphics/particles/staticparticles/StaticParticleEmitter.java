package pl.warp.engine.graphics.particles.staticparticles;

import org.joml.Vector3f;
import pl.warp.engine.core.scene.Component;
import pl.warp.engine.core.scene.properties.TransformProperty;
import pl.warp.engine.graphics.particles.Particle;
import pl.warp.engine.graphics.particles.ParticleEmitter;
import pl.warp.engine.graphics.particles.ParticleFactory;

import java.util.List;
import java.util.Random;

/**
 * @author Jaca777
 *         Created 2017-03-21 at 20
 */
public class StaticParticleEmitter<T extends Particle> extends ParticleEmitter<T> {

    private static final Random random = new Random();

    private Component component;
    private boolean emitted = false;
    private int particleCount;
    private float boxEdge;

    public StaticParticleEmitter(Component component, int particleCount, float boxEdge) {
        super(0);
        this.component = component;
        this.particleCount = particleCount;
        this.boxEdge = boxEdge;
    }

    @Override
    public void emit(ParticleFactory<T> particleFactory, List<T> particles, int delta) {
        if (!emitted) {
            emitStatic(particleFactory, particles);
            emitted = true;
        }
    }

    protected void emitStatic(ParticleFactory<T> particleFactory, List<T> particles) {
        for (int i = 0; i < particleCount; i++)
            particles.add(newParticle(particleFactory));
    }

    @Override
    protected T newParticle(ParticleFactory<T> factory) {
        float x = random.nextFloat() * boxEdge - boxEdge * 0.5f;
        float y = random.nextFloat() * boxEdge - boxEdge * 0.5f;
        float z = random.nextFloat() * boxEdge - boxEdge * 0.5f;
        TransformProperty transform = component.getProperty(TransformProperty.TRANSFORM_PROPERTY_NAME);
        return factory.newParticle(new Vector3f(x, y, z).add(transform.getTranslation()), new Vector3f(0), 1.0f, 0.0f, Integer.MAX_VALUE);
    }
}
