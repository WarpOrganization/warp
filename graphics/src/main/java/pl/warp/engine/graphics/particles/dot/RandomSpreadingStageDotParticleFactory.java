package pl.warp.engine.graphics.particles.dot;

import org.joml.Vector3f;
import pl.warp.engine.graphics.particles.RandomSpreadingParticleFactory;

/**
 * @author Jaca777
 *         Created 2016-08-10 at 14
 */
public class RandomSpreadingStageDotParticleFactory extends RandomSpreadingParticleFactory<DotParticle> {

    private ParticleStage[] stages;

    public RandomSpreadingStageDotParticleFactory(float velocity, int timeToLive, int timeToLiveGradient, boolean randomizeRotation /* <- U WOT? TODO REMOVE*/, boolean randomizeScaleScalar, ParticleStage[] stages) {
        super(velocity, timeToLive, timeToLiveGradient, randomizeRotation, randomizeScaleScalar);
        this.stages = stages;
    }

    @Override
    public DotParticle newParticle(Vector3f position, Vector3f velocity, float scale, float rotation, int ttl) {
        return new StageDotParticle(position, velocity, scale, rotation, ttl, ttl, stages);
    }
}
