package pl.warp.engine.graphics.light;

import org.joml.Vector3f;

/**
 * @author Jaca777
 *         Created 2016-06-30 at 13
 */
public class SpotLight {
    private Vector3f position;
    private Vector3f color, ambientColor;
    private float attenuation, gradient;
    private float specularFactor;

    public SpotLight(Vector3f position, Vector3f color, Vector3f ambientColor,
                     float attenuation, float gradient, float specularFactor) {
        this.position = position;
        this.color = color;
        this.ambientColor = ambientColor;
        this.attenuation = attenuation;
        this.gradient = gradient;
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

    public float getAttenuation() {
        return attenuation;
    }

    public void setAttenuation(float attenuation) {
        this.attenuation = attenuation;
    }

    public float getGradient() {
        return gradient;
    }

    public void setGradient(float gradient) {
        this.gradient = gradient;
    }

    public float getSpecularFactor() {
        return specularFactor;
    }

    public void setSpecularFactor(float specularFactor) {
        this.specularFactor = specularFactor;
    }
}
