package pl.warp.engine.graphics.particles.animator;

import org.joml.Vector3f;
import pl.warp.engine.graphics.particles.Particle;
import pl.warp.engine.graphics.particles.ParticleAnimator;

/**
 * @author Jaca777
 *         Created 2017-03-09 at 22
 */
public class LinearAccelerationAnimator implements ParticleAnimator {
    private float acceleration;

    public LinearAccelerationAnimator(float acceleration) {
        this.acceleration = acceleration;
    }

    private Vector3f temp = new Vector3f();
    @Override
    public void animate(Particle particle, int delta) {
        Vector3f direction = particle.getVelocity().normalize(temp);
        Vector3f velocityDelta = direction.mul(acceleration * delta);
        particle.getVelocity().add(velocityDelta);
    }
}
