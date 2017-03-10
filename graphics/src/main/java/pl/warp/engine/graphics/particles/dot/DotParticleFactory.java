package pl.warp.engine.graphics.particles.dot;

import org.joml.Vector3f;
import pl.warp.engine.graphics.particles.ParticleFactory;

/**
 * @author Jaca777
 *         Created 2017-03-10 at 20
 */
public class DotParticleFactory implements ParticleFactory<DotParticle> {
    private ParticleStage[] stages;

    public DotParticleFactory(ParticleStage[] stages) {
        this.stages = stages;
    }

    @Override
    public DotParticle newParticle(Vector3f position, Vector3f velocity, float scale, float rotation, int ttl) {
        return new DotParticle(position, velocity, scale, rotation, ttl, ttl, stages);
    }

}
