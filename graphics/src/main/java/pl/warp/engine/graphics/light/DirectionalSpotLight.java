package pl.warp.engine.graphics.light;

import org.joml.Matrix4f;
import org.joml.Quaternionf;
import org.joml.Vector3f;
import pl.warp.engine.core.scene.Component;
import pl.warp.engine.core.scene.properties.TransformProperty;
import pl.warp.engine.graphics.math.Transforms;

/**
 * @author Jaca777
 *         Created 2016-06-30 at 13
 */
public class DirectionalSpotLight {

    private Component owner;
    private Vector3f relativePosition;
    private Vector3f coneDirection;
    private float coneAngle;
    private float coneGradient;
    private Vector3f color, ambientColor;
    private float attenuation, gradient;

    public DirectionalSpotLight(
            Component owner,
            Vector3f relativePosition,
            Vector3f coneDirection, float coneAngle,
            float coneGradient,
            Vector3f color,
            Vector3f ambientColor,
            float attenuation,
            float gradient) {
        this.owner = owner;
        this.relativePosition = relativePosition;
        this.coneDirection = coneDirection;
        this.coneAngle = coneAngle;
        this.coneGradient = coneGradient;
        this.color = color;
        this.ambientColor = ambientColor;
        this.attenuation = attenuation;
        this.gradient = gradient;
    }

    private Vector3f tempPosition = new Vector3f();

    public Vector3f getPosition() {
        Matrix4f fullTransform = Transforms.getFullTransform(owner);
        return fullTransform.transformPosition(relativePosition, tempPosition);
    }

    public void setRelativePosition(Vector3f position) {
        this.relativePosition = position;
    }

    private Vector3f tempDirection = new Vector3f();

    public Vector3f getDirection() {
        Quaternionf fullRotation = Transforms.getFullRotation(owner);
        return fullRotation.transform(coneDirection, tempDirection);
    }

    public void setConeDirection(Vector3f direction) {
        this.coneDirection = direction;
    }

    public float getConeAngle() {
        return coneAngle;
    }

    public void setConeAngle(float coneAngle) {
        this.coneAngle = coneAngle;
    }

    public Vector3f getConeDirection() {
        return coneDirection;
    }

    public float getConeGradient() {
        return coneGradient;
    }

    public void setConeGradient(float coneGradient) {
        this.coneGradient = coneGradient;
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

}
