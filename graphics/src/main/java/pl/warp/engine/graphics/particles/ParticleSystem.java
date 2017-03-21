package pl.warp.engine.graphics.particles;

import java.util.*;

/**
 * @author Jaca777
 *         Created 2016-08-08 at 20
 */
public class ParticleSystem<T extends Particle> {

    private ParticleSystemAttribute<T> attribute;
    private List<T> particles;
    private ParticleEmitter<T> emitter;
    private List<ParticleAnimator<T>> animators;
    private ParticleRenderer<T> renderer;
    private boolean emit = true;

    public ParticleSystem(ParticleSystemAttribute<T> attribute, ParticleEmitter<T> emitter, ParticleAnimator<T>... animators) {
        this.attribute = attribute;
        this.particles = new LinkedList<>();
        this.emitter = emitter;
        this.animators = new ArrayList<>();
        this.animators.addAll(Arrays.asList(animators));
        this.animators.add(new ParticleMovementAnimator<>());
    }

    public void update(int delta) {
        updateParticleLifeTimes(delta);
        emitParticles(delta);
        animateParticles(delta);
    }

    protected void updateParticleLifeTimes(int delta) {
        for (Iterator<? extends Particle> iterator = particles.iterator(); iterator.hasNext(); ) {
            Particle particle = iterator.next();
            int ttl = particle.getTimeToLive() - delta;
            if (ttl > 0) particle.setTimeToLive(ttl);
            else iterator.remove();
        }
    }

    private void animateParticles(int delta) {
        for (Particle particle : particles) {
            for(ParticleAnimator animator : animators)
                animator.animate(particle, delta);
        }
        for(ParticleAnimator animator : animators)
            animator.afterAnimate();
    }

    private void emitParticles(int delta) {
        if(emit) emitter.emit(attribute.getParticleFactory(), particles, delta);
    }

    public void setEmit(boolean emit) {
        this.emit = emit;
    }

    public boolean getEmit() {
        return emit;
    }

    public List<T> getParticles() {
        return particles;
    }

    public ParticleSystemAttribute<T> getAttribute() {
        return attribute;
    }

    public ParticleRenderer<T> getRenderer() {
        return renderer;
    }

    public void setRenderer(ParticleRenderer<T> renderer) {
        this.renderer = renderer;
    }

    public void addAnimator(ParticleAnimator<T> animator){
        this.animators.add(animator);
    }
}
