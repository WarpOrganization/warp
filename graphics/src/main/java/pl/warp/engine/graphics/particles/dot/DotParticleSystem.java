package pl.warp.engine.graphics.particles.dot;

import pl.warp.engine.graphics.particles.*;

import java.util.LinkedList;
import java.util.List;

/**
 * @author Jaca777
 *         Created 2016-08-09 at 23
 */
public class DotParticleSystem extends ParticleSystem {

    private List<DotParticle> particles;

    public DotParticleSystem(ParticleAnimator<? super DotParticle> animator, ParticleFactory<DotParticle> factory, float frequency) {
        this(animator, factory, frequency, new LinkedList<>());
    }

    public DotParticleSystem(ParticleAnimator<? super DotParticle> animator, ParticleFactory<DotParticle> factory, float frequency, List<DotParticle> particles) {
        super(new ParticleEmitter<>(factory, frequency, particles), animator);
        this.particles = particles;
    }

    @Override
    public void onUpdate(int delta) {

    }

    @Override
    public List<DotParticle> getParticles() {
        return particles;
    }

    @Override
    public ParticleType getParticleType() {
        return ParticleType.DOT;
    }
}
