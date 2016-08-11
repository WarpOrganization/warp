package pl.warp.engine.graphics.particles.textured;

import org.joml.Vector3f;
import org.joml.Vector4f;
import pl.warp.engine.graphics.particles.RandomSpreadingParticleFactory;
import pl.warp.engine.graphics.particles.dot.DotParticle;

/**
 * @author Jaca777
 *         Created 2016-08-10 at 14
 */
public class RandomSpreadingTexturedParticleFactory extends RandomSpreadingParticleFactory<TexturedParticle> {

    public RandomSpreadingTexturedParticleFactory(float velocity, int timeToLive, boolean randomizeRotation, boolean randomizeScaleScalar) {
        super(velocity, timeToLive, randomizeRotation, randomizeScaleScalar);
    }

    @Override
    public TexturedParticle newParticle(Vector3f position, Vector3f velocity, float scale, float rotation, int ttl) {
        return new TexturedParticle(position, velocity, scale, rotation, ttl, ttl, 0);
    }
}
