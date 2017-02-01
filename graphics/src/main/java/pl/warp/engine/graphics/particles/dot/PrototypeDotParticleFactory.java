package pl.warp.engine.graphics.particles.dot;

import org.joml.Vector3f;
import org.joml.Vector4f;
import pl.warp.engine.graphics.particles.PrototypeParticleFactory;

/**
 * @author Jaca777
 *         Created 2016-08-10 at 13
 */
public class PrototypeDotParticleFactory extends PrototypeParticleFactory<TwoColorDotParticle> {

    public PrototypeDotParticleFactory(TwoColorDotParticle prototype) {
        super(prototype);
    }

    @Override
    public TwoColorDotParticle copy(TwoColorDotParticle particle) {
        Vector3f position = new Vector3f(particle.getPosition());
        Vector3f velocity = new Vector3f(particle.getVelocity());
        float scale = particle.getScale();
        Vector4f color = new Vector4f(particle.getStartColor());
        Vector4f endColor = particle.getEndColor();
        float gradient = particle.getGradient();
        int ttl = particle.getTimeToLive();
        int tttl = particle.getTotalTimeToLive();
        return new TwoColorDotParticle(position, velocity, scale, ttl, tttl, color, endColor, gradient);
    }

}
