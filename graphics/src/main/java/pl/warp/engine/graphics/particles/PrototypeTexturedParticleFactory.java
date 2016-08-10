package pl.warp.engine.graphics.particles;

import org.joml.Vector2f;
import org.joml.Vector3f;
import pl.warp.engine.graphics.particles.textured.TexturedParticle;

/**
 * @author Jaca777
 *         Created 2016-07-10 at 15
 */
public class PrototypeTexturedParticleFactory implements ParticleFactory<TexturedParticle> {
    private TexturedParticle prototype;

    public PrototypeTexturedParticleFactory(TexturedParticle prototype) {
        this.prototype = prototype;
    }

    @Override
    public TexturedParticle newParticle() {
        return copy(prototype);
    }

    private TexturedParticle copy(TexturedParticle particle) {
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
