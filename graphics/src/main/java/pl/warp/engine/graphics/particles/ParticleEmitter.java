package pl.warp.engine.graphics.particles;

import java.util.Iterator;
import java.util.List;

/**
 * @author Jaca777
 *         Created 2016-07-10 at 13
 */
public class ParticleEmitter {

    private ParticleFactory factory;
    private float emissionDelay;
    private List<Particle> particles;

    public ParticleEmitter(ParticleFactory factory, float frequency, List<Particle> particles) {
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
