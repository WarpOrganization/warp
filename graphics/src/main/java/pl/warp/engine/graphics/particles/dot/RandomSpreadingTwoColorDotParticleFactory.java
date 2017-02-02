package pl.warp.engine.graphics.particles.dot;

import org.joml.Vector3f;
import org.joml.Vector4f;
import pl.warp.engine.graphics.particles.RandomSpreadingParticleFactory;

/**
 * @author Jaca777
 *         Created 2016-08-10 at 14
 */
public class RandomSpreadingTwoColorDotParticleFactory extends RandomSpreadingParticleFactory<DotParticle> {

    private Vector4f initialColor;
    private Vector4f endColor;
    private float gradient;

    public RandomSpreadingTwoColorDotParticleFactory(float velocity, int timeToLive, int timeToLiveGradient, boolean randomizeRotation /* <- U WOT? TODO REMOVE*/, boolean randomizeScaleScalar, Vector4f initialColor, Vector4f endColor, float gradient) {
        super(velocity, timeToLive, timeToLiveGradient, randomizeRotation, randomizeScaleScalar);
        this.initialColor = initialColor;
        this.endColor = endColor;
        this.gradient = gradient;
    }

    @Override
    public DotParticle newParticle(Vector3f position, Vector3f velocity, float scale, float rotation, int ttl) {
        return new TwoColorDotParticle(position, velocity, scale, ttl, ttl, initialColor, endColor, gradient);
    }
}
