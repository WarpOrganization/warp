package pl.warp.engine.graphics.particles;

import java.util.List;

/**
 * @author Jaca777
 *         Created 2016-07-10 at 13
 */
public abstract class ParticleEmitter <T extends Particle> {

    private float emissionDelay;

    public ParticleEmitter(float frequency) {
        this.emissionDelay = 1000f / frequency;
    }

    private float timeWithoutEmission = 0;

    public void emit(ParticleFactory<T> particleFactory, List<T> particles, int delta) {
        timeWithoutEmission += delta;
        int toEmitt = (int) Math.floor(timeWithoutEmission / emissionDelay);
        timeWithoutEmission -= emissionDelay * toEmitt;
        emitParticlesNumber(particleFactory, particles, toEmitt);
    }

    private void emitParticlesNumber(ParticleFactory<T> particleFactory, List<T> particles, int number) {
        for (int i = 0; i < number; i++)
            particles.add(newParticle(particleFactory));
    }

    protected abstract T newParticle(ParticleFactory<T> factory);

}
