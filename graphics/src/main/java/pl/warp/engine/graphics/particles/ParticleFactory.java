package pl.warp.engine.graphics.particles;

import org.joml.Vector3f;

/**
 * @author Jaca777
 *         Created 2016-07-10 at 15
 */
public interface ParticleFactory <T extends Particle> {
    T newParticle(Vector3f position, Vector3f velocity, float scale, float rotation, int ttl);
}
