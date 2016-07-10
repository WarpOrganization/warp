package pl.warp.engine.graphics.particles;

import org.joml.Vector3f;

/**
 * @author Jaca777
 *         Created 2016-07-10 at 14
 */
public class SimpleParticleAnimator implements ParticleAnimator{
    private Vector3f acceleration;
    private Vector3f scailingRate;
    private Vector3f rotationRate;

    public SimpleParticleAnimator(Vector3f acceleration, Vector3f scailingRate, Vector3f rotationRate) {
        this.acceleration = acceleration;
        this.scailingRate = scailingRate;
        this.rotationRate = rotationRate;
    }

    private Vector3f tempVector;
    @Override
    public void animate(Particle particle, int delta) {
        updateVelocity(particle, delta);
        updateScale(particle, delta);
        updateRotation(particle, delta);
    }

    private void updateVelocity(Particle particle, int delta) {
        Vector3f velocityDelta = acceleration.mul(delta, tempVector);
        particle.getVelocity().add(velocityDelta);
        particle.getPosition().add(particle.getVelocity());
    }

    private void updateScale(Particle particle, int delta) {
        Vector3f scaleDelta = scailingRate.mul(delta, tempVector);
        particle.getScale().add(scaleDelta);
    }

    private void updateRotation(Particle particle, int delta) {
        Vector3f rotationDelta = rotationRate.mul(delta, tempVector);
        particle.getRotation().add(rotationDelta);
    }
}
