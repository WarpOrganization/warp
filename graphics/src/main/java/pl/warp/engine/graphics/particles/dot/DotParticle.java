package pl.warp.engine.graphics.particles.dot;

import org.joml.Vector3f;
import pl.warp.engine.graphics.particles.Particle;

/**
 * @author Jaca777
 *         Created 2017-02-02 at 01
 */
public abstract class DotParticle extends Particle {
    public DotParticle(Vector3f position, Vector3f velocity, float scale, float rotation, int totalTimeToLive, int timeToLive) {
        super(position, velocity, scale, rotation, totalTimeToLive, timeToLive);
    }


    abstract ParticleStage getStage();
}
