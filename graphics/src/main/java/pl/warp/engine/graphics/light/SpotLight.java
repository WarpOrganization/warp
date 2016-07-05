package pl.warp.engine.graphics.light;

import org.joml.Vector3f;
import pl.warp.engine.core.scene.Component;
import pl.warp.engine.core.scene.properties.TransformProperty;

/**
 * @author Jaca777
 *         Created 2016-06-30 at 13
 */
public class SpotLight {

    private Component owner;
    private Vector3f relativePosition;
    private Vector3f color, ambientColor;
    private float attenuation, gradient;
    private float specularFactor;

    public SpotLight(Component owner, Vector3f relativePosition, Vector3f color, Vector3f ambientColor,
                     float attenuation, float gradient, float specularFactor) {
        this.owner = owner;
        this.relativePosition = relativePosition;
        this.color = color;
        this.ambientColor = ambientColor;
        this.attenuation = attenuation;
        this.gradient = gradient;
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
