package pl.warp.engine.graphics.particles.dot;

import org.joml.Vector2f;
import org.joml.Vector3f;
import pl.warp.engine.graphics.particles.Particle;

/**
 * @author Jaca777
 *         Created 2016-08-08 at 19
 */
public class DotParticle extends Particle {

    private Vector3f color;
    private float gradient;

    public DotParticle(Vector3f position, Vector3f velocity, Vector2f scale, float rotation, int totalTimeToLive, int timeToLive, Vector3f color, float gradient) {
        super(position, velocity, scale, rotation, totalTimeToLive, timeToLive);
        this.color = color;
        this.gradient = gradient;
    }

    public Vector3f getColor() {
        return color;
    }

    public float getGradient() {
        return gradient;
    }
}
