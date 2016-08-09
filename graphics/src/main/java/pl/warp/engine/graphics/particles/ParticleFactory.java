package pl.warp.engine.graphics.particles;

import pl.warp.engine.graphics.particles.textured.TexturedParticle;

/**
 * @author Jaca777
 *         Created 2016-07-10 at 15
 */
public interface ParticleFactory <T extends Particle> {
    T newParticle();
}
