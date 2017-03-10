package pl.warp.engine.graphics.particles;

/**
 * @author Jaca777
 *         Created 2017-03-10 at 21
 */
public interface ParticleSystemAttribute<T extends Particle> {
    ParticleFactory<T> getParticleFactory();
    ParticleRendererFactory<T> getParticleRendererFactory();
}
