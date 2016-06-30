package pl.warp.engine.graphics.light;

import org.joml.Vector3f;

/**
 * @author Jaca777
 *         Created 2016-06-30 at 13
 */
public class SpotLight {
    private Vector3f position;
    private Vector3f color, ambientColor;
    private float gradient, ambientGradient;
    private float factor, specularFactor;

    public SpotLight(Vector3f position, Vector3f color, float gradient, Vector3f ambientColor,  float ambientGradient, float factor, float specularFactor) {
        this.position = position;
        this.color = color;
        this.ambientColor = ambientColor;
        this.gradient = gradient;
        this.ambientGradient = ambientGradient;
        this.factor = factor;
        this.specularFactor = specularFactor;
    }

    public Vector3f getPosition() {
        return position;
    }

    public void setPosition(Vector3f position) {
        this.position = position;
    }

    public Vector3f getColor() {
        return color;
    }

    public void setColor(Vector3f color) {
        this.color = color;
    }

    public Vector3f getAmbientColor() {
        return ambientColor;
    }

    public void setAmbientColor(Vector3f ambientColor) {
        this.ambientColor = ambientColor;
    }

    public float getGradient() {
        return gradient;
    }

    public void setGradient(float gradient) {
        this.gradient = gradient;
    }

    public float getAmbientGradient() {
        return ambientGradient;
    }

    public void setAmbientGradient(float ambientGradient) {
        this.ambientGradient = ambientGradient;
    }

    public float getFactor() {
        return factor;
    }

    public void setFactor(float factor) {
        this.factor = factor;
    }

    public float getSpecularFactor() {
        return specularFactor;
    }

    public void setSpecularFactor(float specularFactor) {
        this.specularFactor = specularFactor;
    }
}
