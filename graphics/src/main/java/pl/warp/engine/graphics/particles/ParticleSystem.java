package pl.warp.engine.graphics.particles;

import pl.warp.engine.graphics.particles.textured.TexturedParticle;

import java.util.Iterator;
import java.util.List;

/**
 * @author Jaca777
 *         Created 2016-08-08 at 20
 */
public abstract class ParticleSystem {

    private ParticleEmitter emitter;
    private ParticleAnimator animator;

    public ParticleSystem(ParticleEmitter emitter, ParticleAnimator animator) {
        this.emitter = emitter;
        this.animator = animator;
    }

    public void update(int delta) {
        updateParticleLifeTimes(delta);
        emitParticles(delta);
        animateParticles(delta);
        onUpdate(delta);
    }

    private void updateParticleLifeTimes(int delta) {
        for (Iterator<? extends Particle> iterator = getParticles().iterator(); iterator.hasNext(); ) {
            Particle particle = iterator.next();
            int ttl = particle.getTimeToLive() - delta;
            if (ttl > 0) particle.setTimeToLive(ttl);
            else iterator.remove();
        }
    }

    private void animateParticles(int delta) {
        for (Particle particle : getParticles())
            animator.animate(particle, delta);
    }

    public void onUpdate(int delta) {
    }

    private void emitParticles(int delta) {
        emitter.emit(delta);
    }

    public abstract List<? extends Particle> getParticles();

    public abstract ParticleType getParticleType();
}
