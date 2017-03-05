package pl.warp.engine.graphics.particles.textured;

import org.joml.Vector3f;
import pl.warp.engine.graphics.particles.RandomSpreadingParticleFactory;

/**
 * @author Jaca777
 *         Created 2016-08-10 at 14
 */
public class RandomSpreadingTexturedParticleFactory extends RandomSpreadingParticleFactory<TexturedParticle> {


    public RandomSpreadingTexturedParticleFactory(Vector3f initialVelocity, Vector3f spread, int timeToLive, int timeToLiveGradient, boolean randomizeRotation, boolean randomizeScaleScalar) {
        super(initialVelocity, spread, timeToLive, timeToLiveGradient, randomizeRotation, randomizeScaleScalar);
    }

    @Override
    public TexturedParticle newParticle(Vector3f position, Vector3f velocity, float scale, float rotation, int ttl) {
        return new TexturedParticle(position, velocity, scale, rotation, ttl, ttl, 0);
    }
}
