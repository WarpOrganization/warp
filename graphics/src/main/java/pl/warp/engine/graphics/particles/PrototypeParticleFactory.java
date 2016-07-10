package pl.warp.engine.graphics.particles;

import org.joml.Vector2f;
import org.joml.Vector3f;

/**
 * @author Jaca777
 *         Created 2016-07-10 at 15
 */
public class PrototypeParticleFactory implements ParticleFactory {
    private Particle prototype;

    public PrototypeParticleFactory(Particle prototype) {
        this.prototype = prototype;
    }

    @Override
    public Particle newParticle() {
        return copy(prototype);
    }

    private Particle copy(Particle particle) {
        Vector3f position = new Vector3f(particle.getPosition());
        Vector3f velocity = new Vector3f(particle.getVelocity());
        Vector2f scale = new Vector2f(particle.getScale());
        float rotation = particle.getRotation();
        int ttl = particle.getTimeToLive();
        return new Particle(position, velocity, scale, rotation, ttl);
    }

}
