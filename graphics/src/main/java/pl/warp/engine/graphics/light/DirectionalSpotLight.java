package pl.warp.engine.graphics.light;

import org.joml.Vector3f;
import pl.warp.engine.core.scene.Component;
import pl.warp.engine.core.scene.properties.TransformProperty;

/**
 * @author Jaca777
 *         Created 2016-06-30 at 13
 */
public class DirectionalSpotLight {

    private Component owner;
    private Vector3f relativePosition;
    private Vector3f direction;
    private float directionGradient;
    private Vector3f color, ambientColor;
    private float attenuation, gradient;
    private float factor, specularFactor;

    public DirectionalSpotLight(Component owner, Vector3f relativePosition, Vector3f direction, float directionGradient, Vector3f color, Vector3f ambientColor,
                                float attenuation, float gradient, float factor, float specularFactor) {
        this.relativePosition = relativePosition;
        this.direction = direction;
        this.directionGradient = directionGradient;
        this.color = color;
        this.ambientColor = ambientColor;
        this.attenuation = attenuation;
        this.gradient = gradient;
        this.factor = factor;
        this.specularFactor = specularFactor;
    }

    private Vector3f tempPosition = new Vector3f();
    public Vector3f getPosition() {
        tempPosition.set(relativePosition);
        if (owner.hasProperty(TransformProperty.TRANSFORM_PROPERTY_NAME)) {
            TransformProperty transform = owner.getProperty(TransformProperty.TRANSFORM_PROPERTY_NAME);
            return transform.getTranslation().add(relativePosition);
        }
        return tempPosition;
    }

    public void setRelativePosition(Vector3f position) {
        this.relativePosition = position;
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
