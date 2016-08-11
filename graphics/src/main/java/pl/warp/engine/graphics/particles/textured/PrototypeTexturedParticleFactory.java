package pl.warp.engine.graphics.particles.textured;

import org.joml.Vector3f;
import pl.warp.engine.graphics.particles.PrototypeParticleFactory;

import javax.xml.soap.Text;

/**
 * @author Jaca777
 *         Created 2016-08-10 at 13
 */
public class PrototypeTexturedParticleFactory extends PrototypeParticleFactory<TexturedParticle> {

    public PrototypeTexturedParticleFactory(TexturedParticle prototype) {
        super(prototype);
    }

    @Override
    public TexturedParticle copy(TexturedParticle particle) {
        Vector3f position = new Vector3f(particle.getPosition());
        Vector3f velocity = new Vector3f(particle.getVelocity());
        float scale = particle.getScale();
        float rotation = particle.getRotation();
        int textureIndex = particle.getTextureIndex();
        int ttl = particle.getTimeToLive();
        int tttl = particle.getTotalTimeToLive();
        return new TexturedParticle(position, velocity, scale, rotation, textureIndex, tttl, ttl);
    }

}
