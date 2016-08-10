package pl.warp.engine.graphics.particles.dot;

import org.joml.Vector3f;
import org.joml.Vector4f;
import pl.warp.engine.graphics.particles.RandomSpreadingParticleFactory;

/**
 * @author Jaca777
 *         Created 2016-08-10 at 14
 */
public class RandomSpreadingDotParticleFactory extends RandomSpreadingParticleFactory<DotParticle> {

    private Vector4f initialColor;
    private float gradient;

    public RandomSpreadingDotParticleFactory(float velocity, int timeToLive, boolean randomizeRotation, boolean randomizeScaleScalar, Vector4f initialColor, float gradient) {
        super(velocity, timeToLive, randomizeRotation, randomizeScaleScalar);
        this.initialColor = initialColor;
        this.gradient = gradient;
    }

    @Override
    public DotParticle newParticle(Vector3f position, Vector3f velocity, float scale, float rotation, int ttl) {
        return new DotParticle(position, velocity, scale, ttl, ttl, new Vector4f(initialColor), gradient);
    }
}
