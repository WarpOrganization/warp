package pl.warp.engine.graphics.particles;

import org.joml.Vector2f;
import org.joml.Vector3f;
import pl.warp.engine.graphics.particles.textured.TexturedParticle;

/**
 * @author Jaca777
 *         Created 2016-07-10 at 15
 */
public abstract class PrototypeParticleFactory<T extends Particle> implements ParticleFactory<T> {
    private T prototype;

    public PrototypeParticleFactory(T prototype) {
        this.prototype = prototype;
    }

    @Override
    public T newParticle() {
        return copy(prototype);
    }

    public abstract T copy(T prototype);


}
