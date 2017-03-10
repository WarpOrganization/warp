package pl.warp.engine.graphics.particles;

import org.joml.Vector3f;

/**
 * @author Jaca777
 *         Created 2017-03-10 at 22
 */
class ParticleMovementAnimator<T extends Particle> implements ParticleAnimator<T> {
    private Vector3f positionDelta = new Vector3f();

    @Override
    public void animate(T particle, int delta) {
        Vector3f position = particle.getPosition();
        positionDelta.set(particle.getVelocity()).mul(delta);
        position.add(positionDelta);
    }
}
