package pl.warp.engine.graphics.particles;

/**
 * @author Jaca777
 *         Created 2016-07-10 at 13
 */
public class ParticleEmitter {

    private ParticleAnimator animator;
    private ParticleFactory factory;
    private float emissionDelay;
    private ParticleEnvironment environment;

    public ParticleEmitter(ParticleAnimator animator, ParticleFactory factory, ParticleEnvironment environment, float frequency) {
        this.animator = animator;
        this.factory = factory;
        this.environment = environment;
        this.emissionDelay = 1000f / frequency;
    }

    public void update(int delta) {
        updateParticlesLifeTime(delta);
        emit(delta);
        animate(delta);
    }

    private void updateParticlesLifeTime(int delta) {
        for (Particle particle : environment.getParticles()) {
            int ttl = particle.getTimeToLive() - delta;
            if (ttl > 0) particle.setTimeToLive(ttl);
            else environment.removeParticle(particle);
        }
    }

    private float timeWithoutEmission = 0;

    private void emit(int delta) {
        timeWithoutEmission += delta;
        int toEmitt = (int) Math.floor(timeWithoutEmission / emissionDelay);
        timeWithoutEmission -= emissionDelay * toEmitt;
        emitParticlesNumber(toEmitt);
    }

    private void emitParticlesNumber(int number) {
        for (int i = 0; i < number; i++)
            environment.addParticle(factory.newParticle());
    }

    private void animate(int delta) {
        for (Particle particle : environment.getParticles()) {
            animator.animate(particle, delta);
        }
    }
}
