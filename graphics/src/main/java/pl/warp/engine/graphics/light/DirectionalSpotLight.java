package pl.warp.engine.graphics.light;

import org.joml.Vector3f;

/**
 * @author Jaca777
 *         Created 2016-06-30 at 13
 */
public class DirectionalSpotLight {
    private Vector3f position;
    private Vector3f direction;
    private float directionGradient;
    private Vector3f color, ambientColor;
    private float attenuation, gradient;
    private float factor, specularFactor;

    public DirectionalSpotLight(Vector3f position, Vector3f direction, float directionGradient, Vector3f color, Vector3f ambientColor,
                                float attenuation, float gradient, float factor, float specularFactor) {
        this.position = position;
        this.direction = direction;
        this.directionGradient = directionGradient;
        this.color = color;
        this.ambientColor = ambientColor;
        this.attenuation = attenuation;
        this.gradient = gradient;
        this.factor = factor;
        this.specularFactor = specularFactor;
    }

    public Vector3f getPosition() {
        return position;
    }

    public void setPosition(Vector3f position) {
        this.position = position;
    }

    public Vector3f getDirection() {
        return direction;
    }

    public void setDirection(Vector3f direction) {
        this.direction = direction;
    }

    public float getDirectionGradient() {
        return directionGradient;
    }

    public void setDirectionGradient(float directionGradient) {
        this.directionGradient = directionGradient;
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
