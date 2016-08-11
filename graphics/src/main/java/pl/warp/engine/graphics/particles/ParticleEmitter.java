package pl.warp.engine.graphics.particles;

import java.util.List;

/**
 * @author Jaca777
 *         Created 2016-07-10 at 13
 */
public class ParticleEmitter <T extends Particle> {

    private ParticleFactory<T> factory;
    private float emissionDelay;
    private List<T> particles;

    public ParticleEmitter(ParticleFactory<T> factory, float frequency, List<T> particles) {
        this.factory = factory;
        this.emissionDelay = 1000f / frequency;
        this.particles = particles;
    }

    private float timeWithoutEmission = 0;

    public void emit(int delta) {
        timeWithoutEmission += delta;
        int toEmitt = (int) Math.floor(timeWithoutEmission / emissionDelay);
        timeWithoutEmission -= emissionDelay * toEmitt;
        emitParticlesNumber(toEmitt);
    }

    private void emitParticlesNumber(int number) {
        for (int i = 0; i < number; i++)
            particles.add(factory.newParticle());
    }

}
