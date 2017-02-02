package pl.warp.engine.graphics.particles.dot;

import org.joml.Vector3f;
import org.joml.Vector4f;

/**
 * @author Jaca777
 *         Created 2016-08-08 at 19
 */
public class TwoColorDotParticle extends DotParticle {

    private Vector4f startColor;
    private Vector4f endColor;
    private float gradient;

    public TwoColorDotParticle(Vector3f position, Vector3f velocity, float scale, int totalTimeToLive, int timeToLive, Vector4f color, Vector4f endColor, float gradient) {
        super(position, velocity, scale, 0, totalTimeToLive, timeToLive);
        this.startColor = color;
        this.endColor = endColor;
        this.gradient = gradient;
    }

    public Vector4f getStartColor() {
        return startColor;
    }

    public Vector4f getEndColor() {
        return endColor;
    }

    private Vector4f color = new Vector4f();

    @Override
    public Vector4f getColor() {
        float progress = getTimeToLive() / (float) getTotalTimeToLive();
        return color.set(interpolateValues(startColor.x, endColor.x, progress), interpolateValues(startColor.y, endColor.y, progress),
                interpolateValues(startColor.z, endColor.z, progress), interpolateValues(startColor.w, endColor.w, progress));
    }

    private float interpolateValues(float a, float b, float i) {
        return a * i + b * (1.0f - i);
    }

    @Override
    public float getGradient() {
        return gradient;
    }

}
