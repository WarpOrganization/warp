package pl.warp.engine.graphics.particles.textured;

import org.joml.Vector3f;
import pl.warp.engine.graphics.particles.ParticleFactory;
import pl.warp.engine.graphics.texture.Texture2DArray;

/**
 * @author Jaca777
 *         Created 2017-03-10 at 20
 */
public class TexturedParticleFactory implements ParticleFactory<TexturedParticle> {

    private Texture2DArray spritesheet;

    public TexturedParticleFactory(Texture2DArray spritesheet) {
        this.spritesheet = spritesheet;
    }

    @Override
    public TexturedParticle newParticle(Vector3f position, Vector3f velocity, float scale, float rotation, int ttl) {
        return new TexturedParticle(position, velocity, scale, rotation, ttl, ttl, 0, spritesheet);
    }


}
