package pl.warp.engine.graphics.particles;

/**
 * @author Jaca777
 *         Created 2016-07-10 at 14
 */
public interface ParticleAnimator<T extends Particle> {
    void animate(T particle, int delta);
}
