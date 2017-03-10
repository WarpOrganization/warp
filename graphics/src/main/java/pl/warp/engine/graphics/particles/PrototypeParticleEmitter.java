package pl.warp.engine.graphics.particles;

/**
 * @author Jaca777
 *         Created 2016-07-10 at 15
 */
public abstract class PrototypeParticleEmitter<T extends Particle> extends ParticleEmitter<T> {
    private T prototype;

    public PrototypeParticleEmitter(float frequency, T prototype) {
        super(frequency);
        this.prototype = prototype;
    }

    @Override
    public T newParticle(ParticleFactory<T> factory) {
        return copy(prototype, factory);
    }

    public abstract T copy(T prototype, ParticleFactory<T> factory);


}
