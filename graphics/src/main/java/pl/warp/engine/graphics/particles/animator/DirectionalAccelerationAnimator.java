package pl.warp.engine.graphics.particles.animator;

import org.joml.Vector3f;
import pl.warp.engine.graphics.particles.Particle;
import pl.warp.engine.graphics.particles.ParticleAnimator;

/**
 * @author Jaca777
 *         Created 2017-03-09 at 22
 */
public class DirectionalAccelerationAnimator implements ParticleAnimator {
    private Vector3f acceleration;

    public DirectionalAccelerationAnimator(Vector3f acceleration) {
        this.acceleration = acceleration;
    }

    private Vector3f temp = new Vector3f();

    @Override
    public void animate(Particle particle, int delta) {
        Vector3f velocityDelta = temp.set(acceleration).mul(delta);
        particle.getVelocity().add(velocityDelta);
    }
}
