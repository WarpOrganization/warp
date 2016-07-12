package pl.warp.engine.graphics.particles;

import java.util.LinkedHashSet;
import java.util.LinkedList;

/**
 * @author Jaca777
 *         Created 2016-07-12 at 14
 */
class ParticleEnvironment {
    private LinkedList<Particle> particles = new LinkedList<>();

    public void addParticle(Particle particle) {
        particles.add(particle);
    }

    public void removeParticle(Particle particle) {
        particles.remove(particle);
    }

    public LinkedList<Particle> getParticles() {
        return particles;
    }
}
