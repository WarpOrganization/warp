package pl.warp.engine.graphics.particles.dot;

import org.joml.Vector2f;
import org.joml.Vector3f;
import org.joml.Vector4f;
import pl.warp.engine.graphics.particles.Particle;

/**
 * @author Jaca777
 *         Created 2016-08-08 at 19
 */
public class DotParticle extends Particle {

    private Vector4f color;
    private float gradient;

    public DotParticle(Vector3f position, Vector3f velocity, float scale, int totalTimeToLive, int timeToLive, Vector4f color, float gradient) {
        super(position, velocity, scale, 0, totalTimeToLive, timeToLive);
        this.color = color;
        this.gradient = gradient;
    }

    public Vector4f getColor() {
        return color;
    }

    public float getGradient() {
        return gradient;
    }

}
