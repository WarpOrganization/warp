package pl.warp.engine.graphics.particles;

/**
 * @author Jaca777
 *         Created 2017-03-09 at 22
 */
@FunctionalInterface
public interface ParticleRendererFactory<T extends Particle> {
    ParticleRenderer<T> createRenderer();

}
