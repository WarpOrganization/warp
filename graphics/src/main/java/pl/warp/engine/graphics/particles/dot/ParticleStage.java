package pl.warp.engine.graphics.particles.dot;

import org.joml.Vector4f;

/**
 * @author Jaca777
 *         Created 2017-02-04 at 13
 */
public class ParticleStage {
    private float gradient;
    private float scale;
    private Vector4f color;

    public ParticleStage(float gradient, Vector4f color) {
        this.gradient = gradient;
        this.color = color;
    }

    public ParticleStage() {
        this.gradient = 0f;
        this.color = new Vector4f();
    }

    public float getGradient() {
        return gradient;
    }

    public void setGradient(float gradient) {
        this.gradient = gradient;
    }

    public Vector4f getColor() {
        return color;
    }

    public void setColor(Vector4f color) {
        this.color = color;
    }

    public void setColor(float r, float g, float b, float a) {
        color.set(r, g, b, a);
    }
}
