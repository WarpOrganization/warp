package pl.warp.engine.graphics.particles;

import org.joml.Vector2f;
import org.joml.Vector3f;

/**
 * @author Jaca777
 *         Created 2016-07-10 at 14
 */
public class SimpleParticleAnimator implements ParticleAnimator<Particle> {
    private Vector3f acceleration;
    private float scalingRate;
    private float rotationRate;

    public SimpleParticleAnimator(Vector3f acceleration, float scalingRate, float rotationRate) {
        this.acceleration = acceleration;
        this.scalingRate = scalingRate;
        this.rotationRate = rotationRate;
    }

    private Vector3f tempVector3 = new Vector3f();

    @Override
    public void animate(Particle particle, int delta) {
        updateVelocity(particle, delta);
        updateScale(particle, delta);
        updateRotation(particle, delta);
    }

    private void updateVelocity(Particle particle, int delta) {
        Vector3f velocityDelta = acceleration.mul(delta, tempVector3);
        particle.getVelocity().add(velocityDelta);
        particle.getPosition().add(particle.getVelocity().mul(delta, tempVector3));
    }

    private Vector2f tempVector2 = new Vector2f();

    private void updateScale(Particle particle, int delta) {
        float scaleDelta = scalingRate * delta;
        particle.setScale(particle.getScale() + scaleDelta);
    }

    private void updateRotation(Particle particle, int delta) {
        float rotationDelta = rotationRate * delta;
        float rotation = particle.getRotation() + rotationDelta;
        particle.setRotation(rotation);
    }
}
