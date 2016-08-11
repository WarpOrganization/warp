package pl.warp.engine.graphics.particles.dot;

import org.joml.Vector3f;
import org.joml.Vector4f;
import pl.warp.engine.graphics.particles.PrototypeParticleFactory;
import pl.warp.engine.graphics.particles.textured.TexturedParticle;

/**
 * @author Jaca777
 *         Created 2016-08-10 at 13
 */
public class PrototypeDotParticleFactory extends PrototypeParticleFactory<DotParticle> {

    public PrototypeDotParticleFactory(DotParticle prototype) {
        super(prototype);
    }

    @Override
    public DotParticle copy(DotParticle particle) {
        Vector3f position = new Vector3f(particle.getPosition());
        Vector3f velocity = new Vector3f(particle.getVelocity());
        float scale = particle.getScale();
        Vector4f color = new Vector4f(particle.getColor());
        float gradient = particle.getGradient();
        int ttl = particle.getTimeToLive();
        int tttl = particle.getTotalTimeToLive();
        return new DotParticle(position, velocity, scale, ttl, tttl, color, gradient);
    }

}
